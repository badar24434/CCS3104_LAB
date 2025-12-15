package com.csc3402.lab.ccs3104_lab.LAB4;

public class Testing {
    public static void main(String[] args) {

        System.out.println("=== FOR LOOP ===");
        // Repeat exactly
        for (int i = 0; i <= 5; i++) {
            System.out.println("For Loop - Iteration: " + i);
        }

        System.out.println("\n=== WHILE LOOP ===");
        // Loop while condition is true
        int count = 1;
        while (count <= 5) {
            System.out.println("While Loop - Count: " + count);
            count++;
        }

        System.out.println("\n=== DO-WHILE LOOP ===");
        // Always runs at least once
        int num = 1;
        do {
            System.out.println("Do-While Loop - Number: " + num);
            num++;
        } while (num <= 5);


    }
}
