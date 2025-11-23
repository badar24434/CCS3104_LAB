package com.csc3402.lab.ccs3104_lab.LAB3;//Q1_StringManipulation
import java.util.Scanner;
public class Q1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Accept first string
        System.out.print("Enter the first string: ");
        String firstString  = input.nextLine();

        // Check length and display message if the string exceeds 10
        if (firstString.length() > 10) {
            String substring = firstString.substring(4, 10);
            System.out.println("Substring from 5 to 10: " + substring);
        } else {
            System.out.println("The first string is less than 10.");
        }

        System.out.println();

        //Accept second string
        System.out.print("Enter the second string: ");
        String secondString = input.nextLine();

        // Check if the second string exists in the first string or not
        if (firstString.indexOf(secondString) != -1) { // != -1 means it is not found
            System.out.println("Second string exists in the first string.");
            System.out.println();

            // Replace second string with "-replaced-"

            //we want to replace what is available in secondstring with -replaced-
            String newFristString = firstString.replace(secondString, "-replaced-");
            System.out.println("New first string: " + newFristString);
        }
        else {
            System.out.println("Second string does not exist in the first string.");
        }

        input.close();
    }

}
