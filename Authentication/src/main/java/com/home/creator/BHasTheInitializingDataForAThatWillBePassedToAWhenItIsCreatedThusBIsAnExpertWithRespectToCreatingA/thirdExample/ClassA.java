package com.home.creator.BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA.thirdExample;

public class ClassA {

    private String aString;
    private int aInt;

    public ClassA(String aString, int aInt) {
        this.aString = aString;
        this.aInt = aInt;
    }

    public String toString() {
        return  "\n" +
                aString + "\n" +
                aInt + "\n";
    }
}
