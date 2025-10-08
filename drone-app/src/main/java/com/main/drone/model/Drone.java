package com.main.drone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.main.drone.enums.Model;
import com.main.drone.enums.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serialNo.", length= 100, nullable = false)
    @JsonProperty("serialNo.")
    private String serialNumber;

    @Column(nullable = false)
    @JsonProperty("model")
    @Enumerated(EnumType.STRING)
    private Model model;

    @Column(nullable = false)
    @JsonProperty("weight")
    private int weight;

    @Column(nullable = false)
    @JsonProperty("battery")
    private double battery;

    @Column(nullable = false)
    @JsonProperty("state")
    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonProperty("medications")
    private List<Medication> medications = new ArrayList<>();

}
