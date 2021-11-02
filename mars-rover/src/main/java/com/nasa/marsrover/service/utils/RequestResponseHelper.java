package com.nasa.marsrover.service.utils;

import com.nasa.marsrover.entity.Plateau;
import com.nasa.marsrover.entity.Rover;
import com.nasa.marsrover.entity.response.PlateauResponse;
import com.nasa.marsrover.entity.response.RoverResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nasa.marsrover.service.utils.StringHelper.*;
@Component
public class RequestResponseHelper {
    public List<Rover> buildRoverRequest(String roverRequestDetails) {
        //1 2 N}MLRMRLR]3 5 W}LMRRR
        String[] roverStrings = roverRequestDetails.split(squareBracket);
        List<Rover> roverList = new ArrayList<>();
        Arrays.stream(roverStrings).forEach(roverString -> {
            String[] roverDetails = roverString.split(curlyBracket);
            String[] roverCoords = roverDetails[0].split(freeSpace);
            var rover = new Rover();
            rover.setCoordinateX(Integer.parseInt(roverCoords[0]));
            rover.setCoordinateY(Integer.parseInt(roverCoords[1]));
            rover.setFacingDirection(roverCoords[2]);
            rover.setMotionCommand(roverDetails[1]);
            roverList.add(rover);
        });
        return roverList;
    }
    public RoverResponse buildRoverResponse(Rover rover) {
        RoverResponse roverResponse = new RoverResponse();
        roverResponse.setRoverId(rover.getId());
        roverResponse.setRoverX(rover.getCoordinateX());
        roverResponse.setRoverY(rover.getCoordinateY());
        roverResponse.setRoverFacing(rover.getFacingDirection());
        return roverResponse;
    }
    public Rover buildRoverForState(Rover rover) {
        var stateRover = new Rover();
        stateRover.setMoveType(rover.getMoveType());
        stateRover.setFacingDirection(rover.getFacingDirection());
        stateRover.setCoordinateY(rover.getCoordinateY());
        stateRover.setCoordinateX(rover.getCoordinateX());
        stateRover.setMotionCommand(rover.getMotionCommand());
        return stateRover;
    }
    public PlateauResponse buildPlateauResponse(Plateau plateau) {
        PlateauResponse plateauResponse = new PlateauResponse();
        plateauResponse.setPlateauId(plateau.getId());
        plateauResponse.setPlateauMaxRover(plateau.getMaxRoverNumber());
        plateauResponse.setPlateauMaxRow(plateau.getMaxXCoordinate());
        plateauResponse.setPlateauMaxCol(plateau.getMaxYCoordinate());
        plateauResponse.setRoverId(plateau.getRoverId());
        return plateauResponse;
    }
    public Plateau buildPlateau(PlateauResponse plateauResponse) {
        var plateau = new Plateau();
        plateau.setId(plateauResponse.getPlateauId());
        plateau.setMaxRoverNumber(plateauResponse.getPlateauMaxRover());
        plateau.setMaxXCoordinate(plateauResponse.getPlateauMaxRow());
        plateau.setMaxYCoordinate(plateauResponse.getPlateauMaxCol());
        plateau.setRoverId(plateauResponse.getRoverId());
        return plateau;
    }
}
