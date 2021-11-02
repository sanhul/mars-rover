package com.nasa.marsrover.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ROVER_DETAILS")
public class Rover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String  id;
    @Column(name = "PLATEAU_REF")
    private String  plateauRef;
    @Column(name = "X_COORD")
    private Integer coordinateX;
    @Column(name = "Y_COORD")
    private Integer coordinateY;
    @Column(name = "FACING")
    private String facingDirection;
    @Column(name = "MOTION_COMMAND")
    private String motionCommand;
    @Column(name = "MOVE_TYPE")
    private String moveType;
}
