package com.example.elevator.dao;

import com.example.elevator.model.Elevator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElevatorRepository extends CrudRepository<Elevator, Long> {
}
