package com.challenge.tw.trains.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "name")
public class Station {
    @Getter
    private final String name;
    private Map<Station, List<Integer>> destDistanceMap = new HashMap<>();

    public boolean addRouteTo(Station destStation, int distance) {
        destDistanceMap.putIfAbsent(destStation, new ArrayList());
        if (destDistanceMap.get(destStation).contains(distance)) return false;
        destDistanceMap.get(destStation).add(distance);
        return true;
    }

    public OptionalInt getShortestDistanceTo(Station destination) {
        return destDistanceMap.getOrDefault(destination, Collections.emptyList())
                .stream()
                .mapToInt(dist -> Integer.valueOf(dist))
                .min();
    }

    public Collection<Station> getAllDestinations() {
        return destDistanceMap.keySet();
    }
}
