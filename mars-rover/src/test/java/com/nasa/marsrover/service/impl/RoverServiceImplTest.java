package com.nasa.marsrover.service.impl;

import com.nasa.marsrover.entity.Plateau;
import com.nasa.marsrover.entity.Rover;
import com.nasa.marsrover.entity.enums.CardinalPoint;
import com.nasa.marsrover.entityHelper.TestHelper;
import com.nasa.marsrover.repository.RoverRepository;
import com.nasa.marsrover.service.MotionService;
import com.nasa.marsrover.service.utils.RequestResponseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoverServiceImplTest {
    @InjectMocks
    private RoverServiceImpl roverService;
    @Mock
    private MotionService motionService;
    @Mock
    private RoverRepository roverRepository;
    @Mock
    private RequestResponseHelper requestResponseHelper;
    private Plateau plateau = TestHelper.buildPlateau(20, 11);

    @Test
    public void getNewRoverPosition_Ok() {
        Rover testRover = TestHelper.buildRover(7, 7, CardinalPoint.E);
        Rover updatedRover = TestHelper.updateRover(testRover, 5, 7, CardinalPoint.W);
        when(motionService.validateControlCommand(testRover.getMotionCommand())).thenReturn(true);
        when(requestResponseHelper.buildRoverForState(testRover)).thenReturn(testRover);
        when(motionService.checkMotionIsNotPrime(updatedRover.getCoordinateX(), updatedRover.getCoordinateY())).thenReturn(true);
        when(motionService.spinRoverOpposite(testRover.getFacingDirection())).thenReturn(CardinalPoint.W.toString());
        when(motionService.moveAlongAxes(testRover.getCoordinateX(), testRover.getCoordinateY(), testRover.getFacingDirection())).thenReturn(updatedRover);
        Rover outcomeRover = roverService.getNewRoverPosition(testRover, TestHelper.buildPlateau(10, 11));
        assertNotNull(outcomeRover);
    }

    @Test
    public void getNewRoverPosition_Prime() {
        Rover testRover = TestHelper.buildRover(7, 7, CardinalPoint.E);
        Rover updatedRover = TestHelper.updateRover(testRover, 5, 7, CardinalPoint.W);
        when(motionService.validateControlCommand(testRover.getMotionCommand())).thenReturn(true);
        when(requestResponseHelper.buildRoverForState(testRover)).thenReturn(testRover);
        when(motionService.checkMotionIsNotPrime(updatedRover.getCoordinateX(), updatedRover.getCoordinateY())).thenReturn(false);
        when(motionService.spinRoverOpposite(testRover.getFacingDirection())).thenReturn(CardinalPoint.W.toString());
        when(motionService.moveAlongAxes(testRover.getCoordinateX(), testRover.getCoordinateY(), testRover.getFacingDirection())).thenReturn(updatedRover);
        Rover outcomeRover = roverService.getNewRoverPosition(testRover, TestHelper.buildPlateau(10, 11));
        assertNull(outcomeRover);
    }
}