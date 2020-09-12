package com.example.elevator.service;

import com.example.elevator.dao.TripRepository;
import com.example.elevator.event.TripEvent;
import com.example.elevator.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {
    public static final double RATIO_TO_MOVE_ELEVATOR = 0.75;
    private TripRepository tripRepository;
    private TripService tripService;

    @Autowired
    public AnalyticsService(TripRepository tripRepository, TripService tripService) {
        this.tripRepository = tripRepository;
        this.tripService = tripService;
    }

    public void runAnalytics(TripEvent event) {
        List<Trip> trips = (List) tripRepository.findAll();

        if (trips.size() < 10) {
            return;
        }

        Map<Long, Integer> floorMap = new HashMap();
        for (Trip trip : trips) {
            if (!floorMap.containsKey(trip.getStartFloor())) {
                floorMap.put(trip.getStartFloor(), 0);
            }
            Integer oldValue = floorMap.get(trip.getStartFloor());
            floorMap.replace(trip.getStartFloor(), oldValue, oldValue + 1);
        }

        for (Long floor : floorMap.keySet()) {
            double percentageOfTripsWithAStartFloorAtThisFloor = floorMap.get(floor) / trips.size();
            if (percentageOfTripsWithAStartFloorAtThisFloor > RATIO_TO_MOVE_ELEVATOR) {
                moveIdleElevatorToThisFloor(floor);
            }
        }
    }

    private void moveIdleElevatorToThisFloor(Long floor) {
        Trip trip = new Trip();
        trip.setRequestTime(new Date());
        trip.setStartFloor(floor);
        trip.setDestinationFloor(floor);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("Idle elevator is being moved to floor %s due to identification "
                + "of a current trend in elevator use", floor));

        tripService.takeTrip(trip);
    }
}
