package com.nasa.marsrover.service.impl;

import com.nasa.marsrover.entity.Rover;
import com.nasa.marsrover.entity.enums.CardinalPoint;
import com.nasa.marsrover.service.MotionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.primes.Primes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

import static com.nasa.marsrover.service.utils.PredicateSet.*;

@Service
@Transactional
@Slf4j
public class MotionServiceImpl implements MotionService {

    @Override
    public String getOverallSpin(Long countSpin, String currentFacing) {
        if (checkIsEven(countSpin)) {
            return currentFacing;
        } else {
            return spinRoverOpposite(currentFacing);
        }
    }

    @Override
    public Boolean validateControlCommand(String commandString) {
        log.info("Mars Rover - Motion Command Validation");
        if (Objects.nonNull(commandString)) {
            String[] strings = commandString.split("");
            return Arrays.stream(strings).noneMatch(doesNotcontainM.and(doesNotcontainR).and(doesNotcontainL));
        }
        return false;
    }

    @Override
    public String spinRoverOpposite(String currentFacing) {
        String newFacing;
        if (currentFacing.equals(CardinalPoint.N.toString())) {
            newFacing = CardinalPoint.S.toString();
        } else if (currentFacing.equals(CardinalPoint.S.toString())) {
            newFacing = CardinalPoint.N.toString();
        } else if (currentFacing.equals(CardinalPoint.E.toString())) {
            newFacing = CardinalPoint.W.toString();
        } else if (currentFacing.equals(CardinalPoint.W.toString())) {
            newFacing = CardinalPoint.E.toString();
        } else {
            newFacing = currentFacing;
        }
        return newFacing;
    }

    @Override
    public Rover moveAlongAxes(Integer x, Integer y, String facing) {
        log.info("Mars Rover - Motion Command Move along");
        var roverAlongAxes = new Rover();
        var newXCoord = 0;
        var newYCoord = 0;
        if (facing.equals(CardinalPoint.E.toString())) {

            newXCoord = x + 1;
            newYCoord = y;
        } else if (facing.equals(CardinalPoint.W.toString())) {
            newXCoord = x - 1;
            newYCoord = y;
        } else if (facing.equals(CardinalPoint.N.toString())) {
            newYCoord = y + 1;
            newXCoord = x;
        } else {
            newYCoord = y - 1;
            newXCoord = x;
        }
        roverAlongAxes.setFacingDirection(facing);
        roverAlongAxes.setCoordinateX(newXCoord);
        roverAlongAxes.setCoordinateY(newYCoord);
        return roverAlongAxes;


    }

    @Override
    public Boolean checkMotionIsNotPrime(Integer newX, Integer newY) {
        log.info("Mars Rover - Motion Is not Prime");
        return (checkIsNotPrime(newX, newY));
    }

    private boolean checkIsEven(long count) {
        return (count % 2 == 0);
    }

    private boolean checkIsNotPrime(Integer newX, Integer newY) {
        int coordinateSum = Math.addExact(newX, newY);
        return !Primes.isPrime(coordinateSum);

    }
}
