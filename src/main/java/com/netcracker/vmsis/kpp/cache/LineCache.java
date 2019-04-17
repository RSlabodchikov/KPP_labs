package com.netcracker.vmsis.kpp.cache;

import com.netcracker.vmsis.kpp.entity.Input;
import com.netcracker.vmsis.kpp.entity.Line;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class LineCache {
    private Map<Input, Line> cache = new HashMap<>();

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

}
