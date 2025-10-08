package com.main.drone.enums;

import lombok.Getter;

@Getter
public enum State {
    IDLE("IDLE"),
    LOADING("LOADING"),
    LOADED("LOADED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    RETURNING("RETURNING");

    private final String state;

    State(String state) {
        this.state = state;
    }


}
