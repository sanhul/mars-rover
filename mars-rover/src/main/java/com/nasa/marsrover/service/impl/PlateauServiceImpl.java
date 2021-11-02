package com.nasa.marsrover.service.impl;

import com.nasa.marsrover.entity.Plateau;
import com.nasa.marsrover.entity.request.PlateauUpdateRequest;
import com.nasa.marsrover.entity.response.EntirePlateauResponse;
import com.nasa.marsrover.entity.response.PlateauResponse;
import com.nasa.marsrover.entity.response.RoverResponse;
import com.nasa.marsrover.repository.PlateauRepository;
import com.nasa.marsrover.service.PlateauService;
import com.nasa.marsrover.service.RoverService;
import com.nasa.marsrover.service.utils.RequestResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.nasa.marsrover.service.utils.StringHelper.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PlateauServiceImpl implements PlateauService {
    private final RoverService roverService;
    private final PlateauRepository plateauRepository;
    private final RequestResponseHelper requestResponseHelper;
    private static final Integer INITIAL_POSITION = 0;

    @Override
    public Plateau createPlateau(Integer maxXCoord, Integer maxYCoord) {
        log.info("Mars Rover - Plateau Creation");
        if (isPlateauRectangular(maxXCoord, maxYCoord) && isPlateauValid(maxXCoord, maxYCoord)) {
            var plateau = new Plateau();
            plateau.setMaxXCoordinate(maxXCoord);
            plateau.setMaxYCoordinate(maxYCoord);
            plateau.setRoverId(null);
            plateau.setMaxRoverNumber(maxXCoord * maxYCoord);
            return plateauRepository.save(plateau);
        }
        return null;
    }

    @Override
    public PlateauResponse updatePlateauForRoverCreation(PlateauUpdateRequest plateauUpdateRequest) {
        log.info("Mars Rover - Rover Landing and Plateau Update");
        var plateau = plateauRepository.getById(plateauUpdateRequest.getPlateauId());
        var roverIdSb = new StringBuilder();
        if (Objects.nonNull(plateau.getRoverId())) {
            roverIdSb.append(plateau.getRoverId()).append(squareBracket);
        }
        var roverListTobeCreated = requestResponseHelper.buildRoverRequest(plateauUpdateRequest.getRoverRequestDetail());
        roverListTobeCreated.forEach(roverToBeCreated -> {
            if (checkIsPositionValid(roverToBeCreated.getCoordinateX(), roverToBeCreated.getCoordinateY(), plateau)) {
                roverIdSb.append(roverService.createRover(plateau.getId(), roverToBeCreated)).append(squareBracket);
            }
        });
        plateau.setRoverId(roverIdSb.toString());
        plateauRepository.save(plateau);
        return requestResponseHelper.buildPlateauResponse(plateau);
    }

    @Override
    public EntirePlateauResponse updatePlateauForRoverMotion(PlateauResponse plateauResponse) {
        log.info("Mars Rover - Plateau With Rover motion");
        var entirePlateauResponse = new EntirePlateauResponse();
        List<RoverResponse> roverResponseList = new ArrayList<>();
        var plateau = requestResponseHelper.buildPlateau(plateauResponse);
        entirePlateauResponse.setPlateauResponse(plateauResponse);
        var roverIdArr = plateauResponse.getRoverId().split(squareBracket);
        Arrays.stream(roverIdArr).forEach(roverIdToBeMoved -> {
            var updatedRover = roverService.updateRover(roverIdToBeMoved, plateau);
            roverResponseList.add(requestResponseHelper.buildRoverResponse(updatedRover));
        });
        entirePlateauResponse.setRoverResponseList(roverResponseList);
        return entirePlateauResponse;
    }
    private boolean isPlateauRectangular(Integer x, Integer y) {
        return !(x.equals(y));
    }

    private boolean isPlateauValid(Integer x, Integer y) {
        return (x > INITIAL_POSITION && y > INITIAL_POSITION);
    }

    private boolean checkIsPositionValid(Integer currentX, Integer currentY, Plateau currentPlateau) {
        return (currentX >= INITIAL_POSITION && currentY >= INITIAL_POSITION && currentX <= currentPlateau.getMaxXCoordinate()
                && currentY <= currentPlateau.getMaxYCoordinate());
    }

}
