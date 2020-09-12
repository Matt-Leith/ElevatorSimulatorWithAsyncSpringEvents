package com.example.elevator.model.request;

public class TripRequest {
    private Long startFloor;
    private Long destinationFloor;

    public Long getStartFloor() {
        return startFloor;
    }

    public void setStartFloor(Long startFloor) {
        this.startFloor = startFloor;
    }

    public Long getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(Long destinationFloor) {
        this.destinationFloor = destinationFloor;
    }
}
