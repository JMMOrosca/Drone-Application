package com.main.drone.service;

import com.main.drone.model.Drone;
import com.main.drone.model.Medication;

import java.util.List;


public interface IDroneService {

    Drone registerDrone(Drone drone);
    Drone loadMedications(Long droneId, List<Medication> medications);
    List<Medication> getLoadedMedications(Long droneId);
    List<Drone> getAvailableDrones();
    double getDroneBattery(Long droneId);
}
