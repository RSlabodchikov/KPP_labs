package com.epam.vmsis.kpp.cache;

import com.epam.vmsis.kpp.entity.Input;
import com.epam.vmsis.kpp.entity.ResultCollection;
import com.epam.vmsis.kpp.entity.Line;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Component
@Slf4j
public class LineCache {
    private Map<Input, Line> cache = new HashMap<>();

    private Map<Long, Future<ResultCollection>> resultCollectionMap = new HashMap<>();


    public long generateId() {
        return new Random().nextLong();
    }

    public void addLineToCache(Input input, Line line) {
        log.info("You add object to cache:{}", line.toString());
        this.cache.put(input, line);
    }

    public boolean checkInput(Input input) {
        return this.cache.containsKey(input);
    }

    public Line findByInput(Input input) {
        log.info("You get object from cache :{}", cache.get(input).toString());
        return this.cache.get(input);
    }


    public ResultCollection getCollectionById(Long id) throws InterruptedException, ExecutionException {
        Future<ResultCollection> future = resultCollectionMap.get(id);
        if (future.isDone()) {
            return future.get();
        } else return null;
    }

    public boolean containsResult(Long id) {
        return resultCollectionMap.containsKey(id);
    }

    public void addResultToMap(Future<ResultCollection> future, Long id) {
        resultCollectionMap.put(id, future);
    }

}
