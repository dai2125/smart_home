package com.home.asm;

import java.util.ArrayList;
import java.util.List;

public class Indirection {

    String from;
    List<String> to;

    public Indirection(String from, String ... to) {
        this.from = from;
        this.to = new ArrayList<>();
        for(String value : to) {
            this.to.add(value);
        }
    }

    public String getFrom() {
        return from;
    }

    public List<String> getTo() {
        return to;
    }

    public boolean containsComponent(String className) {
        for(int i = 0; i < to.size(); i++) {
            if(to.get(i).equals(className)) {
//                System.out.println("contains: " + to.get(i) + ", className: "  + className);
                return true;
            }
        }
        for(int i = 0; i < to.size(); i++) {
            if(to.get(i).equals(className.replaceFirst(".*/", ""))) {
//                System.out.println("contains: " + to.get(i) + ", className: " + className.replaceFirst(".*/", ""));

                return true;
            }
        }
        return false;
    }
}
