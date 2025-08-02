package com.home.asm;

import java.util.Comparator;

public class BooleanComparator implements Comparator<InspectedClass> {

    @Override
    public int compare(InspectedClass o1, InspectedClass o2) {
        if(o1.isMainClass()) {
//            System.out.println("if: " + o1.getName() + " - " + o1.isMainClass() + " - " + o1.getIsInterface());
//            System.out.println("if: " + o2.getName() + " - " + o2.isMainClass() + " - " + o2.getIsInterface());
            return 0;
        } else if(o1.getIsInterface() && !o2.getIsInterface()) {
//            System.out.println("else if: " + o1.getName() + " - " + o1.isMainClass() + " - " + o1.getIsInterface());
//            System.out.println("else if: " + o2.getName() + " - " + o2.isMainClass() + " - " + o2.getIsInterface());

            return 1;
        } else if(o1.getIsInterface() && o1.isMainClass()) {
//            System.out.println("else if: " + o1.getName() + " - " + o1.isMainClass() + " - " + o1.getIsInterface());
//            System.out.println("else if: " + o2.getName() + " - " + o2.isMainClass() + " - " + o2.getIsInterface());

            return -1;
        } else {
//            System.out.println("else: " + o1.getName() + " - " + o1.isMainClass() + " - " + o1.getIsInterface());
//            System.out.println("else: " + o2.getName() + " - " + o2.isMainClass() + " - " + o2.getIsInterface());

            return -1;
        }
    }
}
