package com.main.drone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, name = "name")
    @JsonProperty("name")
    private String name;

    @Column(nullable = false)
    @JsonProperty("weight")
    private int weight;

    @Column (name = "code", nullable = false)
    @JsonProperty("code")
    private String code;

    @Column(nullable = false)
    @JsonProperty("image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id")
    @JsonIgnore
    private Drone drone;
}
