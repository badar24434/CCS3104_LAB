package com.csc3402.lab.ccs3104_lab.LAB1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Solusi untuk Soalan 3: BorderPane dengan 5 butang.
 * Soalan ini menguji pengetahuan tentang tingkah laku lalai BorderPane.
 * Rantau "Center" secara automatik mengisi semua ruang yang tinggal,
 * jadi ia akan mengubah saiz apabila tetingkap diubah saiznya.
 * Rantau lain (Top, Bottom, Left, Right) hanya mengambil ruang
 * yang mereka perlukan dan kekal "tetap" di rantau mereka.
 *
 * Solution for Question 3: BorderPane with 5 buttons.
 * This question tests the knowledge of BorderPane's default behavior.
 * The "Center" region automatically fills all remaining space,
 * so it will resize when the window is resized.
 * The other regions (Top, Bottom, Left, Right) only take the space
 * they need and stay "fixed" in their regions.
 */
public class ResizableBorderPane extends Application {

    @Override
    public void start(Stage primaryStage) {

        // 1. Cipta bekas BorderPane (Create BorderPane container)
        BorderPane rootPane = new BorderPane();

        // 2. Cipta 5 butang (Create 5 buttons)
        Button btnTop = new Button("Top");
        Button btnLeft = new Button("Left");
        Button btnRight = new Button("Right");
        Button btnBottom = new Button("Bottom");
        Button btnCenter = new Button("Center");

        // 3. Tambah butang ke rantau masing-masing
        // (Add buttons to their respective regions)
        rootPane.setTop(btnTop);
        rootPane.setLeft(btnLeft);
        rootPane.setRight(btnRight);
        rootPane.setBottom(btnBottom);
        rootPane.setCenter(btnCenter);

        /*
         * NOTA PENTING (untuk pengajar):
         * Tiada kod tambahan diperlukan untuk membuat butang tengah "adjusts its size".
         * Ini adalah tingkah laku lalai bagi rantau TENGAH.
         *
         * Rantau Top/Bottom akan meregang secara MENEGAK (horizontal).
         * Rantau Left/Right akan meregang secara MENEGAK (vertical).
         * Rantau Center akan meregang kedua-dua arah.
         *
         * Keperluan "other buttons should stay fixed" dipenuhi kerana
         * saiz lalai mereka tidak berubah, walaupun rantau Top/Bottom
         * meregang lebar penuh. Butang itu sendiri tidak meregang.
         * Jika anda MAHU butang itu meregang penuh, anda perlu
         * tetapkan MaxWidth/MaxHeight kepada Double.MAX_VALUE.
         *
         * IMPORTANT NOTE (for instructor):
         * No extra code is needed to make the center button "adjust its size".
         * This is the default behavior of the CENTER region.
         *
         * The Top/Bottom regions will stretch HORIZONTALLY.
         * The Left/Right regions will stretch VERTICALLY.
         * The Center region will stretch BOTH ways.
         *
         * The requirement "other buttons should stay fixed" is met because
         * their default size doesn't change, even as the Top/Bottom
         * regions stretch to full width. The buttons themselves don't stretch.
         * If you WANTED the buttons to stretch, you would have to
         * set their MaxWidth/MaxHeight to Double.MAX_VALUE.
         */

        // 4. Sediakan Scene dan Stage
        Scene scene = new Scene(rootPane, 400, 300); // Saiz tetingkap awal
        primaryStage.setTitle("Resizable BorderPane");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}