package com.home.coupling.fourthAnalysis.goodExample;

public class Main {

    public static void main(String[] args) {

        CalculateVolume calculateVolume = new Box();
        int volume = calculateVolume.volumeResult(5,5,5);
        System.out.println(volume);
    }
}
