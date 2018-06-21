package com.challenge.tw.trains.service;

import com.challenge.tw.trains.model.Route;
import com.challenge.tw.trains.model.Station;
import com.challenge.tw.trains.model.TrainNetwork;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import static java.util.stream.Collectors.toList;

@Service
public class SelfService {

    TrainNetwork network;

    public SelfService(TrainNetwork network) {
        this.network = network;
    }

    public boolean addRoute(Route route) {
        return network.addRoute(route);
    }

    public int getTripCountBetweenStationsWithMaxHop(String startStationName, String endStationName, int maxHop) {
        Station startStation = network.getStationByName(startStationName)
                .orElseThrow(() -> new IllegalArgumentException("invalid station name"));
        Station endStation = network.getStationByName(endStationName)
                .orElseThrow(() -> new IllegalArgumentException("invalid station name"));
        if (startStation == null || endStation == null)
            throw new IllegalArgumentException("invalid station name");
        return getTripCountBetweenStationsWithMaxHop(startStation, endStation, maxHop);
    }

    private int getTripCountBetweenStationsWithMaxHop(Station startStation, Station endStation, int maxHop) {
        if (maxHop == 0) return 0;
        int tripCount = 0;
        for (Station nextStation : startStation.getAllDestinations()) {
            if (nextStation == endStation) tripCount++;
            tripCount += getTripCountBetweenStationsWithMaxHop(nextStation, endStation, maxHop - 1);
        }
        return tripCount;
    }

    public int getTripCountBetweenStationsWithExactHop(String startStationName, String endStationName, int exactHop) {
        Station startStation = network.getStationByName(startStationName)
                .orElseThrow(() -> new IllegalArgumentException("invalid station name"));
        Station endStation = network.getStationByName(endStationName)
                .orElseThrow(() -> new IllegalArgumentException("invalid station name"));
        return getTripCountBetweenStationsWithExactHop(startStation, endStation, exactHop);
    }

    private int getTripCountBetweenStationsWithExactHop(Station startStation, Station endStation, int exactHop) {
        if (startStation == endStation && exactHop == 0) return 1;
        if (exactHop == 0) return 0;
        return startStation.getAllDestinations().stream()
                .mapToInt(nextStation ->
                        getTripCountBetweenStationsWithExactHop(nextStation, endStation, exactHop - 1))
                .sum();
    }


    public int getShortestTripDistanceAlongPath(String path) {
        List<Station> stationSeq = stationListFor(path);
        int nextIndex = 1;
        int distance = 0;
        while (nextIndex < stationSeq.size()) {
            Station currentStation = stationSeq.get(nextIndex - 1);
            Station nextStation = stationSeq.get(nextIndex++);
            distance += currentStation.getShortestDistanceTo(nextStation)
                    .orElseThrow(() -> new IllegalStateException("NO SUCH ROUTE"));
        }
        return distance;
    }

    private List<Station> stationListFor(String path) {
        return Arrays.stream(path.trim().split("-"))
                .map(stationName -> network.getStationByName(stationName)
                        .orElseThrow(() -> new IllegalArgumentException("invalid station name")))
                .collect(toList());
    }

    public int getShortestTripDistanceBetweenStations(String startStationName, String endStationName) {
        Station startStation = network.getStationByName(startStationName)
                .orElseThrow(() -> new IllegalArgumentException("invalid station name"));
        Station endStation = network.getStationByName(endStationName)
                .orElseThrow(() -> new IllegalArgumentException("invalid station name"));
        return getShortestTripDistanceBetweenStations(
                startStation,
                endStation,
                new ArrayList(),
                0)
                .orElseThrow(() -> new IllegalStateException("NO SUCH TRIP"));
    }

    private OptionalInt getShortestTripDistanceBetweenStations(Station startStation,
                                                               Station endStation,
                                                               List<Station> visited,
                                                               int distanceCoveredSoFar) {
        if (startStation == endStation && distanceCoveredSoFar > 0) return OptionalInt.of(distanceCoveredSoFar);
        if (visited.contains(startStation)) return OptionalInt.empty();
        visited.add(startStation);
        OptionalInt shortestDist = startStation.getAllDestinations().stream()
                .map(nextStation -> getShortestTripDistanceBetweenStations(nextStation, endStation, visited,
                        distanceCoveredSoFar + startStation.getShortestDistanceTo(nextStation).getAsInt()))
                .filter(optionalShortestDistances -> optionalShortestDistances.isPresent())
                .mapToInt(optionalShortestDistances -> optionalShortestDistances.getAsInt())
                .min();
        visited.remove(startStation);
        return shortestDist;
    }

    public int getTripCountBetweenStationsWithMaxDistance(
            String startStationName,
            String endStationName,
            int maxDistance)
    {
        Station startStation = network.getStationByName(startStationName)
                .orElseThrow(() -> new IllegalArgumentException("invalid station name"));
        Station endStation = network.getStationByName(endStationName)
                .orElseThrow(() -> new IllegalArgumentException("invalid station name"));
        return getTripCountBetweenStationsWithMaxDistance(startStation, endStation, maxDistance, 0);
    }

    private int getTripCountBetweenStationsWithMaxDistance(
            Station startStation,
            Station endStation,
            int distanceRemaining,
            int distanceCoveredSoFar)
    {
        if (distanceRemaining <= 0) return 0;
        int tripEndingHere = (startStation == endStation && distanceCoveredSoFar > 0) ? 1 : 0;
        return tripEndingHere + startStation.getAllDestinations().stream()
                .mapToInt(nextStation -> getTripCountBetweenStationsWithMaxDistance(
                        nextStation,
                        endStation,
                        distanceRemaining - (startStation.getShortestDistanceTo(nextStation).getAsInt()),
                        distanceCoveredSoFar + (startStation.getShortestDistanceTo(nextStation).getAsInt())))
                .sum();
    }
}
