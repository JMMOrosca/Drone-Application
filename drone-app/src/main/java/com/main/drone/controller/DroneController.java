package com.main.drone.controller;

import com.main.drone.model.Drone;
import com.main.drone.model.Medication;
import com.main.drone.service.impl.DroneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/drones")
@RequiredArgsConstructor
public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping
    public ResponseEntity<Drone> registerDrone(@RequestBody @Valid Drone drone){
        Drone newDrone = droneService.registerDrone(drone);
        return new ResponseEntity<>(newDrone, HttpStatus.CREATED);
    }
    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones(){
        return ResponseEntity.ok(droneService.getAvailableDrones());
    }
    @PostMapping("/{droneId}/load")
    public ResponseEntity<Drone> loadDrone(@PathVariable Long droneId, @RequestBody @Valid List<Medication> medications){
        return ResponseEntity.ok(droneService.loadMedications(droneId, medications));
    }
    @GetMapping("/{droneId}/medications")
    public ResponseEntity<List<Medication>> getMedications(@PathVariable Long droneId){
        return ResponseEntity.ok(droneService.getLoadedMedications(droneId));
    }
    @GetMapping("/{droneId}/battery")
    public ResponseEntity<Double> checkBattery(@PathVariable Long droneId){
        return ResponseEntity.ok(droneService.getDroneBattery(droneId));
    }
}
