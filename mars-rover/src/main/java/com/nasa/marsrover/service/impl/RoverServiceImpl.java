package com.nasa.marsrover.service.impl;

import com.nasa.marsrover.entity.Plateau;
import com.nasa.marsrover.entity.Rover;
import com.nasa.marsrover.entity.enums.Control;
import com.nasa.marsrover.entity.enums.ErrorState;
import com.nasa.marsrover.repository.RoverRepository;
import com.nasa.marsrover.service.MotionService;
import com.nasa.marsrover.service.RoverService;
import com.nasa.marsrover.service.utils.RequestResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.nasa.marsrover.service.utils.PredicateSet.doesNotcontainM;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RoverServiceImpl implements RoverService {
    private final MotionService motionService;
    private final RoverRepository roverRepository;
    private final RequestResponseHelper requestResponseHelper;

    @Override
    public String createRover(String plateauId, Rover rover) {
        var roverTobeCreated = new Rover();
        var existingRover = roverRepository.findByCoordinateXAndCoordinateYAndPlateauRef(rover.getCoordinateX()
                , rover.getCoordinateY(), plateauId);
        if (existingRover.isEmpty()) {
            log.info("Mars Rover - Rover Creation");
            roverTobeCreated.setPlateauRef(plateauId);
            roverTobeCreated.setCoordinateX(rover.getCoordinateX());
            roverTobeCreated.setCoordinateY(rover.getCoordinateY());
            roverTobeCreated.setFacingDirection(rover.getFacingDirection());
            roverTobeCreated.setMotionCommand(rover.getMotionCommand());
            roverTobeCreated.setMoveType("");
            roverRepository.save(roverTobeCreated);
            return roverTobeCreated.getId();
        } else {
            log.info("Mars Rover - Possible Crash!");
            //to-do-audit
            return existingRover.get().getId();
        }
    }

    @Override
    public Rover getNewRoverPosition(Rover fetchedRover, Plateau plateau) {
        log.info("Mars Rover - Initiate Rover Tentative Move");
        var newRoverState = requestResponseHelper.buildRoverForState(fetchedRover);
        if (Boolean.TRUE.equals(motionService.validateControlCommand(newRoverState.getMotionCommand()))) {
            String[] strings = newRoverState.getMotionCommand().split("");
            if (!newRoverState.getMotionCommand().contains(Control.M.toString())) {
                newRoverState.setMoveType("spin");
                var countSpin = Arrays.stream(strings).filter(doesNotcontainM).count();
                String newFacingDirection = motionService.getOverallSpin(countSpin, newRoverState.getFacingDirection());
                newRoverState.setFacingDirection(newFacingDirection);
                return newRoverState;
            } else {
                var changedRover = roverCheckSpinAndMove(strings, newRoverState);
                if (Boolean.TRUE.equals(motionService.checkMotionIsNotPrime(changedRover.getCoordinateX(), changedRover.getCoordinateY()))
                        && (Boolean.TRUE.equals(checkWithinBoundary(changedRover.getCoordinateX(), changedRover.getCoordinateY(), plateau)))
                        && (roverRepository.findByCoordinateXAndCoordinateYAndPlateauRef(changedRover.getCoordinateX()
                        , changedRover.getCoordinateY(), plateau.getId()).isEmpty())) {
                    return changedRover;
                } else {
                    log.info("Mars Rover - Either Prime or occupied or out of bound move");
                    return null;
                }

            }

        }
        return null;
    }

    private Rover roverCheckSpinAndMove(String[] commandArr, Rover existingRover) {
        Arrays.stream(commandArr).forEach(s -> {
            if (s.equals(Control.L.toString()) || s.equals(Control.R.toString())) {
                String newFacingDirection = motionService.spinRoverOpposite(existingRover.getFacingDirection());
                existingRover.setFacingDirection(newFacingDirection);
            } else {
                var intermediateRover = motionService.moveAlongAxes(existingRover.getCoordinateX(), existingRover.getCoordinateY(), existingRover.getFacingDirection());
                existingRover.setCoordinateX(intermediateRover.getCoordinateX());
                existingRover.setCoordinateY(intermediateRover.getCoordinateY());
            }
        });
        existingRover.setId(existingRover.getId());
        return existingRover;
    }

    @Override
    public Rover updateRover(String roverId, Plateau plateau) {
        log.info("Mars Rover - RoverUpdate");
        var fetchedRover = getRoverDetailByID(roverId);
        var movedRover = getNewRoverPosition(fetchedRover, plateau);
        if (Objects.isNull(movedRover)) {
            return fetchedRover;
        } else {
            fetchedRover.setCoordinateX(movedRover.getCoordinateX());
            fetchedRover.setCoordinateY(movedRover.getCoordinateY());
            fetchedRover.setMoveType(movedRover.getMoveType());
            fetchedRover.setFacingDirection(movedRover.getFacingDirection());
            return roverRepository.save(fetchedRover);
        }
    }


    @Override
    public List<Rover> getRoverByPlateauRef(String plateauRef) {
        log.info("Mars Rover - get Existing Rover in the Plateau");
        return roverRepository.findByPlateauRef(plateauRef);
    }

    @Override
    public Rover getRoverDetailByID(String roverId) {
        return roverRepository.getById(roverId);
    }

    private Boolean checkWithinBoundary(Integer currentX, Integer currentY, Plateau currentPlateau) {
        return ((currentX >= 0 && currentX <= currentPlateau.getMaxXCoordinate())
                && (currentY >= 0 && currentY <= currentPlateau.getMaxYCoordinate()));
    }

    private boolean checkCoordinatesOccupied(Integer newX, Integer newY, Plateau currentPlateau) {
        return (newX <= currentPlateau.getMaxXCoordinate() && newY <= currentPlateau.getMaxYCoordinate());
    }
}
