package main.java.com.riddleroad.batmobilechronicles;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class GamePanel extends JPanel implements ActionListener {
    private JFrame frame;
    private Image batmobileImage, riddlerImage, environmentImage;

    private Timer timer;
    private boolean engineOn = false; // Whether the Batmobile's engine is on or not
    private boolean riddlerVisible = false; // Whether the Riddler's car is visible
    private boolean gameEnded = false; // Whether the game is over
    private boolean triviaActive = false; // Whether a trivia question is currently active
    private boolean triviaCooldown = false; // Whether there's a cooldown period after answering a trivia question

    private int batmanX = 400, batmanY = 325; // Position of the Batmobile on screen
    private int riddlerX = 900, riddlerY = 255; // Position of the Riddler's car
    private int distanceTraveled = 0; // Total miles traveled by Batmobile

    private int batmobileWidth = 140, batmobileHeight = 60; // Size of the Batmobile
    private int riddlerWidth = 140, riddlerHeight = 61; // Size of the Riddler's car

    private int roadOffsetX = 0; // Horizontal offset for the scrolling road background

    private int correctAnswers = 0; // Number of correct trivia answers
    private int incorrectAnswers = 0; // Number of incorrect trivia answers
    private int triviaIndex = 0; // Index for the trivia questions

    private static final int MILES_TO_NEXT_RIDDLER = 500; // Distance before the Riddler appears
    private static final int RIDDLER_SPEED = 5; // Speed at which the Riddler's car moves
    private static final int MAX_QUESTIONS = 100; // Maximum number of trivia questions

    private JButton startEngineButton, stopEngineButton; // Buttons for starting and stopping the Batmobile engine

    private int progressBarX = 230;
    private int progressBarY = 70;
    private int progressBarWidth = 270;
    private int progressBarHeight = 10;

    private boolean isDPressed = false; // Whether the 'D' key is being pressed
    private boolean isDPhysicallyPressed = false; // Whether the 'D' key was physically pressed

    public GamePanel(JFrame frame, String selectedVehicle) {
        this.frame = frame;

        // Load images for the Batmobile, Riddler, and the environment
        loadCarImages();
        loadBackgroundImage();

        // Set up the panel
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        requestFocusInWindow();

        // Initialize and style the Start button
        startEngineButton = new JButton("Start Engine") {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 30, 30);
                g.drawString("Miles Driven: " + distanceTraveled, 10, 60);
                super.paintComponent(g);
            }
        };

        // Set button properties and add it to the panel
        startEngineButton.setBounds(320, 250, 150, 50);
        startEngineButton.setFont(new Font("Arial", Font.BOLD, 18));
        startEngineButton.setForeground(Color.WHITE);
        startEngineButton.setBackground(Color.BLACK);
        startEngineButton.setOpaque(true);
        startEngineButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        startEngineButton.addActionListener(e -> startEngine());
        add(startEngineButton);

        // Initialize and style the Stop button (hidden by default)
        stopEngineButton = new JButton("Stop Engine");
        stopEngineButton.setBounds(320, 250, 150, 50);
        stopEngineButton.setFont(new Font("Arial", Font.BOLD, 18));
        stopEngineButton.setForeground(Color.WHITE);
        stopEngineButton.setBackground(Color.BLACK);
        stopEngineButton.setOpaque(true);
        stopEngineButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        stopEngineButton.addActionListener(e -> stopEngine());
        stopEngineButton.setVisible(false); // Hide the stop button initially
        add(stopEngineButton);

        // Handles key events for controlling the Batmobile
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    isDPhysicallyPressed = true;
                    if (engineOn && !gameEnded && !triviaActive) {
                        isDPressed = true;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    isDPhysicallyPressed = false;
                    isDPressed = false;
                }
            }
        });

        // Start the game loop with a timer (16ms interval)
        timer = new Timer(16, this);
        timer.start();
    }

    // Load the Batmobile and Riddler images
    private void loadCarImages() {
        try {
            batmobileImage = loadImageResource("/images/batman.png", batmobileWidth, batmobileHeight);
            riddlerImage = loadImageResource("/images/riddler.png", riddlerWidth, riddlerHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load the background image (the environment)
    private void loadBackgroundImage() {
        try {
            environmentImage = ImageIO.read(getClass().getResource("/images/environment.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Utility method to load an image from resources and scale it
    private Image loadImageResource(String resourcePath, int width, int height) {
        try {
            Image image = ImageIO.read(getClass().getResource(resourcePath));
            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Start the Batmobile engine
    private void startEngine() {
        engineOn = true;
        startEngineButton.setVisible(false); // Hide the start button
        stopEngineButton.setVisible(true); // Show the stop button
        repaint();
    }

    // Stop the Batmobile engine
    private void stopEngine() {
        engineOn = false;
        startEngineButton.setVisible(true); // Show the start button
        stopEngineButton.setVisible(false); // Hide the stop button
        repaint();
    }

    @Override
    // Override the paintComponent method to handle custom drawing
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);
        g.drawImage(batmobileImage, batmanX - batmobileWidth / 2, batmanY - batmobileHeight / 2, null);

        // If the Riddler is visible, draw his car
        if (riddlerVisible) {
            g.drawImage(riddlerImage, riddlerX - riddlerWidth / 2, riddlerY - riddlerHeight / 2, null);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Correct Answers: " + correctAnswers, 10, 20);
        g.drawString("Incorrect Answers: " + incorrectAnswers, 10, 40);

        drawMiniProgressBar(g);

        // If the game has ended, display the end message
        if (gameEnded) {
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Game Over! You got " + correctAnswers + " correct answers!", 250, 300);
        }
    }

    // Draw the background (scrolling road effect)
    private void drawBackground(Graphics g) {
        int backgroundWidth = 640;
        int backgroundHeight = 580;
        Image scaledBackground = environmentImage.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);

        // Draw the repeating background for a moving road effect
        for (int x = roadOffsetX % scaledBackground.getWidth(null); x < getWidth(); x += scaledBackground.getWidth(null)) {
            g.drawImage(scaledBackground, x, 0, null);
        }
    }

    // Draw the mini progress bar at the top
    private void drawMiniProgressBar(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(progressBarX, progressBarY, progressBarWidth, progressBarHeight);

        // Fill the progress bar based on correct and incorrect answers
        int progress = (correctAnswers + incorrectAnswers) * progressBarWidth / MAX_QUESTIONS;
        g.setColor(Color.GREEN);
        g.fillRect(progressBarX, progressBarY, progress, progressBarHeight);

        // Draw a tiny Batmobile to show progress
        int miniWidth = batmobileWidth / 4;
        int miniHeight = batmobileHeight / 4;
        int miniX = progressBarX + progress - miniWidth / 2;
        int miniY = progressBarY - miniHeight - 2;

        g.drawImage(
                batmobileImage.getScaledInstance(miniWidth, miniHeight, Image.SCALE_SMOOTH),
                miniX,
                miniY,
                null
        );

        drawCheckeredFlag(g, progressBarX + progressBarWidth + 10, progressBarY - 2);
    }

    // Draw a checkered flag at the end of the progress bar
    private void drawCheckeredFlag(Graphics g, int x, int y) {
        int flagWidth = 30;
        int flagHeight = 10;
        int squareSize = flagWidth / 5;

        // Checkerboard pattern
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                g.setColor((i + j) % 2 == 0 ? Color.BLACK : Color.WHITE);
                g.fillRect(x + i * squareSize, y + j * squareSize, squareSize, squareSize);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (engineOn && !gameEnded) {
            // Move the Batmobile and increase distance if 'D' is pressed
            if (isDPressed) {
                roadOffsetX -= 4;
                distanceTraveled += 2;

                // If the Batmobile has traveled enough distance, show the Riddler
                if (!riddlerVisible && distanceTraveled >= MILES_TO_NEXT_RIDDLER) {
                    riddlerVisible = true;
                    riddlerX = getWidth();
                }
            }

            // Move the Riddler's car
            if (riddlerVisible) {
                riddlerX -= RIDDLER_SPEED;

                // Check if the Batmobile collides with the Riddler's car
                if (!triviaActive && !triviaCooldown && isCollisionWithRiddler()) {
                    triviaActive = true;
                    startTrivia();
                }

                // Reset the Riddler's state if he goes off-screen
                if (riddlerX < -riddlerWidth) {
                    resetRiddlerState();
                }
            }

            repaint();
        }
    }

    // Check if the Batmobile collides with the Riddler's car
    private boolean isCollisionWithRiddler() {
        // Check if the right edge of the Batmobile overlaps with the left edge of the Riddler's car
        boolean rightEdgeCollision = batmanX + batmobileWidth / 2 >= riddlerX - riddlerWidth / 2;

        // Check if the left edge of the Batmobile overlaps with the right edge of the Riddler's car
        boolean leftEdgeCollision = batmanX - batmobileWidth / 2 <= riddlerX + riddlerWidth / 2;

        // Return true if both conditions are satisfied, indicating a collision
        return rightEdgeCollision && leftEdgeCollision;
    }

    // Reset the Riddler's state after he disappears off the screen
    private void resetRiddlerState() {
        riddlerVisible = false;
        riddlerX = -riddlerWidth;
        distanceTraveled = 0;
    }

    // Stop the Batmobile's movement temporarily after a collision
    private void stopMovement() {
        isDPressed = false;

        new Timer(1000, e -> isDPhysicallyPressed = false).start();
    }

    // Start the trivia question
    private void startTrivia() {
        if (triviaIndex >= MAX_QUESTIONS) {
            endGame(); // End the game if all trivia questions are answered
            return;
        }

        stopMovement();

        // Get the trivia question and options
        String[] trivia = MovieTrivia.triviaMap.get(triviaIndex + 1);
        if (trivia == null) return;

        String movieTitle = trivia[0];
        String question = trivia[1];
        String[] choices = Arrays.copyOfRange(trivia, 2, trivia.length);

        String[] options = new String[choices.length + 1];
        options[0] = "Select an answer";
        System.arraycopy(choices, 0, options, 1, choices.length);

        String fullQuestion = movieTitle + ": " + question;

        // Display the trivia question in a dialog
        String answer = (String) JOptionPane.showInputDialog(this, fullQuestion, "Riddle me this", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (answer != null && answer.equals(choices[0])) {
            correctAnswers++; // Correct answer
            JOptionPane.showMessageDialog(this, "Correct!", "Correct!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            incorrectAnswers++; // Incorrect answer
            JOptionPane.showMessageDialog(this, "Incorrect! The correct answer was: " + choices[0], "Incorrect!", JOptionPane.ERROR_MESSAGE);
        }

        triviaIndex++; // Move to the next trivia question
        triviaActive = false; // Reset trivia state

        startCooldown(); // Start the cooldown period before the next trivia question

        // Repaint the screen
        if (correctAnswers + incorrectAnswers <= MAX_QUESTIONS) {
            repaint();
        }

        // If the 'D' key is still pressed, continue the Batmobile movement
        if (isDPhysicallyPressed) {
            isDPressed = true;
        }
    }

    // Start the cooldown period after answering a trivia question
    private void startCooldown() {
        triviaCooldown = true;

        // Set a timer for the cooldown period
        Timer cooldownTimer = new Timer(3000, e -> triviaCooldown = false);
        cooldownTimer.setRepeats(false);
        cooldownTimer.start();
    }

    // End the game and display the result
    public void endGame() {
        gameEnded = true;
        repaint();
    }
}
