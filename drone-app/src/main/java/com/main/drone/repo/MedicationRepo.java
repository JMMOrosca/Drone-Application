package com.main.drone.repo;

import com.main.drone.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepo extends JpaRepository<Medication,Long> {
    List<Medication> findByDroneId(Long droneId);
}
