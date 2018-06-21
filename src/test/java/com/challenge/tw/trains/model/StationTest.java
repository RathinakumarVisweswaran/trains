package com.challenge.tw.trains.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StationTest {

    Station stationA;
    Station stationB;
    Station stationC;

    @BeforeEach
    void setUp() {
        stationA = new Station("A");
        stationB = new Station("B");
        stationC = new Station("C");
    }

    @Test
    public void shouldBeAbleToAddRouteToNewStation() {
        Station newDestination = stationB;
        int newDistance = 10;
        assertThat(stationA.addRouteTo(newDestination, newDistance)).isTrue();
    }

    @Test
    public void duplicateRoutesShouldNotBeAccepted() {
        Station newDestination = stationB;
        int newDistance = 10;
        stationA.addRouteTo(newDestination, newDistance);
        Station duplicateDestination = stationB;
        int duplicateDistance = 10;
        assertThat(stationA.addRouteTo(duplicateDestination, duplicateDistance)).isFalse();
    }

    @Test
    public void differentRoutesToTheSameDestinationShouldBeAllowed() {
        Station newDestination = stationB;
        int newDistance = 10;
        stationA.addRouteTo(newDestination, newDistance);
        Station existingDestination = stationB;
        int differentDistance = 11;
        assertThat(stationA.addRouteTo(existingDestination, differentDistance)).isTrue();
    }

    @Test
    public void initiallyDestinationStationsShouldBeEmpty() {
        assertThat(stationA.getAllDestinations()).isEmpty();
    }

    @Test
    public void withOneRouteAddedDestinationStationsShouldHaveOne() {
        stationA.addRouteTo(stationB, 10);
        assertThat(stationA.getAllDestinations()).hasSize(1);
    }

    @Test
    public void withMultiRouteAddedDestinationStationsShouldHaveMultiCount() {
        stationA.addRouteTo(stationB, 10);
        stationA.addRouteTo(stationC, 20);
        assertThat(stationA.getAllDestinations()).hasSize(2);
    }

    @Test
    public void multiRouteToSameDestinationShouldNotCountInDestinationList() {
        Station newDestination = stationB;
        int newDistance = 10;
        stationA.addRouteTo(newDestination, newDistance);
        Station existingDestination = stationB;
        int differentDistance = 10;
        stationA.addRouteTo(existingDestination, differentDistance);
        assertThat(stationA.getAllDestinations()).hasSize(1);
    }
}