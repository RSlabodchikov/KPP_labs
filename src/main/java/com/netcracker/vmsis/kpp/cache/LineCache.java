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

    public void addLineToCache(Input input, Line line){
        this.cache.put(input,line);
    }

    public boolean checkInput(Input input){
        return this.cache.containsKey(input);
      /* for (Input i : cache.keySet()){
           if (i.equals(input)) return true;
       }
       return false;*/
    }

    public Line findByInput(Input input){
        return this.cache.get(input);
    }

}
