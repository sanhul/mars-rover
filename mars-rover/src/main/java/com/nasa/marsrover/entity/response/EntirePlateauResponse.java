package com.nasa.marsrover.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntirePlateauResponse {
    @JsonProperty("plateau")
    private PlateauResponse plateauResponse;
    @JsonProperty("allRovers")
    private List<RoverResponse> roverResponseList;
}
