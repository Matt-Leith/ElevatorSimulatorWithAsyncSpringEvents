package com.example.elevator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long startFloor;
    private Long destinationFloor;
    private Date requestTime;
    private Date arrivalAtPickupTime;
    private Date arrivalAtDestinationTime;
    private String totalTripTime;

    public Long getStartFloor() {
        return startFloor;
    }

    public void setStartFloor(Long startFloor) {
        this.startFloor = startFloor;
    }

    public Long getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(Long destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getArrivalAtPickupTime() {
        return arrivalAtPickupTime;
    }

    public void setArrivalAtPickupTime(Date arrivalAtPickupTime) {
        this.arrivalAtPickupTime = arrivalAtPickupTime;
    }

    public Date getArrivalAtDestinationTime() {
        return arrivalAtDestinationTime;
    }

    public void setArrivalAtDestinationTime(Date arrivalAtDestinationTime) {
        this.arrivalAtDestinationTime = arrivalAtDestinationTime;
    }

    public Long getId() {
        return id;
    }

    public void calculateAndSetTotalTripTime() {
        ZonedDateTime requestDate = ZonedDateTime.ofInstant(requestTime.toInstant(), ZoneId.systemDefault());
        ZonedDateTime destinationDate = ZonedDateTime.ofInstant(arrivalAtDestinationTime.toInstant(), ZoneId.systemDefault());
        Duration duration = Duration.between(requestDate, destinationDate);
        System.out.println("Minutes: " + duration.toMinutes());
        totalTripTime = duration.toString();
    }

    public String getTotalTripTime() {
        return totalTripTime.replaceAll("[^\\d.]", "");
    }

    public Double getTotalTripTimeDouble() {
        return Double.valueOf(totalTripTime.replaceAll("[^\\d.]", ""));
    }
}
