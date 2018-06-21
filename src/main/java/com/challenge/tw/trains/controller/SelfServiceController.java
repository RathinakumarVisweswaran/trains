package com.challenge.tw.trains.controller;

import com.challenge.tw.trains.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SelfServiceController {

    @Autowired
    SelfService service;

    @GetMapping("/query/shortest-distance-for/{path:[[A-E]-]*[A-E]}")
    public int getShortestTripDistanceAlongPath(@PathVariable String path) {
        return service.getShortestTripDistanceAlongPath(path);
    }

    @GetMapping("/query/count-trips-with-max-hop")
    public int getTripCountBetweenStationsWithMaxHop(
            @RequestParam(value = "from-station") String fromStationName,
            @RequestParam(value = "to-station") String toStationName,
            @RequestParam(value = "max-hop") int maxHop) {
        return service.getTripCountBetweenStationsWithMaxHop(fromStationName, toStationName, maxHop);
    }

    @GetMapping("/query/count-trips-with-exact-hop")
    public int getTripCountBetweenStationsWithExactHop(
            @RequestParam(value = "from-station") String fromStationName,
            @RequestParam(value = "to-station") String toStationName,
            @RequestParam(value = "exact-hop") int exactHop) {
        return service.getTripCountBetweenStationsWithExactHop(fromStationName, toStationName, exactHop);
    }

    @GetMapping("/query/shortest-route-distance-between")
    public int getShortestRouteDistanceBetweenStations(
            @RequestParam(value = "from-station") String fromStationName,
            @RequestParam(value = "to-station") String toStationName) {
        return service.getShortestTripDistanceBetweenStations(fromStationName, toStationName);
    }

    @GetMapping("/query/count-trips-with-max-distance")
    public int getTripCountBetweenStationsWithMaxDistance(
            @RequestParam(value = "from-station") String fromStationName,
            @RequestParam(value = "to-station") String toStationName,
            @RequestParam(value = "max-distance") int maxDistance) {
        return service.getTripCountBetweenStationsWithMaxDistance(fromStationName, toStationName, maxDistance);
    }

}
