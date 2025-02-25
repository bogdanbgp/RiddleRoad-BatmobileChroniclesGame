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
    private boolean engineOn = false; // engine on / off
    private boolean riddlerVisible = false; // riddler's car visible yes/no
    private boolean gameEnded = false; // game over false by default
    private boolean triviaActive = false; // trivia question false by default
    private boolean triviaCooldown = false; // wait before next trivia

    private int batmanX = 400, batmanY = 325; // batmobile pos
    private int riddlerX = 900, riddlerY = 255; // riddler pos
    private int distanceTraveled = 0; // miles traveled by batmobile (get trivia after traveling X miles)

    private int batmobileWidth = 140, batmobileHeight = 60; // batmobile size
    private int riddlerWidth = 140, riddlerHeight = 61; // riddler size

    private int roadOffsetX = 0; // road scroll

    private int correctAnswers = 0; // correct answers counter
    private int incorrectAnswers = 0; // wrong answers counter
    private int triviaIndex = 0; // trivia question index

    private static final int MILES_TO_NEXT_RIDDLER = 500; // when riddler shows up (with trivia)
    private static final int RIDDLER_SPEED = 5; // riddler's speed
    private static final int MAX_QUESTIONS = 100; // max trivia questions (in MovieTrivia class)

    private JButton startEngineButton, stopEngineButton; // start/stop engine buttons

    private int progressBarX = 230;
    private int progressBarY = 70;
    private int progressBarWidth = 270;
    private int progressBarHeight = 10;

    private boolean isDPressed = false; // is d pressed?
    private boolean isDPhysicallyPressed = false; // was d pressed physically?

    public GamePanel(JFrame frame, String selectedVehicle) {
        this.frame = frame;

        // load batmobile and riddler images, background
        loadCarImages();
        loadBackgroundImage();

        // set up the panel
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        requestFocusInWindow();

        // style and action for start button
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

        // button settings
        startEngineButton.setBounds(320, 250, 150, 50);
        startEngineButton.setFont(new Font("Arial", Font.BOLD, 18));
        startEngineButton.setForeground(Color.WHITE);
        startEngineButton.setBackground(Color.BLACK);
        startEngineButton.setOpaque(true);
        startEngineButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        startEngineButton.addActionListener(e -> startEngine());
        add(startEngineButton);

        // stop engine button
        stopEngineButton = new JButton("Stop Engine");
        stopEngineButton.setBounds(320, 250, 150, 50);
        stopEngineButton.setFont(new Font("Arial", Font.BOLD, 18));
        stopEngineButton.setForeground(Color.WHITE);
        stopEngineButton.setBackground(Color.BLACK);
        stopEngineButton.setOpaque(true);
        stopEngineButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        stopEngineButton.addActionListener(e -> stopEngine());
        stopEngineButton.setVisible(false); // hide it initially
        add(stopEngineButton);

        // key listener for controlling batmobile
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

        // timer for game loop
        timer = new Timer(16, this);
        timer.start();
    }

    // load images for batmobile and riddler
    private void loadCarImages() {
        try {
            batmobileImage = loadImageResource("/images/batman.png", batmobileWidth, batmobileHeight);
            riddlerImage = loadImageResource("/images/riddler.png", riddlerWidth, riddlerHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // load background image
    private void loadBackgroundImage() {
        try {
            environmentImage = ImageIO.read(getClass().getResource("/images/environment.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // utility to load image and scale
    private Image loadImageResource(String resourcePath, int width, int height) {
        try {
            Image image = ImageIO.read(getClass().getResource(resourcePath));
            return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // start engine
    private void startEngine() {
        engineOn = true;
        startEngineButton.setVisible(false); // hide start button
        stopEngineButton.setVisible(true); // show stop button
        repaint();
    }

    // stop engine
    private void stopEngine() {
        engineOn = false;
        startEngineButton.setVisible(true); // show start button
        stopEngineButton.setVisible(false); // hide stop button
        repaint();
    }

    @Override
    // custom drawing for the game screen
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);
        g.drawImage(batmobileImage, batmanX - batmobileWidth / 2, batmanY - batmobileHeight / 2, null);

        // riddler car
        if (riddlerVisible) {
            g.drawImage(riddlerImage, riddlerX - riddlerWidth / 2, riddlerY - riddlerHeight / 2, null);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Correct Answers: " + correctAnswers, 10, 20);
        g.drawString("Incorrect Answers: " + incorrectAnswers, 10, 40);

        drawMiniProgressBar(g);

        // if game ended
        if (gameEnded) {
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Game Over! You got " + correctAnswers + " correct answers!", 250, 300);
        }
    }

    // draw background (moving road)
    private void drawBackground(Graphics g) {
        int backgroundWidth = 640;
        int backgroundHeight = 580;
        Image scaledBackground = environmentImage.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);

        // repeating background for moving effect
        for (int x = roadOffsetX % scaledBackground.getWidth(null); x < getWidth(); x += scaledBackground.getWidth(null)) {
            g.drawImage(scaledBackground, x, 0, null);
        }
    }

    // draw the mini progress bar at top
    private void drawMiniProgressBar(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(progressBarX, progressBarY, progressBarWidth, progressBarHeight);

        // fill progress bar based on answers
        int progress = (correctAnswers + incorrectAnswers) * progressBarWidth / MAX_QUESTIONS;
        g.setColor(Color.GREEN);
        g.fillRect(progressBarX, progressBarY, progress, progressBarHeight);

        // tiny batmobile to show progress
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

    // draw checkered flag at progress bar end
    private void drawCheckeredFlag(Graphics g, int x, int y) {
        int flagWidth = 30;
        int flagHeight = 10;
        int squareSize = flagWidth / 5;

        // checkerboard pattern
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
            // move batmobile, increase distance if 'D' pressed
            if (isDPressed) {
                roadOffsetX -= 4;
                distanceTraveled += 2;

                // riddler shows up after enough miles
                if (!riddlerVisible && distanceTraveled >= MILES_TO_NEXT_RIDDLER) {
                    riddlerVisible = true;
                    riddlerX = getWidth();
                }
            }

            // move riddler's car
            if (riddlerVisible) {
                riddlerX -= RIDDLER_SPEED;

                // check for collision with riddler
                if (!triviaActive && !triviaCooldown && isCollisionWithRiddler()) {
                    triviaActive = true;
                    startTrivia();
                }

                // reset riddler if offscreen
                if (riddlerX < -riddlerWidth) {
                    resetRiddlerState();
                }
            }

            repaint();
        }
    }

    // check if batmobile collides with riddler's car
    private boolean isCollisionWithRiddler() {
        boolean rightEdgeCollision = batmanX + batmobileWidth / 2 >= riddlerX - riddlerWidth / 2;
        boolean leftEdgeCollision = batmanX - batmobileWidth / 2 <= riddlerX + riddlerWidth / 2;
        return rightEdgeCollision && leftEdgeCollision;
    }

    // reset riddler after disappearing
    private void resetRiddlerState() {
        riddlerVisible = false;
        riddlerX = -riddlerWidth;
        distanceTraveled = 0;
    }

    // stop movement temporarily after collision
    private void stopMovement() {
        isDPressed = false;
        new Timer(1000, e -> isDPhysicallyPressed = false).start();
    }

    // start trivia question
    private void startTrivia() {
        if (triviaIndex >= MAX_QUESTIONS) {
            endGame(); // end if all questions are done
            return;
        }

        stopMovement();

        // get trivia question and options
        String[] trivia = MovieTrivia.triviaMap.get(triviaIndex + 1);
        if (trivia == null) return;

        String movieTitle = trivia[0];
        String question = trivia[1];
        String[] choices = Arrays.copyOfRange(trivia, 2, trivia.length);

        String[] options = new String[choices.length + 1];
        options[0] = "Select an answer";
        System.arraycopy(choices, 0, options, 1, choices.length);

        String fullQuestion = movieTitle + ": " + question;

        // show trivia question
        String answer = (String) JOptionPane.showInputDialog(this, fullQuestion, "Riddle me this", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (answer != null && answer.equals(choices[0])) {
            correctAnswers++; // correct answer
            JOptionPane.showMessageDialog(this, "Correct!", "Correct!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            incorrectAnswers++; // wrong answer
            JOptionPane.showMessageDialog(this, "Incorrect! The correct answer was: " + choices[0], "Incorrect!", JOptionPane.ERROR_MESSAGE);
        }

        triviaIndex++; // next question
        triviaActive = false; // reset trivia state

        startCooldown(); // cooldown before next trivia

        repaint();

        if (correctAnswers + incorrectAnswers <= MAX_QUESTIONS) {
            repaint();
        }

        if (isDPhysicallyPressed) {
            isDPressed = true;
        }
    }

    // cooldown after trivia
    private void startCooldown() {
        triviaCooldown = true;
        Timer cooldownTimer = new Timer(3000, e -> triviaCooldown = false);
        cooldownTimer.setRepeats(false);
        cooldownTimer.start();
    }

    // end game and show result
    public void endGame() {
        gameEnded = true;
        repaint();
    }
}
