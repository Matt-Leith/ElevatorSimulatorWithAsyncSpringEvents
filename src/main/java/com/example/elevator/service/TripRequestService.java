package com.example.elevator.service;

import com.example.elevator.dao.TripRepository;
import com.example.elevator.event.TripEventPublisher;
import com.example.elevator.model.Trip;
import com.example.elevator.model.request.TripRequest;
import com.example.elevator.translator.TripTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TripRequestService {
    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripTranslator tripTranslator;

    @Autowired
    private TripEventPublisher tripEventPublisher;

    public Trip createTrip(TripRequest tripRequest) {
        Trip trip = tripTranslator.translateToTrip(tripRequest);
        trip.setRequestTime(new Date());
        Long tripId = tripRepository.save(trip).getId();
        tripEventPublisher.publishTripEvent(trip);
        return getTrip(tripId);
    }

    public Trip getTrip(Long tripId) {
        return tripRepository.findById(tripId).orElse(null);
    }

    public List<Trip> getTrips() {
        return (List) tripRepository.findAll();
    }
}
