package com.challenge.tw.trains.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RouteTest {

    @Test
    public void createValidRoute() {
        assertThat(Route.between("A", "B", 10)).isNotNull();
    }

    @Test
    public void NegativeDistanceThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Route.between("A", "B", -9))
                .withMessage("distance cannot be negative");
    }

    @Test
    public void nullStationShouldThrowException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Route.between(null, "B", 10));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Route.between("A", null, 10));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Route.between(null, null, 10));
    }
}