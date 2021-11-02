package com.nasa.marsrover.repository;

import com.nasa.marsrover.entity.Rover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoverRepository extends JpaRepository<Rover, String> {
    Optional<Rover> findByCoordinateXAndCoordinateYAndPlateauRef(Integer coordinateX,Integer coordinateY,String plateauRef);
    List<Rover> findByPlateauRef(String plateauRef);
}