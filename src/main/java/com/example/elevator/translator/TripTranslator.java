package com.example.elevator.translator;

import com.example.elevator.model.Trip;
import com.example.elevator.model.request.TripRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TripTranslator {
    public Trip translateToTrip(TripRequest tripRequest) {
        Trip trip = new Trip();
        trip.setStartFloor(tripRequest.getStartFloor());
        trip.setDestinationFloor(tripRequest.getDestinationFloor());
        trip.setRequestTime(new Date());
        return trip;
    }
}
