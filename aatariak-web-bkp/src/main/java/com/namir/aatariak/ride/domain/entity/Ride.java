package com.namir.aatariak.ride.domain.entity;

public class Ride {
    private String userId;
    private String name;
    private Double startGeo;
    private Double endGeo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStartGeo() {
        return startGeo;
    }

    public void setStartGeo(Double startGeo) {
        this.startGeo = startGeo;
    }

    public Double getEndGeo() {
        return endGeo;
    }

    public void setEndGeo(Double endGeo) {
        this.endGeo = endGeo;
    }
}
