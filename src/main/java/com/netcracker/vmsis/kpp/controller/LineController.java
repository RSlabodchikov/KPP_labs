package com.netcracker.vmsis.kpp.controller;

import com.netcracker.vmsis.kpp.entity.Line;
import com.netcracker.vmsis.kpp.service.LineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping(value = "/lab/line")
public class LineController {
    private LineService service;

    @Autowired
    public LineController(LineService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public synchronized ResponseEntity createLine(
            @RequestParam(name =  "params") List<String> params) {
        try {
            List<Integer> integers= params.stream().map(Integer::valueOf).collect(Collectors.toList());
            log.info("Coordinates: {}", integers);
            Optional<Line> line = service.createLine(integers);
            if (line.isPresent()) {
                return ResponseEntity.ok(line.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception exception) {
            log.error("Bad params");
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}

