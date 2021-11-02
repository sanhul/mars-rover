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
public class RoverResponse {
    @JsonProperty("roverId")
    private String roverId;
    @JsonProperty("roverX")
    private Integer roverX;
    @JsonProperty("roverY")
    private Integer roverY;
    @JsonProperty("roverFacing")
    private String roverFacing;
}
