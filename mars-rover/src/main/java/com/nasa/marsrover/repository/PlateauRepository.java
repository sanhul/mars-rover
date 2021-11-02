package com.nasa.marsrover.repository;

import com.nasa.marsrover.entity.Plateau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlateauRepository extends JpaRepository<Plateau, String> {
}
