//import main.java.com.riddleroad.batmobilechronicles.MovieTrivia;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.File;
//import javax.imageio.ImageIO;
//import javax.swing.Timer;
//import java.util.*;
//import java.util.List;
//
//public class GamePanel extends JPanel implements ActionListener, MouseListener {
//    private JFrame frame;
//    private Image carImage;
//    private Image environmentImage;
//    private Timer timer;
//    private boolean engineOn = false;
//    private int roadOffsetX = 0; // scroll the road horizontally
//    private int carX, carY;
//    private int score = 0;
//    private int misses = 0; // count the misses
//    private List<ImageIcon> posters = new ArrayList<>();
//    private List<Rectangle> posterBounds = new ArrayList<>();
//    private int nextPosterId = 1;
//    private boolean gameEnded = false; // flag to check if the game is over
//    private long lastPosterGenerationTime = 0; // track when to generate the next poster
//    private int distanceTraveled = 0; // how far the car has traveled
//
//    private static final int ROAD_HEIGHT = 100;
//    private static final int MAX_POSTERS = 100;
//    private static final int POSTER_DISTANCE_INTERVAL = 3000; // interval to generate posters
//
//    // button to start the engine
//    private JButton startEngineButton;
//    private boolean movingForward = false;  // track if the car is moving forward
//
//    public GamePanel(JFrame frame, String selectedVehicle) {
//        this.frame = frame;
//        this.carX = 400;
//        this.carY = 320;
//
//        loadCarImage(selectedVehicle);
//        loadBackgroundImage();
//        setBackground(Color.CYAN);
//        setPreferredSize(new Dimension(800, 600));
//
//        setFocusable(true);
//        requestFocusInWindow();
//
//        // button to start the engine
//        startEngineButton = new JButton("Start Engine");
//        startEngineButton.setBounds(350, 50, 150, 50);
//        startEngineButton.setFont(new Font("Arial", Font.BOLD, 16));
//        startEngineButton.addActionListener(e -> startEngine());
//        startEngineButton.setVisible(true);
//
//        add(startEngineButton);
//
//        // handle movement after engine starts
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (engineOn && !gameEnded) {
//                    if (e.getKeyCode() == KeyEvent.VK_D) {
//                        roadOffsetX -= 15; // move road to the left, car moves right
//                        movingForward = true;
//                    } else if (e.getKeyCode() == KeyEvent.VK_A) {
//                        roadOffsetX += 15; // move road to the right, car moves left
//                        movingForward = false;
//                    }
//                }
//            }
//        });
//
//        addMouseListener(this);
//
//        timer = new Timer(16, this); // around 60 FPS
//        timer.start();
//    }
//
//    private void loadCarImage(String selectedVehicle) {
//        try {
//            carImage = ImageIO.read(new File("/Users/bogdyhh/Desktop/MovieTriviaGame/vehicles/" + selectedVehicle + ".png"));
//
//            // set width and height for car image
//            int carWidth = 140;
//            int carHeight = 120;
//
//            // scale the car image to match the width and height
//            carImage = carImage.getScaledInstance(carWidth, carHeight, Image.SCALE_SMOOTH);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void loadBackgroundImage() {
//        try {
//            environmentImage = ImageIO.read(new File("/Users/bogdyhh/Desktop/MovieTriviaGame/background/environment.png"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void startEngine() {
//        engineOn = true;
//        startEngineButton.setVisible(false);  // hide start engine button
//        repaint();
//    }
//
//    private void addPoster(int xPosition) {
//        if (nextPosterId > MAX_POSTERS || gameEnded) return;
//
//        try {
//            File posterFile = new File("/Users/bogdyhh/Desktop/MovieTriviaGame/movieposters/" + nextPosterId + ".png");
//            if (!posterFile.exists()) return;
//
//            ImageIcon poster = new ImageIcon(posterFile.getAbsolutePath());
//
//            // add a new poster with random vertical offset
//            int posterY = carY - 100 + (int)(Math.random() * 200); // random position above or below road
//            posters.add(poster);
//            posterBounds.add(new Rectangle(xPosition, posterY, poster.getIconWidth(), poster.getIconHeight()));
//
//            nextPosterId++;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void updatePosters() {
//        // only generate new poster after certain distance
//        if (movingForward && distanceTraveled >= POSTER_DISTANCE_INTERVAL) {
//            addPoster(roadOffsetX + 1200); // add new poster after road
//            distanceTraveled = 0; // reset distance counter
//        }
//
//        // update position of posters and remove those that pass the car
//        for (int i = 0; i < posterBounds.size(); i++) {
//            Rectangle bounds = posterBounds.get(i);
//            bounds.x -= 10; // move posters left with road
//
//            // if the poster goes off-screen, remove it
//            if (bounds.x + bounds.width < 0) {
//                posters.remove(i);
//                posterBounds.remove(i);
//                i--; // adjust index after removal
//            }
//        }
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        // draw the background
//        drawBackground(g);
//
//        // draw the car
//        g.drawImage(carImage, carX - carImage.getWidth(null) / 2, carY - carImage.getHeight(null) / 2, null);
//
//        // draw posters
//        if (posterBounds.size() > 0) {
//            for (int i = 0; i < posterBounds.size(); i++) {
//                ImageIcon poster = posters.get(i);
//                Rectangle bounds = posterBounds.get(i);
//
//                // draw the poster above or below the road
//                g.drawImage(poster.getImage(), bounds.x, bounds.y, null);
//            }
//        }
//
//        // draw the score
//        g.setColor(Color.BLACK);
//        g.setFont(new Font("Arial", Font.BOLD, 16));
//        g.drawString("Score: " + score + "%", 10, 20);
//
//        // draw the number of misses
//        g.setColor(Color.RED);
//        g.drawString("Misses: " + misses, 10, 40);
//
//        // show the end-of-game message if the game has ended
//        if (gameEnded) {
//            g.setColor(Color.BLACK);
//            g.setFont(new Font("Arial", Font.BOLD, 24));
//            g.drawString("Game Over! You scored " + score + "%", 250, 300);
//        }
//    }
//
//    private void drawBackground(Graphics g) {
//        // draw repeating background (environment image)
//        int backgroundWidth = 640;  // background width
//        int backgroundHeight = 580; // background height
//
//        // stretch background to fit screen width
//        Image scaledBackground = environmentImage.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);
//
//        // draw background, repeat it across the screen
//        for (int x = roadOffsetX % scaledBackground.getWidth(null); x < getWidth(); x += scaledBackground.getWidth(null)) {
//            g.drawImage(scaledBackground, x, 0, null);
//        }
//
//        // for moving backward (A), also repeat on the left side
//        if (roadOffsetX > 0) {
//            for (int x = (roadOffsetX % scaledBackground.getWidth(null)) - scaledBackground.getWidth(null); x < 0; x += scaledBackground.getWidth(null)) {
//                g.drawImage(scaledBackground, x, 0, null);
//            }
//        }
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (engineOn && !gameEnded) {
//            updatePosters();
//            distanceTraveled += 15; // increase distance by car's speed
//            repaint();
//        }
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {
//        if (gameEnded) {
//            return;
//        }
//
//        // check if the player clicked on a poster
//        for (int i = 0; i < posterBounds.size(); i++) {
//            Rectangle bounds = posterBounds.get(i);
//            if (bounds.contains(e.getPoint())) {
//                startTrivia(i);
//                posters.remove(i);
//                posterBounds.remove(i);
//                return;
//            }
//        }
//
//        // if clicked outside a poster, count as a miss
//        misses++;
//    }
//
//    private void startTrivia(int posterIndex) {
//        int questionId = nextPosterId - posters.size() + posterIndex;
//
//        String[] trivia = MovieTrivia.triviaMap.get(questionId);
//        if (trivia == null) return;
//
//        String title = trivia[0];
//        String question = trivia[1];
//        String[] choices = Arrays.copyOfRange(trivia, 2, trivia.length);
//
//        String answer = (String) JOptionPane.showInputDialog(
//                this, question, title, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
//
//        if (answer != null && answer.equals(choices[0])) {
//            JOptionPane.showMessageDialog(this, "Correct!", "Trivia", JOptionPane.INFORMATION_MESSAGE);
//            score++;
//        } else {
//            JOptionPane.showMessageDialog(this, "Wrong! Correct answer: " + choices[0], "Trivia", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    public void endGame() {
//        gameEnded = true;
//        repaint();
//    }
//
//    @Override public void mousePressed(MouseEvent e) {}
//    @Override public void mouseReleased(MouseEvent e) {}
//    @Override public void mouseEntered(MouseEvent e) {}
//    @Override public void mouseExited(MouseEvent e) {}
//}
