package com.example.elevator.model.request;

import java.util.Date;

public class AnalyticsRequest {
    private Date timeRange;

    public Date getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(Date timeRange) {
        this.timeRange = timeRange;
    }
}
