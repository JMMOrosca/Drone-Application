package com.main.drone.component;

import com.main.drone.enums.Model;
import com.main.drone.enums.State;
import com.main.drone.model.Drone;
import com.main.drone.model.Medication;
import com.main.drone.repo.DroneRepo;
import com.main.drone.repo.MedicationRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DroneRepo droneRepo;
    private final MedicationRepo medicationRepo;

    public DataInitializer(DroneRepo droneRepo, MedicationRepo medicationRepo) {
        this.droneRepo = droneRepo;
        this.medicationRepo = medicationRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        medicationRepo.save(new Medication(null, "Aspirin", 325, "ASP-325"," ", null));
        medicationRepo.save(new Medication(null, "Amoxicillin", 500, "AMOX-500"," ", null));
        medicationRepo.save(new Medication(null, "Ibuprofen", 200, "IBUP-200"," ", null));
        medicationRepo.save(new Medication(null, "Lisinopril", 10, "LISI-010"," ", null));
        medicationRepo.save(new Medication(null, "Cetirizine", 5, "CETE-005"," ", null));

        Model lmodel = Model.Lightweight;
        Model mmodel = Model.Middleweight;
        Model cmodel = Model.Cruiserweight;
        Model hmodel = Model.Heavyweight;

        droneRepo.save(new Drone(null,"DRN-L01-A1001", lmodel,lmodel.getCapacity(), 0.95, State.IDLE, List.of()));
        droneRepo.save(new Drone(null,"DRN-M01-B2001", mmodel,mmodel.getCapacity(), 0.75, State.IDLE, List.of()));
        droneRepo.save(new Drone(null,"DRN-C01-C3001", cmodel,cmodel.getCapacity(), 0.25, State.IDLE, List.of()));
        droneRepo.save(new Drone(null,"DRN-H01-D4001", hmodel,hmodel.getCapacity(), 0.5, State.IDLE, List.of()));
    }
}
