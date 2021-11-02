package com.nasa.marsrover.service;

import com.nasa.marsrover.entity.Plateau;
import com.nasa.marsrover.entity.request.PlateauUpdateRequest;
import com.nasa.marsrover.entity.response.EntirePlateauResponse;
import com.nasa.marsrover.entity.response.PlateauResponse;

public interface PlateauService {
    Plateau createPlateau(Integer maxXCoord, Integer maxYCoord);
    PlateauResponse updatePlateauForRoverCreation(PlateauUpdateRequest plateauUpdateRequest);
    EntirePlateauResponse updatePlateauForRoverMotion(PlateauResponse plateauResponse);
}
