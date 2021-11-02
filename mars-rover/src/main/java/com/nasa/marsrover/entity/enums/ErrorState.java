package com.nasa.marsrover.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorState {
    VALID(0),
    INVALID(-1);
    private final int value;
}
