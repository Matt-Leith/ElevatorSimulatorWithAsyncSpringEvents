package com.example.elevator.event;

import com.example.elevator.service.AnalyticsService;
import com.example.elevator.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsEventListener implements ApplicationListener<TripEvent> {
    @Autowired
    private AnalyticsService analyticsService;

    @Override
    public void onApplicationEvent(TripEvent event) {
        analyticsService.runAnalytics(event);
    }
}
