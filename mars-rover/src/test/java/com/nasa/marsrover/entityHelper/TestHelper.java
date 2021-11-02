package com.nasa.marsrover.entityHelper;

import com.nasa.marsrover.entity.Plateau;
import com.nasa.marsrover.entity.Rover;
import com.nasa.marsrover.entity.enums.CardinalPoint;
import com.nasa.marsrover.entity.request.PlateauUpdateRequest;
import com.nasa.marsrover.entity.response.EntirePlateauResponse;
import com.nasa.marsrover.entity.response.PlateauResponse;
import com.nasa.marsrover.entity.response.RoverResponse;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {
    public static Plateau buildPlateau(Integer maxX, Integer maxy) {
        var testPlateau = new Plateau();
        testPlateau.setId("1");
        testPlateau.setMaxXCoordinate(maxX);
        testPlateau.setMaxYCoordinate(maxy);
        testPlateau.setMaxRoverNumber(maxX * maxy);
        testPlateau.setRoverId("1");
        return testPlateau;
    }

    public static Plateau buildRoverPlateau(Rover rover, Plateau plateau) {
        plateau.setRoverId(rover.getId());
        return plateau;
    }

    public static Rover buildRover(Integer x, Integer y, Enum<CardinalPoint> facing) {
        var rover = new Rover();
        rover.setId("1");
        rover.setCoordinateX(x);
        rover.setCoordinateY(y);
        rover.setFacingDirection(facing.toString());
        rover.setMotionCommand("LMRRM");
        return rover;
    }

    public static Rover updateRover(Rover rover, Integer newX, Integer newY, Enum<CardinalPoint> newFacing) {
        rover.setId("1");
        rover.setCoordinateX(newX);
        rover.setCoordinateY(newY);
        rover.setFacingDirection(newFacing.toString());
        return rover;
    }

    public static PlateauUpdateRequest buildPlateauUpdateRequest(Plateau plateau) {
        PlateauUpdateRequest plateauUpdateRequest = new PlateauUpdateRequest();
        plateauUpdateRequest.setPlateauId(plateau.getId());
        plateauUpdateRequest.setRoverRequestDetail("");
        return plateauUpdateRequest;
    }

    public static EntirePlateauResponse buildEntireResponse(Plateau plateau, Rover rover) {
        EntirePlateauResponse entirePlateauResponse = new EntirePlateauResponse();
        entirePlateauResponse.setPlateauResponse(buildPlateauResponse(plateau));
        entirePlateauResponse.setRoverResponseList(buildRoverResponseList(rover));
        return entirePlateauResponse;
    }

    public static PlateauResponse buildPlateauResponse(Plateau plateau) {
        PlateauResponse plateauResponse = new PlateauResponse();
        plateauResponse.setPlateauId(plateau.getId());
        plateauResponse.setPlateauMaxRover(plateau.getMaxRoverNumber());
        plateauResponse.setPlateauMaxRow(plateau.getMaxXCoordinate());
        plateauResponse.setPlateauMaxCol(plateau.getMaxYCoordinate());
        plateauResponse.setRoverId(plateau.getRoverId());
        return plateauResponse;
    }

    public static List<RoverResponse> buildRoverResponseList(Rover rover) {
        List<RoverResponse> roverResponseList = new ArrayList<>();
        roverResponseList.add(buildRoverResponse(rover));
        return roverResponseList;
    }

    public static RoverResponse buildRoverResponse(Rover rover) {
        RoverResponse roverResponse = new RoverResponse();
        roverResponse.setRoverX(rover.getCoordinateX());
        roverResponse.setRoverY(rover.getCoordinateY());
        roverResponse.setRoverFacing(rover.getFacingDirection());
        return roverResponse;
    }
}
