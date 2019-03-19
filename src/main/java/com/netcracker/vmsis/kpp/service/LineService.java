package com.netcracker.vmsis.kpp.service;


import com.netcracker.vmsis.kpp.entity.Line;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LineService {
    public Optional<Line> createLine(List<Integer> coordinates) {
        if (coordinates.size()<4){
            log.error("Not enough coordinates");
            return Optional.empty();
        }
        int x1, x2, y1, y2;
        x1 = coordinates.get(0);
        x2 = coordinates.get(1);
        y1 = coordinates.get(2);
        y2 = coordinates.get(3);
        if (x1 > x2 || y1 > y2) {
            log.error("Wrong coordinates : {}", coordinates);
            return Optional.empty();
        }
        int projectionX = x2 - x1;
        int projectionY = y2 - y1;
        double length = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        Line line = new Line(projectionX, projectionY, length);
        log.info(line.toString());
        return Optional.of(line);
    }
}
