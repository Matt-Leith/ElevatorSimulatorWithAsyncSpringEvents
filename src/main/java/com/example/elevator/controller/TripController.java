package com.example.elevator.controller;

import com.example.elevator.model.Trip;
import com.example.elevator.model.request.TripRequest;
import com.example.elevator.service.TripRequestService;
import com.example.elevator.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping()
public class TripController {
    @Autowired
    private TripRequestService tripRequestService;
    @Autowired
    private TripService tripService;

    @PostMapping(value = "createTrip")
    @ResponseBody
    public Trip createTrip(@RequestBody TripRequest tripRequest) {
        return tripRequestService.createTrip(tripRequest);
    }

    @RequestMapping(value = "getTrip", method = RequestMethod.GET)
    public Trip getTrip(Long tripId) {
        return tripRequestService.getTrip(tripId);
    }

    @RequestMapping(value = "getTrips", method = RequestMethod.GET)
    @ResponseBody
    public List<Trip> getTrips() {
        return tripRequestService.getTrips();
    }

    @RequestMapping(value = "getAverageTimeOfTrips", method = RequestMethod.GET)
    @ResponseBody
    public Double getAverageTimeOfTrips() {
        return tripService.getAverageTripTimes();
    }
}
