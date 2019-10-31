package com.objectway.stage.examples.weatherservice.service.random;

import java.util.Iterator;
import java.util.Random;

/**
 * Bounded random-walk time series generator.
 */
public class TimeSeriesGenerator implements Iterator<Integer> {
    private Random random = new Random();
    private final int size;
    private final int min;
    private final int max;
    private int currentIndex = 0;
    private int lastValue = 0;

    public TimeSeriesGenerator(final int size, final int min, final int max) {
        this.size = size;
        this.min = min;
        this.max = max;
        currentIndex = 0;
        lastValue = min + random.nextInt(max - min + 1);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < size;
    }

    @Override
    public Integer next() {
        // Move
        lastValue += random.nextInt(7) - 3;

        // Clamp
        if (lastValue < min) {
            lastValue = min;
        }

        if (lastValue > max) {
            lastValue = max;
        }

        // Update index
        currentIndex++;
        return lastValue;
    }
}
