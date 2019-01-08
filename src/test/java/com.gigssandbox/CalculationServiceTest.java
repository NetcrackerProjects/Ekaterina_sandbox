package com.gigssandbox;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class CalculationServiceTest {
    private CalculationService calculationService;

    @Before
    public void setUp() {
        this.calculationService = new CalculationService();
    }

    @Test
    public void shouldCalculateBandRatingWhenParametersSatisfyTheirRanges() {
        int expectedRating = 31;

        int actualRating = calculationService.calculateBandRating(10,5,150,500);

        assertEquals(expectedRating, actualRating);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalStateExceptionWhenParametersDoNotSatisfyTheirRanges() {
        int expectedRating = -8;

        int actualRating = calculationService.calculateBandRating(-1,-2,-100,-1);

        assertEquals(expectedRating, actualRating);
    }

    @Test
    public void shouldCalculateUserRatingWhenParametersSatisfyTheirRanges() {
        int expectedRating = 38;

        int actualRating = calculationService.calculateUserRating(21, 1, 2, 1);

        assertEquals(expectedRating, actualRating);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenParametersDoNotSatisfyTheirRanges() {
        int expectedRating = -148;

        int actualRating = calculationService.calculateUserRating(-10, -1, -1, -11);

        assertEquals(expectedRating, actualRating);
    }
}
