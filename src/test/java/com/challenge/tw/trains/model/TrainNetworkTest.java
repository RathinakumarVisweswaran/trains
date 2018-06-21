package com.challenge.tw.trains.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrainNetworkTest {

    TrainNetwork network;
    Route routeAtoB;
    Route routeAtoC;
    Route routeBtoC;
    Route routeBtoA;
    Route routeAtoA;

    @BeforeEach
    void setUp() {
        network = new TrainNetwork();
        routeAtoB = Route.between("A", "B", 10);
        routeAtoC = Route.between("A", "C", 10);
        routeBtoC = Route.between("B", "C", 10);
        routeBtoA = Route.between("B", "A", 10);
        routeAtoA = Route.between("A", "A", 10);
    }

    @Test
    public void initiallyStationsListShouldBeEmpty() {
        assertThat(network.getStations()).isEmpty();
    }

    @Test
    public void shouldBeAbleToAddNewRoutes() {
        assertThat(network.addRoute(routeAtoB)).isTrue();
    }

    @Test
    public void addingRouteShouldAddBothStationsInTheStationList() {
        Route newRouteBetweenTwoNewStations = routeAtoB;
        network.addRoute(newRouteBetweenTwoNewStations);
        assertThat(network.getStations()).hasSize(2);
    }

    @Test
    public void routeFromAndToTheSameStationAddsOnlyOneStationToNetWork() {
        Route routeBetweenTheSameStation = routeAtoA;
        network.addRoute(routeBetweenTheSameStation);
        assertThat(network.getStations()).hasSize(1);
    }

    @Test
    public void duplicateRouteShouldNotBeAccepted() {
        Route newRoute = routeAtoA;
        network.addRoute(newRoute);
        Route duplicateRoute = routeAtoA;
        assertThat(network.addRoute(duplicateRoute)).isFalse();
    }

    @Test
    public void name() {

    }
}