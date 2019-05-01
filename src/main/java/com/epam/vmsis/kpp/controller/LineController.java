package com.epam.vmsis.kpp.controller;

import com.epam.vmsis.kpp.service.LineService;
import com.epam.vmsis.kpp.entity.InputList;
import com.epam.vmsis.kpp.entity.Line;
import com.epam.vmsis.kpp.entity.ResultCollection;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
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

    private final static Logger logger = Logger.getLogger(LineController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity createLine(@RequestParam(name = "params") List<String> params) {
        try {
            List<Integer> integers = params.stream().map(Integer::valueOf).collect(Collectors.toList());
            log.info("Coordinates: {}", integers);
            Optional<Line> line = service.createLine(integers);
            if (line.isPresent()) {
                logger.info("Response body: " + line.get().toString());
                return ResponseEntity.ok(line.get());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception exception) {
            log.error("Bad params");
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/listParam", method = RequestMethod.POST)
    public ResponseEntity createLineCollection(@RequestBody InputList inputList) {
        return ResponseEntity.ok(service.createLineCollection(inputList));
    }

    @RequestMapping(value = "/listParam", method = RequestMethod.GET)
    public ResponseEntity getResultCollection(@RequestParam(name = "id") Long id) throws InterruptedException, ExecutionException {
        ResultCollection resultCollection = service.getResultCollectionById(id);
        if (resultCollection != null) {
            return ResponseEntity.ok(resultCollection);
        } else return new ResponseEntity<>("Cannot find result or result is still processing!!!", HttpStatus.NOT_FOUND);
    }

}

