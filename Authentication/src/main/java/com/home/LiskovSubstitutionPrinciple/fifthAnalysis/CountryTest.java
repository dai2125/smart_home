package com.home.LiskovSubstitutionPrinciple.fifthAnalysis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CountryTest {

    @Test
    public void testCountry() {

        Country country = new Country("Country", "Currency", 10000.0, 12, 12000);
        Country austria = new Austria("Austria", "Euro", 88871.0, 9159993, 512000000);
        Country vietnam = new Vietnam("Vietnam", "Dong", 331690.0, 98860000, 401000000);

        assertTrue(country instanceof Country);
        assertTrue(austria instanceof Country);
        assertTrue(vietnam instanceof Country);

        assertTrue(country.getName() instanceof String);
        assertTrue(austria.getName() instanceof String);
        assertTrue(vietnam.getName() instanceof String);

        assertTrue(country.getCurrency() instanceof String);
        assertTrue(austria.getCurrency() instanceof String);
        assertTrue(vietnam.getCurrency() instanceof String);

        assertFalse(country.getArea() < 0);
        assertFalse(austria.getArea() < 0);
        assertFalse(vietnam.getArea() < 0);

        assertTrue(country.getArea() > 0);
        assertTrue(austria.getArea() > 0);
        assertTrue(vietnam.getArea() > 0);

        assertFalse(country.getPopulation() < 0);
        assertFalse(austria.getPopulation() < 0);
        assertFalse(vietnam.getPopulation() < 0);

        assertTrue(country.getPopulation() > 0);
        assertTrue(austria.getPopulation() > 0);
        assertTrue(vietnam.getPopulation() > 0);

        assertFalse(country.getGdp() < 0);
        assertFalse(austria.getGdp() < 0);
        assertFalse(vietnam.getGdp() < 0);

        assertTrue(country.getGdp() > 0);
        assertTrue(austria.getGdp() > 0);
        assertTrue(vietnam.getGdp() > 0);
    }
}
