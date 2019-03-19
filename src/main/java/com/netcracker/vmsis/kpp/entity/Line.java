package com.netcracker.vmsis.kpp.entity;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Line {
    private int projectionX;
    private int projectionY;
    private double length;
}