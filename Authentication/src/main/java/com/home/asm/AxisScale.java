package com.home.asm;

public class AxisScale {
    public final double min;
    public final double max;

    public AxisScale(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double normalize(double value) {
        return (value - min) / (max - min);
    }

    public double denormalize(double value) {
        return min + value * (max - min);
    }
}
