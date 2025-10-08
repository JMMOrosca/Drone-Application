package com.main.drone.repo;

import com.main.drone.enums.State;
import com.main.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DroneRepo extends JpaRepository<Drone, Long> {
    List<Drone> findByState(State state);
}
