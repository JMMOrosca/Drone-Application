package com.main.drone.service;


import com.main.drone.enums.State;
import com.main.drone.model.Drone;
import com.main.drone.model.Medication;
import com.main.drone.repo.DroneRepo;
import com.main.drone.repo.MedicationRepo;
import com.main.drone.service.impl.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DroneServiceTest {
    @Mock
    private DroneRepo droneRepo;

    @Mock
    private MedicationRepo medicationRepo;

    @InjectMocks
    private DroneService droneService;

    private Drone sampleDrone;
    private Medication sampleMedication;

    @BeforeEach
    void setUp() {
        // Initialize common objects before each test
        sampleDrone = new Drone();
        sampleDrone.setId(1L);
        sampleDrone.setSerialNumber("SN-001");
        sampleDrone.setBattery(0.95); // 95%
        sampleDrone.setWeight(500);
        sampleDrone.setState(State.IDLE);

        sampleMedication = new Medication();
        sampleMedication.setCode("MED-001");
        sampleMedication.setName("PainRelief");
        sampleMedication.setWeight(100);
    }
    @Test
    void registerDrone_success() {
        when(droneRepo.save(any(Drone.class))).thenReturn(sampleDrone);
        Drone registeredDrone = droneService.registerDrone(sampleDrone);
        assertNotNull(registeredDrone);
        assertEquals(sampleDrone.getId(), registeredDrone.getId());
        verify(droneRepo, times(1)).save(sampleDrone);
    }
    @Test
    void registerDrone_throwsExceptionOnNullInput() {
        assertThrows(NullPointerException.class, () -> {
            droneService.registerDrone(null);
        });
        verify(droneRepo, never()).save(any());
    }

    @Test
    void loadMedications_success() {
        List<Medication> medicationsToLoad = Arrays.asList(sampleMedication);
        when(droneRepo.findById(1L)).thenReturn(Optional.of(sampleDrone));
        when(droneRepo.save(any(Drone.class))).thenReturn(sampleDrone);
        Drone loadedDrone = droneService.loadMedications(1L, medicationsToLoad);
        assertNotNull(loadedDrone.getMedications());
        assertTrue(loadedDrone.getMedications().contains(sampleMedication));
        verify(droneRepo, times(1)).findById(1L);
        verify(droneRepo, times(1)).save(sampleDrone);
    }
    @Test
    void loadMedications_droneNotFound() {
        List<Medication> medicationsToLoad = Arrays.asList(sampleMedication);
        when(droneRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            droneService.loadMedications(2L, medicationsToLoad);
        });
        verify(droneRepo, never()).save(any());
    }
    @Test
    void loadMedications_lowBatteryFails() {
        sampleDrone.setBattery(0.20);
        List<Medication> medicationsToLoad = Arrays.asList(sampleMedication);
        when(droneRepo.findById(1L)).thenReturn(Optional.of(sampleDrone));
        assertThrows(RuntimeException.class, () -> {
            droneService.loadMedications(1L, medicationsToLoad);
        });
        verify(droneRepo, never()).save(any());
    }
    @Test
    void getLoadedMedications_success() {
        sampleDrone.setMedications(Arrays.asList(sampleMedication));
        when(droneRepo.findById(1L)).thenReturn(Optional.of(sampleDrone));
        List<Medication> medications = droneService.getLoadedMedications(1L);
        assertNotNull(medications);
        assertEquals(1, medications.size());
        assertEquals(sampleMedication.getCode(), medications.get(0).getCode());
        verify(droneRepo, times(1)).findById(1L);
    }
    @Test
    void getLoadedMedications_droneNotFound() {
        when(droneRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            droneService.getLoadedMedications(2L);
        });
    }
    @Test
    void getAvailableDrones_returnsOnlyIdleDronesWithSufficientBattery() {
        Drone availableDrone = new Drone();
        availableDrone.setId(1L);
        availableDrone.setState(State.IDLE);
        availableDrone.setBattery(0.80);

        Drone unavailableDroneLowBattery = new Drone();
        unavailableDroneLowBattery.setId(2L);
        unavailableDroneLowBattery.setState(State.IDLE);
        unavailableDroneLowBattery.setBattery(0.20);

        Drone unavailableDroneInTransit = new Drone();
        unavailableDroneInTransit.setId(3L);
        unavailableDroneInTransit.setState(State.DELIVERING);
        unavailableDroneInTransit.setBattery(0.90);

        List<Drone> allDrones = Arrays.asList(availableDrone, unavailableDroneLowBattery);
        when(droneRepo.findByState(State.IDLE)).thenReturn(allDrones);

        List<Drone> availableDrones = droneService.getAvailableDrones();
        assertEquals(1, availableDrones.size());
        assertEquals(availableDrone.getId(), availableDrones.get(0).getId());
        verify(droneRepo, times(1)).findByState(State.IDLE);
    }
    @Test
    void getDroneBattery_success() {
        when(droneRepo.findById(1L)).thenReturn(Optional.of(sampleDrone));
        double expectedBattery = sampleDrone.getBattery();
        double actualBattery = droneService.getDroneBattery(1L);
        assertEquals(expectedBattery, actualBattery, 0.001);
        verify(droneRepo, times(1)).findById(1L);
    }

    @Test
    void getDroneBattery_droneNotFound() {
        when(droneRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            droneService.getDroneBattery(2L);
        });
    }
}
