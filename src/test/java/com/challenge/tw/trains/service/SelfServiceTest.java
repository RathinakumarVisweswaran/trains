package com.challenge.tw.trains.service;

import com.challenge.tw.trains.model.Route;
import com.challenge.tw.trains.model.TrainNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SelfServiceTest {

    SelfService service;
    TrainNetwork network;

    @BeforeEach
    void setUp() {
        network = new TrainNetwork();
        service = new SelfService(network);
    }

    @Test
    public void addNewRoute() {
        Route newRoute = routeFor("AB5");
        assertThat(service.addRoute(newRoute)).isTrue();
    }

    private Route routeFor(String routeEncoding) {
        return Route.between(routeEncoding.substring(0, 1),
                routeEncoding.substring(1, 2),
                Integer.parseInt(routeEncoding.substring(2)));
    }

    @Test
    public void requestForDistancePathOfADirectRouteShouldReturnTheRouteDist() {
        Route routeFromAtoB = Route.between("A", "B", 10);
        service.addRoute(routeFromAtoB);
        assertThat(service.getShortestTripDistanceAlongPath("A-B")).isEqualTo(10);
    }

    @Test
    public void testDistForShortestTripAlongPath() {
        addAllRouteFromSampledTest("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertThat(service.getShortestTripDistanceAlongPath("A-B-C")).isEqualTo(9);
        assertThat(service.getShortestTripDistanceAlongPath("A-D")).isEqualTo(5);
        assertThat(service.getShortestTripDistanceAlongPath("A-D-C")).isEqualTo(13);
        assertThat(service.getShortestTripDistanceAlongPath("A-E-B-C-D")).isEqualTo(22);
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> service.getShortestTripDistanceAlongPath("A-E-D"))
                .withMessage("NO SUCH ROUTE");
    }

    private void addAllRouteFromSampledTest(String routeEncodingList) {
        for (String routeEncoding : routeEncodingList.split(", ")) {
            service.addRoute(routeFor(routeEncoding));
        }
    }

    @Test
    public void getTripCountBetweenStationsWithMaxHop() {
        addAllRouteFromSampledTest("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertThat(service.getTripCountBetweenStationsWithMaxHop("C", "C", 3))
                .isEqualTo(2);
    }

    @Test
    public void getTripCountBetweenStationsWithExactHop() {
        addAllRouteFromSampledTest("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertThat(service.getTripCountBetweenStationsWithExactHop("A", "C", 4))
                .isEqualTo(3);
    }

    @Test
    public void getShortestRouteDistanceBetweenStations() {
        addAllRouteFromSampledTest("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertThat(service.getShortestTripDistanceBetweenStations("A", "C")).isEqualTo(9);
    }

    @Test
    public void getShortestRouteDistanceBetweenStations2() {
        addAllRouteFromSampledTest("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertThat(service.getShortestTripDistanceBetweenStations("B", "B")).isEqualTo(9);
    }

    @Test
    public void getTripCountBetweenStationsWithMaxDistance() {
        addAllRouteFromSampledTest("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertThat(service.getTripCountBetweenStationsWithMaxDistance("C", "C", 30)).isEqualTo(7);
    }
}