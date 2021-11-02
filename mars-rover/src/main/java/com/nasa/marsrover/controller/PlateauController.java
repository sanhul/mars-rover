package com.nasa.marsrover.controller;

import com.nasa.marsrover.entity.Plateau;
import com.nasa.marsrover.entity.request.PlateauUpdateRequest;
import com.nasa.marsrover.entity.response.EntirePlateauResponse;
import com.nasa.marsrover.entity.response.PlateauResponse;
import com.nasa.marsrover.service.PlateauService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
        (value = "/api/v1/plateaus")
@Api(value = "PlateauController")
public class PlateauController {
    private final PlateauService plateauService;

    @ApiOperation(value = "Create Plateau based on number of rows ans columns")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK: with response - Creation successful ", response = Plateau.class),
    })
    @PostMapping
    public ResponseEntity<Plateau> createPlateau(@RequestBody Plateau plateau) {
        if (Objects.nonNull(plateau)) {
            return ResponseEntity.ok(plateauService.createPlateau(plateau.getMaxXCoordinate(), plateau.getMaxYCoordinate()));
        }
        return ResponseEntity.ok(null);
    }
    @ApiOperation(value = "Update Plateau State and Create rovers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK: with response - Update and Creation successful ", response = Plateau.class),
    })
    @PostMapping(value = "/roverCreation")
    public ResponseEntity<EntirePlateauResponse> updatePlateauForRover(@RequestBody PlateauUpdateRequest plateauUpdateRequest) {
        if (Objects.nonNull(plateauUpdateRequest)) {
            var plateauResponse = plateauService.updatePlateauForRoverCreation(plateauUpdateRequest);
            return ResponseEntity.ok(plateauService.updatePlateauForRoverMotion(plateauResponse));
        }
        return ResponseEntity.ok(null);
    }
}
