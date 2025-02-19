package main.java.com.riddleroad.batmobilechronicles;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Movie Trivia Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set the size of the window

        // Start screen
        StartScreenPanel startScreen = new StartScreenPanel(frame);
        frame.setContentPane(startScreen);
        frame.setVisible(true);

        // Center the window on the screen
        frame.setLocationRelativeTo(null);  // This will center the window
    }
}
