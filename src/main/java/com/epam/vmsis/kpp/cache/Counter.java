package com.epam.vmsis.kpp.cache;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public  class Counter {
    private long count;



    public  long increment(){

        return ++count;
    }
}
