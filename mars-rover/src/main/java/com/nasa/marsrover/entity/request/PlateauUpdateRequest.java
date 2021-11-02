package com.nasa.marsrover.entity.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlateauUpdateRequest {
    @JsonProperty("id")
    private  String plateauId;
    @JsonProperty("roverRequestDetails")
    private  String roverRequestDetail;
}
