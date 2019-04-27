package com.netcracker.vmsis.kpp.service;


import com.netcracker.vmsis.kpp.cache.Counter;
import com.netcracker.vmsis.kpp.cache.LineCache;
import com.netcracker.vmsis.kpp.entity.*;
import com.netcracker.vmsis.kpp.repository.InputRepository;
import com.netcracker.vmsis.kpp.repository.LineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LineService {
    private LineCache cache;

    private Counter counter;

    private LineRepository repository;

    private InputRepository inputRepository;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public LineService(LineCache cache, Counter counter, LineRepository repository, InputRepository inputRepository) {
        this.cache = cache;
        this.counter = counter;
        this.repository = repository;
        this.inputRepository = inputRepository;
    }

    @PostConstruct
    public void initialize() {
        inputRepository.findAll().forEach(a -> {
            Line line = repository.findById(a.getLineId()).orElse(null);
            cache.addLineToCache(a, line);
        });
    }

    public Optional<Line> createLine(List<Integer> coordinates) {
        log.info("The number of uses of the service in this session {}", counter.increment());
        Input input = new Input(coordinates.get(0), coordinates.get(1), coordinates.get(2), coordinates.get(3));
        if (!validateInput(input)) return Optional.empty();
        return Optional.of(convertInputToLine(input));
    }

    private boolean validateInput(Input input) {
        return (input.getX2() >= input.getX1() && input.getY2() >= input.getY1());
    }


    public long createLineCollection(InputList inputList) {
        long id = cache.generateId();
        Future<ResultCollection> future = processInputList(inputList);
        cache.addResultToMap(future, id);
        return id;
    }

    public ResultCollection getResultCollectionById(Long id) throws ExecutionException, InterruptedException {
        return cache.containsResult(id) ? cache.getCollectionById(id) : null;
    }

    private Line convertInputToLine(Input input) {
        if (cache.checkInput(input)) {
            return cache.findByInput(input);
        }
        Line line = new Line();
        line.setProjectionX(input.getX2() - input.getX1());
        line.setProjectionY(input.getY2() - input.getY1());
        double length = Math.sqrt((input.getX1() - input.getX2()) * (input.getX1() - input.getX2()) + (input.getY1() - input.getY2()) * (input.getY1() - input.getY2()));
        length = (double) Math.round(length * 100d) / 100d;
        line.setLength(length);
        line = repository.save(line);
        input.setLineId(line.getId());
        inputRepository.save(input);
        cache.addLineToCache(input, line);
        return line;
    }

    private Future<ResultCollection> processInputList(InputList inputList) {
        return executorService.submit(() -> {
            ResultCollection collection = new ResultCollection();
            collection.setList(inputList.getInputList()
                    .stream()
                    .filter(this::validateInput)
                    .map(this::convertInputToLine)
                    .collect(Collectors.toList()));
            Statistic statistic = new Statistic();
            statistic.setTotalAmount(collection.getList().size());
            statistic.setTotalAmountOfInvalidInputParams(inputList.getInputList().size() - collection.getList().size());
            statistic.setMaxValue(collection.getList()
                    .stream()
                    .max(Line::compareTo)
                    .orElse(null));
            statistic.setMinValue(collection.getList()
                    .stream()
                    .min(Line::compareTo)
                    .orElse(null));
            statistic.findMostPopularResult(collection.getList());
            collection.setStatistic(statistic);
            return collection;
        });
    }
}
