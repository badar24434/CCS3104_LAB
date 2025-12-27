package com.csc3402.lab.ccs3104_lab.LAB5;

public class Main {
    public static void main(String[] args) {

        sayHello();
        addnumbers(5, 10,15);
        printnumbers();
    }

    public static void sayHello() {
        System.out.println("Welcome!");
    }

    public static void addnumbers(int a, int b, int c) {
        int sum = a + b+ c;
        System.out.println("The sum is: " + sum);
    }

    public static void  printnumbers() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
        }
    }

}
