package com.example.elevator.service;

import com.example.elevator.dao.ElevatorRepository;
import com.example.elevator.dao.TripRepository;
import com.example.elevator.exception.InvalidSystemStateException;
import com.example.elevator.model.Elevator;
import com.example.elevator.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElevatorService {

    private final ElevatorRepository elevatorRepository;

    private final TripRepository tripRepository;

    @Autowired
    public ElevatorService(ElevatorRepository elevatorRepository, TripRepository tripRepository) {
        this.elevatorRepository = elevatorRepository;
        this.tripRepository = tripRepository;
    }

    public List<Elevator> getElevators() {
        return (List) elevatorRepository.findAll();
    }

    public List<Elevator> getIdleElevators() {
        List<Elevator> elevators = (List) elevatorRepository.findAll();
        List<Elevator> idleElevators = elevators.stream()
                .filter(x -> x.getTripQueueSize() == 0).collect(Collectors.toList());
        return idleElevators;
    }

    @Transactional
    public Elevator assignAvailableElevator(Trip trip) {
        List<Elevator> elevators = (List) elevatorRepository.findAll();
        if (elevators.isEmpty()) {
            throw new InvalidSystemStateException("No elevators are present - cannot assign elevator");
        }
        List<Elevator> sortedElevators = getElevatorsWithLowestNumberOfActiveTrips(elevators);

        Elevator elevatorWithLowestNumberOfJobs = getElevatorWithLowestDistanceToStartFloor(sortedElevators,
                Integer.valueOf(trip.getStartFloor().toString()));

        elevatorWithLowestNumberOfJobs.getTripQueue().add(trip);
        tripRepository.save(trip);
        elevatorRepository.save(elevatorWithLowestNumberOfJobs);
        return elevatorWithLowestNumberOfJobs;
    }

    private List<Elevator> getElevatorsWithLowestNumberOfActiveTrips(List<Elevator> elevators) {
        return elevators.stream()
                .filter(x -> x.getActiveTripsQueueSize().equals(elevators.stream()
                        .min(Comparator.comparing(Elevator::getActiveTripsQueueSize)).get()
                        .getActiveTripsQueueSize()))
                .collect(Collectors.toList());
    }

    private Elevator getElevatorWithLowestDistanceToStartFloor(List<Elevator> elevators, Integer startFloor) {
        Long startFloorInt = Long.valueOf(startFloor.toString());
        return elevators.stream()
                .min(Comparator.comparingLong(i -> Math.abs(i.getCurrentFloor() - (startFloorInt))))
                .orElse(null);
    }
}
