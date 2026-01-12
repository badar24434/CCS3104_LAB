package com.csc3402.lab.ccs3104_lab.LAB7;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Internationalization (i18n) Demo
 * 
 * This program demonstrates how to create applications that support multiple languages
 * and regional formats using Java's Locale and ResourceBundle classes.
 * 
 * Key Concepts:
 * - Locale: Represents a specific geographical, political, or cultural region
 * - ResourceBundle: Contains locale-specific objects (like strings)
 * - DateFormat: Formats dates according to locale conventions
 * - NumberFormat: Formats numbers and currencies according to locale conventions
 * 
 * @author CSC3104 Lab
 */
public class InternationalizationDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║         INTERNATIONALIZATION (i18n) DEMONSTRATION            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Display available locales
        System.out.println("Available Languages / Bahasa Yang Tersedia:");
        System.out.println("  1. English (United States)");
        System.out.println("  2. Bahasa Melayu (Malaysia)");
        System.out.println();
        
        System.out.print("Select language (1 or 2) / Pilih bahasa (1 atau 2): ");
        int choice = scanner.nextInt();
        System.out.println();
        
        // Create Locale based on user selection
        Locale selectedLocale;
        if (choice == 2) {
            // Malay (Malaysia)
            selectedLocale = new Locale("ms", "MY");
        } else {
            // Default to English (US)
            selectedLocale = new Locale("en", "US");
        }
        
        // Display messages using the selected locale
        displayLocalizedContent(selectedLocale);
        
        scanner.close();
    }
    
    /**
     * Displays localized content based on the selected Locale.
     * 
     * @param locale The Locale to use for localization
     */
    public static void displayLocalizedContent(Locale locale) {
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("Selected Locale: " + locale.getDisplayName());
        System.out.println("Language Code: " + locale.getLanguage());
        System.out.println("Country Code: " + locale.getCountry());
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println();
        
        // ═══════════════════════════════════════════════════════════════════
        // PART 1: Loading messages from ResourceBundle (Properties Files)
        // ═══════════════════════════════════════════════════════════════════
        
        // ResourceBundle automatically loads the correct properties file based on locale
        // For locale "en_US" it looks for: messages_en_US.properties
        // For locale "ms_MY" it looks for: messages_ms_MY.properties
        ResourceBundle messages = ResourceBundle.getBundle(
            "com.csc3402.lab.ccs3104_lab.LAB7.messages", 
            locale
        );
        
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│                    LOCALIZED MESSAGES                        │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        
        // Display greeting message
        String greeting = messages.getString("greeting");
        System.out.println("  " + greeting);
        
        // Display welcome message
        String welcome = messages.getString("welcome");
        System.out.println("  " + welcome);
        
        // Display farewell message
        String farewell = messages.getString("farewell");
        System.out.println("  " + farewell);
        
        // Display thank you message
        String thankYou = messages.getString("thankYou");
        System.out.println("  " + thankYou);
        
        System.out.println();
        
        // ═══════════════════════════════════════════════════════════════════
        // PART 2: Date Formatting based on Locale
        // ═══════════════════════════════════════════════════════════════════
        
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│                    DATE FORMATTING                           │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        
        Date currentDate = new Date();
        
        // Different date format styles
        DateFormat shortDate = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        DateFormat mediumDate = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        DateFormat longDate = DateFormat.getDateInstance(DateFormat.LONG, locale);
        DateFormat fullDate = DateFormat.getDateInstance(DateFormat.FULL, locale);
        
        // Date and Time combined
        DateFormat dateTime = DateFormat.getDateTimeInstance(
            DateFormat.LONG, DateFormat.MEDIUM, locale
        );
        
        String dateLabel = messages.getString("dateLabel");
        System.out.println("  " + dateLabel);
        System.out.println("  ─────────────────────────────────────────");
        System.out.println("  SHORT:  " + shortDate.format(currentDate));
        System.out.println("  MEDIUM: " + mediumDate.format(currentDate));
        System.out.println("  LONG:   " + longDate.format(currentDate));
        System.out.println("  FULL:   " + fullDate.format(currentDate));
        System.out.println("  DATE+TIME: " + dateTime.format(currentDate));
        
        System.out.println();
        
        // ═══════════════════════════════════════════════════════════════════
        // PART 3: Currency and Number Formatting based on Locale
        // ═══════════════════════════════════════════════════════════════════
        
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│                 CURRENCY & NUMBER FORMATTING                 │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        
        // Currency formatter
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        
        // Number formatter
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        
        // Percentage formatter
        NumberFormat percentFormat = NumberFormat.getPercentInstance(locale);
        
        double amount = 1234567.89;
        double percentage = 0.85;
        
        String currencyLabel = messages.getString("currencyLabel");
        System.out.println("  " + currencyLabel);
        System.out.println("  ─────────────────────────────────────────");
        System.out.println("  Raw Value:  " + amount);
        System.out.println("  Currency:   " + currencyFormat.format(amount));
        System.out.println("  Number:     " + numberFormat.format(amount));
        System.out.println("  Percentage: " + percentFormat.format(percentage));
        
        System.out.println();
        
        // ═══════════════════════════════════════════════════════════════════
        // PART 4: Example Application - Product Price Display
        // ═══════════════════════════════════════════════════════════════════
        
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.println("│                  SAMPLE APPLICATION OUTPUT                   │");
        System.out.println("└──────────────────────────────────────────────────────────────┘");
        
        String productLabel = messages.getString("productLabel");
        String priceLabel = messages.getString("priceLabel");
        String quantityLabel = messages.getString("quantityLabel");
        String totalLabel = messages.getString("totalLabel");
        
        double productPrice = 299.99;
        int quantity = 3;
        double total = productPrice * quantity;
        
        System.out.println("  " + productLabel + ": Laptop Computer");
        System.out.println("  " + priceLabel + ": " + currencyFormat.format(productPrice));
        System.out.println("  " + quantityLabel + ": " + numberFormat.format(quantity));
        System.out.println("  " + totalLabel + ": " + currencyFormat.format(total));
        
        System.out.println();
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("  " + farewell);
        System.out.println("════════════════════════════════════════════════════════════════");
    }
}
