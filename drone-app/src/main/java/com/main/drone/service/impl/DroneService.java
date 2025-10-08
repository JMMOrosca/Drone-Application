package com.main.drone.service.impl;

import com.main.drone.enums.Model;
import com.main.drone.enums.State;
import com.main.drone.model.Drone;
import com.main.drone.model.Medication;
import com.main.drone.repo.DroneRepo;
import com.main.drone.repo.MedicationRepo;
import com.main.drone.service.IDroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneService implements IDroneService {

    @Autowired
    private DroneRepo droneRepo;

    @Autowired
    private MedicationRepo medicationRepo;

    @Override
    public Drone registerDrone(Drone drone) {
        if(droneRepo.findById(drone.getId()).isPresent()){
            throw new RuntimeException("Drone is already registered" + drone.getId());
        }
        drone.setBattery(100.0);
        drone.setState(State.IDLE);
        return droneRepo.save(drone);
    }

    @Override
    public Drone loadMedications(Long droneId, List<Medication> medications) {
        Drone drone = droneRepo.findById(droneId)
                .orElseThrow(() -> new RuntimeException("Drone not found"));

        if(drone.getBattery() < 0.25)
            throw new RuntimeException("Battery too low to load medication");
        int totalWeight = drone.getMedications()
                .stream()
                .mapToInt(Medication::getWeight)
                .sum() +
                medications.stream().mapToInt(Medication::getWeight).sum();

        if(totalWeight > drone.getWeight())
            throw new RuntimeException("Exceed drone weight");

        for(Medication med : medications){
            med.setDrone(drone);
        }
        drone.getMedications().addAll(medications);
        drone.setState(State.LOADING);

        medicationRepo.saveAll(medications);
        return droneRepo.save(drone);
    }

    @Override
    public List<Medication> getLoadedMedications(Long droneId) {
        Drone drone = droneRepo.findById(droneId)
                .orElseThrow(() -> new RuntimeException("Drone Not Found"));
        return drone.getMedications();
    }

    @Override
    public List<Drone> getAvailableDrones() {
        List<Drone> idleDrones = droneRepo.findByState(State.IDLE);
        return idleDrones.stream()
                .filter(drone -> drone.getBattery() >= 0.25)
                .collect(Collectors.toList());
    }

    @Override
    public double getDroneBattery(Long droneId) {
        Drone drone = droneRepo.findById(droneId)
                .orElseThrow(() -> new RuntimeException("Drone not found"));
        return drone.getBattery();
    }
}
