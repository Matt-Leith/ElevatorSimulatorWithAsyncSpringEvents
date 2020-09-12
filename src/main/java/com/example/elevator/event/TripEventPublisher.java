package com.example.elevator.event;

import com.example.elevator.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TripEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishTripEvent(final Trip trip) {
        System.out.println("Publishing Trip Event. ");
        TripEvent tripEvent = new TripEvent(this, trip);
        applicationEventPublisher.publishEvent(tripEvent);
    }
}
