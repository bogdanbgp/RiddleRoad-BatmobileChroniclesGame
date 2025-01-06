//package main.java.com.riddleroad.batmobilechronicles;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class MoviePoster {
//    private String imagePath;
//    private String movieName;
//    private int yPosition;
//    private String triviaQuestion;
//    private String[] answerOptions;
//    private Rectangle bounds;
//
//    public MoviePoster(String imagePath, String movieName, int yPosition) {
//        this.imagePath = imagePath;
//        this.movieName = movieName;
//        this.yPosition = yPosition;
//        this.triviaQuestion = "Who is the main actor in " + movieName + "?";
//        this.answerOptions = new String[] { "Option 1", "Option 2", "Option 3", "Option 4" };
//        this.bounds = new Rectangle(50, yPosition, 100, 150);  // Set the bounds for the poster
//    }
//
//    public String getMovieName() {
//        return movieName;
//    }
//
//    public int getYPosition() {
//        return yPosition;
//    }
//
//    public Rectangle getBounds() {
//        return bounds;
//    }
//
//    public void draw(Graphics g) {
//        try {
//            ImageIcon posterImage = new ImageIcon(imagePath);
//            g.drawImage(posterImage.getImage(), bounds.x, bounds.y, bounds.width, bounds.height, null);
//            g.setColor(Color.BLACK);
//            g.drawString(movieName, bounds.x, bounds.y + bounds.height + 15);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void showTrivia(JFrame frame) {
//        String message = triviaQuestion + "\n1. " + answerOptions[0] + "\n2. " + answerOptions[1] +
//                "\n3. " + answerOptions[2] + "\n4. " + answerOptions[3];
//        JOptionPane.showMessageDialog(frame, message, "Trivia", JOptionPane.INFORMATION_MESSAGE);
//    }
//}
