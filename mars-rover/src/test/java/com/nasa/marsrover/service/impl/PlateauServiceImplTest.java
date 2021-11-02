package com.nasa.marsrover.service.impl;


import com.nasa.marsrover.entity.Plateau;
import com.nasa.marsrover.entity.enums.CardinalPoint;
import com.nasa.marsrover.entity.response.EntirePlateauResponse;
import com.nasa.marsrover.entity.response.PlateauResponse;
import com.nasa.marsrover.entityHelper.TestHelper;
import com.nasa.marsrover.repository.PlateauRepository;
import com.nasa.marsrover.service.RoverService;
import com.nasa.marsrover.service.utils.RequestResponseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class PlateauServiceImplTest {
    @InjectMocks
    private PlateauServiceImpl plateauService;
    @Mock
    private RoverService roverService;
    @Mock
    private RequestResponseHelper requestResponseHelper;
    @Mock
    private PlateauRepository plateauRepository;

    @Test
    public void isPlateauRectangular_false() {
        Plateau outcome = plateauService.createPlateau(20, 20);
        assertNull(outcome);
    }

    @Test
    public void plateau_shouldBeGreater0() {
        Plateau outcome = plateauService.createPlateau(0, 0);
        assertNull(outcome);
    }

    @Test
    public void plateau_shouldBeGreaterLine() {
        Plateau outcome = plateauService.createPlateau(0, 10);
        assertNull(outcome);
    }

    @Test
    public void isPlateauValid_false() {
        Plateau outcome = plateauService.createPlateau(-1, 0);
        assertNull(outcome);
    }

    @Test
    public void updatePlateauForRoverCreation_test() {
        var plateau = TestHelper.buildPlateau(10, 11);
        var plateauUpdateRequest = TestHelper.buildPlateauUpdateRequest(plateau);
        var rover = TestHelper.buildRover(7, 7, CardinalPoint.E);
        var updatedRover = TestHelper.updateRover(rover,5,7,CardinalPoint.W);
        var plateauResp = TestHelper.buildPlateauResponse(plateau);
        when(plateauRepository.getById(plateau.getId())).thenReturn(plateau);
        when(requestResponseHelper.buildPlateauResponse(plateau)).thenReturn(plateauResp);
        PlateauResponse outcome = plateauService.updatePlateauForRoverCreation(plateauUpdateRequest);
        assertEquals(plateauResp.getPlateauId(), outcome.getPlateauId());
    }
    @Test
    public void updatePlateauForRoverMotion_test() {
        var plateau = TestHelper.buildPlateau(10, 11);
        var rover = TestHelper.buildRover(7, 7, CardinalPoint.E);
        var updatedRover = TestHelper.updateRover(rover,5,7,CardinalPoint.W);
        var roverResp = TestHelper.buildRoverResponse(updatedRover);
        var plateauResp = TestHelper.buildPlateauResponse(plateau);
        var entirePlateauResp = TestHelper.buildEntireResponse(plateau, updatedRover);
        when(requestResponseHelper.buildRoverResponse(rover)).thenReturn(roverResp);
        when(roverService.updateRover("1",plateau)).thenReturn(updatedRover);
        when(requestResponseHelper.buildPlateau(plateauResp)).thenReturn(plateau);
        EntirePlateauResponse outcome = plateauService.updatePlateauForRoverMotion(plateauResp);
        assertEquals(entirePlateauResp.getPlateauResponse().getPlateauId(), outcome.getPlateauResponse().getPlateauId());
        assertEquals(entirePlateauResp.getRoverResponseList().get(0).getRoverId(),
                outcome.getRoverResponseList().get(0).getRoverId());
    }
}