package com.epam.vmsis.kpp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class ResultCollection {
    private List<Line> list;
    private Statistic statistic;
}
