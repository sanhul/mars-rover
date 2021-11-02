package com.nasa.marsrover.service.impl;

import com.nasa.marsrover.entity.Rover;
import com.nasa.marsrover.entity.enums.CardinalPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class MotionServiceImplTest {
    @InjectMocks
    private MotionServiceImpl motionService;
    @Test
    public void validateRoverMotionOK_notPrime_0() {
        Boolean outcome = motionService.checkMotionIsNotPrime(0, 0);
        assertTrue(outcome);
    }

    @Test
    public void validateRoverMotionOK_notPrime() {
        Boolean outcome = motionService.checkMotionIsNotPrime(21, 11);
        assertTrue(outcome);
    }

    @Test
    public void validateRoverMotionOK_Prime_InBoundary() {
        Boolean outcome = motionService.checkMotionIsNotPrime(20, 3);
        assertFalse(outcome);
    }
    @Test
    public void validateMotionCommand_LM() {
        var outcome = motionService.validateControlCommand("LMLMMM");
        assertTrue(outcome);
    }

    @Test
    public void validateMotionCommand_XM() {
        var outcome = motionService.validateControlCommand("LMXM");
        assertFalse(outcome);
    }

    @Test
    public void validateMotionCommand_S233m() {
        var outcome = motionService.validateControlCommand("S233m");
        assertFalse(outcome);
    }

    @Test
    public void validateMotionCommand_LMR() {
        var outcome = motionService.validateControlCommand("LMMMLR");
        assertTrue(outcome);
    }

    @Test
    public void validateMotionCommand_RRR() {
        var outcome = motionService.validateControlCommand("RRRR");
        assertTrue(outcome);
    }

    @Test
    public void validateMotionCommand_NULL() {
        var outcome = motionService.validateControlCommand(null);
        assertFalse(outcome);
    }

    @Test
    public void validateMotionCommand_incorrect() {
        var outcome = motionService.validateControlCommand("L M ");
        assertFalse(outcome);
    }

    @Test
    public void spinRoverOppositeto_S() {
        var outcome = motionService.spinRoverOpposite(CardinalPoint.S.toString());
        assertEquals(CardinalPoint.N, CardinalPoint.valueOf(outcome));
    }

    @Test
    public void spinRoverOppositeto_N() {
        var outcome = motionService.spinRoverOpposite(CardinalPoint.N.toString());
        assertEquals(CardinalPoint.S, CardinalPoint.valueOf(outcome));
    }

    @Test
    public void spinRoverOppositeto_W() {
        var outcome = motionService.spinRoverOpposite(CardinalPoint.W.toString());
        assertEquals(CardinalPoint.E, CardinalPoint.valueOf(outcome));
    }

    @Test
    public void spinRoverOppositeto_E() {
        var outcome = motionService.spinRoverOpposite(CardinalPoint.E.toString());
        assertEquals(CardinalPoint.W, CardinalPoint.valueOf(outcome));
    }

    @Test
    public void getOverallSpin_odd() {
        var outcome = motionService.getOverallSpin(5L, CardinalPoint.E.toString());
        assertEquals(CardinalPoint.W, CardinalPoint.valueOf(outcome));
    }

    @Test
    public void getOverallSpin_even() {
        var outcome = motionService.getOverallSpin(2L, CardinalPoint.N.toString());
        assertEquals(CardinalPoint.N, CardinalPoint.valueOf(outcome));
    }
    @Test
    public void moveXAxis_E() {
        var outcome = motionService.moveAlongAxes( 7,7,CardinalPoint.E.toString());
        assertEquals(8, outcome.getCoordinateX());
    }
    @Test
    public void moveXAxis_W() {
        var outcome = motionService.moveAlongAxes( 7,7,CardinalPoint.W.toString());
        assertEquals(6, outcome.getCoordinateX());
    }
    @Test
    public void moveYAxis_N() {
        var outcome = motionService.moveAlongAxes( 7,7,CardinalPoint.N.toString());
        assertEquals(8, outcome.getCoordinateY());
    }
    @Test
    public void moveYAxis_S() {
        var outcome = motionService.moveAlongAxes( 7,7,CardinalPoint.S.toString());
        assertEquals(6, outcome.getCoordinateY());
    }
}