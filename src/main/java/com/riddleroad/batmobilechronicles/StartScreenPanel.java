package main.java.com.riddleroad.batmobilechronicles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreenPanel extends JPanel {
    private JFrame frame;

    public StartScreenPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout()); // Use GridBagLayout for precise control over positioning
        setBackground(new Color(0, 0, 0)); // Default background is black

        // Title Label: Movie Trivia Game (Smaller size)
        JLabel title = new JLabel("Riddle Road: The Batmobile Chronicles", SwingConstants.CENTER);
        title.setFont(new Font("Lobster", Font.BOLD, 30)); // Beautiful font, Gotham-esque
        title.setForeground(new Color(255, 223, 186)); // Creamy light color for the title
        title.setOpaque(false);

        // Constraints for the title
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(0, 0, 20, 0); // Margin at the bottom
        add(title, gbcTitle);

        // Panel for buttons with padding
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        buttonPanel.setOpaque(false);

        // Constraints for centering the buttons
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        gbcButton.insets = new Insets(20, 20, 20, 20); // Add space around buttons
        buttonPanel.add(createCustomButton("Start Game", new Color(0, 0, 0), new Color(255, 255, 255)), gbcButton); // Dark button

        gbcButton.gridy = 1;
        buttonPanel.add(createCustomButton("Exit", new Color(255, 82, 82), new Color(255, 255, 255)), gbcButton); // Red Exit button

        // Add button panel to the main panel
        GridBagConstraints gbcButtonPanel = new GridBagConstraints();
        gbcButtonPanel.gridx = 0;
        gbcButtonPanel.gridy = 1;
        gbcButtonPanel.insets = new Insets(20, 0, 0, 0); // Move button panel down a bit
        add(buttonPanel, gbcButtonPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Always call super.paintComponent(g) to ensure proper rendering
        // Create a dark gradient background
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 0), 0, getHeight(), new Color(30, 30, 30));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    // Create a custom button with gradient and round corners
    private JButton createCustomButton(String text, Color background, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Modern rounded font
        button.setForeground(textColor);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 50));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(background.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20))); // Add padding inside the button

        // Adding smooth gradient background
        button.setOpaque(true);
        button.setBorderPainted(false);

        // Action listeners for buttons
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Start Game")) {
                    showVehicleSelectionScreen();
                } else if (text.equals("Exit")) {
                    System.exit(0);
                }
            }
        });

        return button;
    }

    private void showVehicleSelectionScreen() {
        // Transition to the next screen (vehicle selection)
        BatmobileOverviewPanel vehicleSelectionPanel = new BatmobileOverviewPanel(frame);
        frame.setContentPane(vehicleSelectionPanel);
        frame.revalidate();
    }
}
