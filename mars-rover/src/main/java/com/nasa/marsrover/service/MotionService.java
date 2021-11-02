package com.nasa.marsrover.service;

import com.nasa.marsrover.entity.Rover;

public interface MotionService {
    String getOverallSpin(Long countSpin, String currentFacing);

    Boolean validateControlCommand(String commandString);

    String spinRoverOpposite(String currentFacing);
    Rover moveAlongAxes( Integer x, Integer y ,String facing);
    Boolean checkMotionIsNotPrime(Integer newX, Integer newY);
}
