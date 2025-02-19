package main.java.com.riddleroad.batmobilechronicles;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // create the game window
        JFrame frame = new JFrame("movie trivia game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // close when done
        frame.setSize(800, 600); // window size

        // set up start screen
        StartScreenPanel startScreen = new StartScreenPanel(frame);
        frame.setContentPane(startScreen);
        frame.setVisible(true); // display

        // center the window
        frame.setLocationRelativeTo(null);
    }
}
