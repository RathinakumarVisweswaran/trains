package com.challenge.tw.trains.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TrainNetwork {

    Map<String, Station> stations = new HashMap();

    public Collection<Station> getStations() {
        return stations.values();
    }

    public boolean addRoute(Route route) {
        updateStationMapWithStationsFrom(route);
        return startStationOf(route)
                .addRouteTo(endStationOf(route), route.getDistance());
    }

    private Station endStationOf(Route route) {
        return stations.get(route.getEndStationName());
    }

    private Station startStationOf(Route route) {
        return stations.get(route.getStartStationName());
    }

    private void updateStationMapWithStationsFrom(Route route) {
        updateStationMapWith(route.getStartStationName());
        updateStationMapWith(route.getEndStationName());
    }

    private void updateStationMapWith(String stationName) {
        stations.putIfAbsent(stationName, new Station(stationName));
    }

    public Optional<Station> getStationByName(String stationName) {
        return Optional.ofNullable(stations.get(stationName));
    }
}
