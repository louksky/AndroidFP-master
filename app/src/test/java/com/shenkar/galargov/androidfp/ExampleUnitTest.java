package com.shenkar.galargov.androidfp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testCalculation() {
        long result = Calculations.calculateCarLocation(10000, 0, 5);

        long expected = 0 + 10000 * 5 / Constants.SPEED_FACTOR;

        assertEquals(result, expected);
    }

}