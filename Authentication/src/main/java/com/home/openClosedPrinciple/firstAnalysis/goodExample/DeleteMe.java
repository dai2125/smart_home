package com.home.openClosedPrinciple.firstAnalysis.goodExample;

public class DeleteMe {

    public static void main(String[] args) {
        int minpos = 0;
        int temp = 0;
        int n = 6;
        int[] a = {1,3,2,4,5};

        for(int i = 0; i < n - 1; i++) {
            minpos = i;
            for(int j = i + 1; j < n; j++) {
                if(a[i] < a[minpos]) {
                    minpos = i;
                }
            }

            if(minpos > i) {
                temp = a[minpos];
                a[minpos] = a[i];
                a[i] = temp;
            }
        }

        for(int i = 0; i < n - 1; i++) {
            System.out.println(a[i]);
        }
    }


}
