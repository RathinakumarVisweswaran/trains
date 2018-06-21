package com.challenge.tw.trains.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import static org.springframework.util.StringUtils.isEmpty;

@Getter
@EqualsAndHashCode
public class Route {
    private final String startStationName;
    private final String endStationName;
    private final int distance;

    private Route(String startStationName, String endStationName, int distance) {
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.distance = distance;
    }

    public static Route between(String startStationName, String endStationName, int distance) {
        if (isEmpty(startStationName) || endStationName == null)
            throw new IllegalArgumentException("invalid station name");
        if (distance < 0) throw new IllegalArgumentException("distance cannot be negative");
        return new Route(startStationName, endStationName, distance);
    }
}
