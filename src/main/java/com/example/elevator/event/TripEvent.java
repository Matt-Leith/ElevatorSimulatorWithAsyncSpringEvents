package com.example.elevator.event;

import com.example.elevator.model.Trip;
import org.springframework.context.ApplicationEvent;

public class TripEvent extends ApplicationEvent {
    private Trip trip;

    public TripEvent(Object source, Trip trip) {
        super(source);
        this.trip = trip;
    }
    public Trip getTrip() {
        return trip;
    }
}
