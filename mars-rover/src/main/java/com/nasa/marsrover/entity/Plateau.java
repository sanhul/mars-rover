package com.nasa.marsrover.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PLATEAU_DETAILS")
public class Plateau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;
    @Column(name = "MAX_X_COORD")
    private Integer maxXCoordinate;
    @Column(name = "MAX_Y_COORD")
    private Integer maxYCoordinate;
    @Column(name = "ROVER_ID")
    private String roverId;
    @Column(name = "MAX_ROVER_NUM")
    private Integer maxRoverNumber;
}
