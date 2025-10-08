package com.main.drone.enums;

public enum Model {
    Lightweight (250, 20.0),
    Middleweight (500, 30.0),
    Cruiserweight (750, 40.0),
    Heavyweight (1000, 50.0);

    private final int capacity;
    private final double batteryConsumed;

    Model(Integer capacity, Double batteryConsumed){
        this.capacity = capacity;
        this.batteryConsumed = batteryConsumed;
    }

    public Integer getCapacity(){
        return capacity;
    }

    public Double getBatterConsumed(){
        return batteryConsumed;
    }
}
