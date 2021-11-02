package com.nasa.marsrover.service;

import com.nasa.marsrover.entity.Plateau;
import com.nasa.marsrover.entity.Rover;

import java.util.List;


public interface RoverService {
    String createRover(String plateauId,Rover rover);
    Rover getNewRoverPosition(Rover newRoverState,Plateau plateau);
    Rover updateRover(String roverId, Plateau plateau);
    List<Rover> getRoverByPlateauRef(String plateauRef);
    Rover getRoverDetailByID(String roverId);

}
