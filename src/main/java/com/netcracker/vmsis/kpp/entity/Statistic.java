package com.netcracker.vmsis.kpp.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class Statistic {
    int totalAmount;
    int totalAmountOfInvalidInputParams;
    Line minValue;
    Line maxValue;
    Line mostPopularResult;

    public void findMostPopularResult(List<Line> lineList) {
        if (lineList.isEmpty()) return;
        HashMap<Line, Integer> lineMap = new HashMap<>();
        lineList.forEach(a -> {
            if (lineMap.containsKey(a)) {
                lineMap.put(a, lineMap.get(a) + 1);
            } else lineMap.put(a, 1);
        });
        int maxNumber = lineMap.values()
                .stream()
                .max(Integer::compareTo)
                .orElse(1);
        lineMap.forEach((k, v) -> {
            if (v == maxNumber) {
                this.mostPopularResult = k;
            }
        });
    }
}
