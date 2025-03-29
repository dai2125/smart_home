package com.home.cohesion;

import com.home.asm.LiskovSubstitutionPrincipleService;

public class Example12 {

    public void classEntry() {
        secondFunction();
        thirdFunction();

        LiskovSubstitutionPrincipleService.deleteMeAfterwards(fourthFunction());
    }

    private void secondFunction() {
    }

    private void thirdFunction() {
        String b = "new example";
    }

    private int fourthFunction() {
        return seventhFunction(fifthFunction(), sixthFunction());
    }

    private int fifthFunction() {
        return 17;
    }

    private String sixthFunction() {
        return "Winter is coming";
    }

    private int seventhFunction(int a, String b) {
        int ans = 0;
        for(int i = 0; i < b.length(); i++) {
            ans += b.charAt(i) + a;
        }
        System.out.println("ANS: " + ans);
        return ans;
    }
}
