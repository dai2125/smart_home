package com.home.coupling.fourthAnalysis.badExample;

public class Volume {

    public static void main(String[] args) {
        Box box = new Box(5,5,5);
        System.out.println(box.volume);
    }
}
