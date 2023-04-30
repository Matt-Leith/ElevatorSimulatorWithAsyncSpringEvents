package com.example.elevator.service;

import com.example.elevator.dao.ElevatorRepository;
import com.example.elevator.dao.TripRepository;
import com.example.elevator.exception.InvalidSystemStateException;
import com.example.elevator.model.Elevator;
import com.example.elevator.model.Trip;
import com.sun.tools.javac.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ElevatorServiceTest {
    @Mock
    private ElevatorRepository elevatorRepository;

    @Mock
    private TripRepository tripRepository;

    private ElevatorService elevatorService;

    @Before
    public void setUp() {
        elevatorService = new ElevatorService(elevatorRepository, tripRepository);
    }

    @Test(expected = InvalidSystemStateException.class)
    public void assigningElevatorWhenNoneExistRaisesInvalidSystemStateException() {
        Trip trip = new Trip();
        trip.setStartFloor(1L);
        when(elevatorRepository.findAll()).thenReturn(emptyList());
        elevatorService.assignAvailableElevator(trip);
    }

    @Test
    public void elevatorWithLowestNumberOfJobsIsAssigned() {
        Trip trip = new Trip();
        trip.setStartFloor(1L);
        when(elevatorRepository.findAll()).thenReturn(List.of(new Elevator()));
        Elevator elevator = elevatorService.assignAvailableElevator(trip);
        assertThat(elevator.getTripQueue().size(), is(1));
    }
}