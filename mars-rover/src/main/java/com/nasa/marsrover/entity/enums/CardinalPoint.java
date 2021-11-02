package com.nasa.marsrover.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardinalPoint {
    N(0),
    S(1),
    E(2),
    W(3);
    private final int towards;
}
