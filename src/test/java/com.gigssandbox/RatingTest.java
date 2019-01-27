package com.gigssandbox;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class RatingTest {
    private Rating rating;

    @Before
    public void setUp() {
        this.rating = new Rating();
    }

    @Test
    public void shouldCalculateBandRatingWhenParametersSatisfyTheirRanges() {
        int expectedRating = 31;

        int actualRating = rating.calculateBandRating(10,5,150,500);

        assertEquals(expectedRating, actualRating);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalStateExceptionWhenParametersDoNotSatisfyTheirRanges() {
        int expectedRating = -8;

        int actualRating = rating.calculateBandRating(-1,-2,-100,-1);

        assertEquals(expectedRating, actualRating);
    }

    @Test
    public void shouldCalculateUserRatingWhenParametersSatisfyTheirRanges() {
        int expectedRating = 38;

        int actualRating = rating.calculateUserRating(21, 1, 2, 1);

        assertEquals(expectedRating, actualRating);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenParametersDoNotSatisfyTheirRanges() {
        int expectedRating = -148;

        int actualRating = rating.calculateUserRating(-10, -1, -1, -11);

        assertEquals(expectedRating, actualRating);
    }
}
