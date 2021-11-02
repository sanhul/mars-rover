package com.nasa.marsrover.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlateauResponse {
    @JsonProperty("id")
    private String plateauId;
    @JsonProperty("maxXCoordinate")
    private Integer plateauMaxRow;
    @JsonProperty("maxYCoordinate")
    private Integer plateauMaxCol;
    @JsonProperty("maxRoverNumber")
    private Integer plateauMaxRover;
    @JsonProperty("roverId")
    private String roverId;
}
