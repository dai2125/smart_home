package com.home.coupling.fourthAnalysis.goodExample;

public class Box implements CalculateVolume {

    @Override
    public int volumeResult(int length, int width, int height) {
        return length * width * height;
    }
}
