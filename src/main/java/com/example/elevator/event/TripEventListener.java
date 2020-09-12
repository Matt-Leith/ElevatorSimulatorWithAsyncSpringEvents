package com.example.elevator.event;

import com.example.elevator.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TripEventListener implements ApplicationListener<TripEvent> {

    @Autowired
    private TripService tripService;

    @Override
    public void onApplicationEvent(TripEvent event) {
        tripService.takeTrip(event.getTrip());
    }
}
