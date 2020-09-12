package com.example.elevator.service;

import com.example.elevator.dao.ElevatorRepository;
import com.example.elevator.dao.TripRepository;
import com.example.elevator.model.Elevator;
import com.example.elevator.model.Trip;
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

    @InjectMocks
    private ElevatorService elevatorService = new ElevatorService();

    @Test
    public void elevatorWithLowestNumberOfJobsIsAssigned() throws Exception {
        Trip trip = new Trip();
        when(elevatorRepository.findAll()).thenReturn(emptyList());
        Elevator elevator = elevatorService.assignAvailableElevator(trip);
        assertThat(elevator.getTripQueue().size(), is(1));
    }
}