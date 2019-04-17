package com.netcracker.vmsis.kpp.service;


import com.netcracker.vmsis.kpp.cache.Counter;
import com.netcracker.vmsis.kpp.cache.LineCache;
import com.netcracker.vmsis.kpp.entity.Input;
import com.netcracker.vmsis.kpp.entity.InputList;
import com.netcracker.vmsis.kpp.entity.Line;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LineService {
    private LineCache cache;

    private Counter counter;


    @Autowired
    public LineService(LineCache cache, Counter counter) {
        this.cache = cache;
        this.counter = counter;
    }


    public Optional<Line> createLine(List<Integer> coordinates) {
        log.info("The number of uses of the service in this session {}", counter.increment());
        Input input = new Input(coordinates.get(0), coordinates.get(1), coordinates.get(2), coordinates.get(3));
        if (!validateInput(input)) return Optional.empty();
        return Optional.of(convertInputToLine(input));
    }

    private boolean validateInput(Input input) {
        return (input.getX2() > input.getX1() || input.getY2() > input.getY1());
    }

    public List<Line> createLineCollection(InputList inputList) {
        return inputList.getInputList()
                .stream()
                .filter(this::validateInput)
                .map(this::convertInputToLine)
                .collect(Collectors.toList());
    }

    private Line convertInputToLine(Input input) {
        System.out.println(cache.checkInput(input));
        if (cache.checkInput(input)) {
            return cache.findByInput(input);
        }
        Line line = new Line();
        line.setProjectionX(input.getX2() - input.getX1());
        line.setProjectionY(input.getY2() - input.getY1());
        line.setLength(Math.sqrt((input.getX1() - input.getX2()) * (input.getX1() - input.getX2()) + (input.getY1() - input.getY2()) * (input.getY1() - input.getY2())));
        cache.addLineToCache(input, line);
        return line;
    }
}
