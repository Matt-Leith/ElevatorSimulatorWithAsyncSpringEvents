package com.example.elevator.service;

import com.example.elevator.dao.ElevatorRepository;
import com.example.elevator.dao.TripRepository;
import com.example.elevator.model.Elevator;
import com.example.elevator.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

@Service
public class TripService {
    private TripRepository tripRepository;
    private ElevatorRepository elevatorRepository;
    private ElevatorService elevatorService;

    @Autowired
    public TripService(TripRepository tripRepository, ElevatorRepository elevatorRepository,
                       ElevatorService elevatorService) {
        this.tripRepository = tripRepository;
        this.elevatorRepository = elevatorRepository;
        this.elevatorService = elevatorService;
    }

    public void takeLowPriorityTrip(Trip trip) {
        List<Elevator> idleElevators = elevatorService.getIdleElevators();
        for (Elevator idleElevator : idleElevators) {
            trip.setStartFloor(idleElevator.getCurrentFloor());
            Long numberOfFloorsToTravelToDestinationFloor = abs(trip.getStartFloor() - trip.getDestinationFloor());
            System.out.println(String.format("Moving idle elevator %s", idleElevator.getName()));
            moveElevatorToFloor(trip.getStartFloor(), idleElevator);
            finaliseTrip(trip, numberOfFloorsToTravelToDestinationFloor);
        }
    }

    public void takeTrip(Trip trip) {
        Elevator assignedElevator = elevatorService.assignAvailableElevator(trip);

        Long elevatorFloorPriorToTrip = assignedElevator.getCurrentFloor();

        moveElevatorToFloor(trip.getStartFloor(), assignedElevator);

        trip.setArrivalAtPickupTime(new Date());
        tripRepository.save(trip);

        Long numberOfFloorsToTravelToDestinationFloor = abs(trip.getStartFloor() - trip.getDestinationFloor());
        Long numberOfFloorsToTravelledToStartFloor = abs(trip.getStartFloor() - elevatorFloorPriorToTrip);
        Long currentFloor = trip.getStartFloor();

        System.out.println("Elevator has arrived at start floor {" + currentFloor
                + "} travelling {" + numberOfFloorsToTravelledToStartFloor + "} floors");

        moveElevatorToFloor(trip.getDestinationFloor(), assignedElevator);

        finaliseTrip(trip, numberOfFloorsToTravelToDestinationFloor);
    }

    private void finaliseTrip(Trip trip, Long numberOfFloorsToTravelToDestinationFloor) {
        System.out.println("Elevator has arrived at destination floor {" + trip.getDestinationFloor()
                + "} travelling {" + numberOfFloorsToTravelToDestinationFloor + "} floors");
        trip.setArrivalAtDestinationTime(new Date());
        trip.calculateAndSetTotalTripTime();
        tripRepository.save(trip);
    }

    private void moveElevatorToFloor(Long destinationFloor, Elevator assignedElevator) {
        Long numberOfFloorsToTravel = abs(assignedElevator.getCurrentFloor() - destinationFloor);
        System.out.println(String.format("Elevator: %s is at floor: %s at the beginning of the journey",
                assignedElevator.getName(), assignedElevator.getCurrentFloor()));
        for (int i = 0; i < numberOfFloorsToTravel; i++) {
            Long currentFloorOfElevator = assignedElevator.getCurrentFloor();
            if (currentFloorOfElevator < destinationFloor) {
                currentFloorOfElevator++;
            } else if (currentFloorOfElevator > destinationFloor) {
                currentFloorOfElevator--;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assignedElevator.setCurrentFloor(currentFloorOfElevator);
            elevatorRepository.save(assignedElevator);
            System.out.println(String.format("Elevator: %s is currently at floor: %s ", assignedElevator.getName(),
                    assignedElevator.getCurrentFloor()));
        }
    }

    public Double getAverageTripTimes() {
        List<Trip> trips = (List) tripRepository.findAll();
        return trips.stream().mapToDouble(Trip::getTotalTripTimeDouble).average().orElse(Double.NaN);
    }
}
