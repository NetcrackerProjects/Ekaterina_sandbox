package com.gigssandbox;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GigCredentialsConverterTest {
    private GigCredentialsConverter converter;

    @Before
    public void setUp() {
        this.converter = new GigCredentialsConverter();
    }

    @Test
    public void shouldCreateStringWithHeadlinerAndGigDateWhenHeadlinerAndDateArePassed() {
        String headliner = "annisokay";
        String gigDate = "2021-01-02";
        String expectedResult = "annisokay:2021-01-02";

        String actualResult = converter.create(headliner, gigDate);

        assertEquals(expectedResult, actualResult);
    }
}
