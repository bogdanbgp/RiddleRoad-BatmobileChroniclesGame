package main.java.com.riddleroad.batmobilechronicles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class BatmobileOverviewPanel extends JPanel {
    private JFrame frame;
    private String selectedVehicle;
    private ImageIcon batmobileImageIcon; // Batmobile ImageIcon

    public BatmobileOverviewPanel(JFrame frame) {
        this.frame = frame;
        this.selectedVehicle = "Batmobile"; // Default selected vehicle

        // Load the Batmobile image from the resources folder
        try {
            // Use getClass().getResource to load the image from the resources folder
            URL batmobileImageUrl = getClass().getResource("/images/batman.png");
            if (batmobileImageUrl != null) {
                batmobileImageIcon = new ImageIcon(batmobileImageUrl);
            } else {
                System.out.println("Image not found: /images/batman.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBackground(new Color(0, 0, 0)); // Set the background color to black for Gotham mood
        setLayout(new BorderLayout());

        // Create a center panel to align everything in the center
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // Transparent background for center panel

        // Create vertical struts to add space
        centerPanel.add(Box.createVerticalGlue());

        // New header message for the Batmobile
        JLabel headerLabel = new JLabel("<html><div style='text-align: center; color: white; font-size: 24px; font-family: Arial;'>"
                + "This is your vehicle: The Batmobile<br/>"
                + "Assigned to you as Gotham's ultimate vehicle, engineered for speed, stealth, and firepower."
                + "</div></html>");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Adjusted font size
        headerLabel.setForeground(Color.WHITE); // White font color for visibility
        headerLabel.setAlignmentX(CENTER_ALIGNMENT); // Center the header
        centerPanel.add(headerLabel);

        centerPanel.add(Box.createVerticalStrut(20)); // Add some vertical space

        // Button for the Batmobile image using ImageIcon
        JButton button = new JButton(batmobileImageIcon);
        button.setPreferredSize(new Dimension(batmobileImageIcon.getIconWidth() + 20, batmobileImageIcon.getIconHeight() + 20)); // Add padding
        button.setContentAreaFilled(false); // Transparent button background
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Border around button

        // Action to start the game, with the Batmobile
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // Start the game with the Batmobile
            }
        });

        // Add the Batmobile button to the center panel
        button.setAlignmentX(CENTER_ALIGNMENT); // Center the button
        centerPanel.add(button);

        // Add a label below the Batmobile image, instructing to click to proceed
        JLabel clickToProceedLabel = new JLabel("Click on the Batmobile to proceed");
        clickToProceedLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Adjusted font size
        clickToProceedLabel.setForeground(Color.WHITE); // White font for visibility
        clickToProceedLabel.setAlignmentX(CENTER_ALIGNMENT); // Center align the label
        centerPanel.add(clickToProceedLabel);

        centerPanel.add(Box.createVerticalStrut(30)); // Add some space below the image

        // Vehicle Info Panel
        JPanel vehicleInfoPanel = new JPanel();
        vehicleInfoPanel.setOpaque(false); // Transparent panel background
        vehicleInfoPanel.setLayout(new BoxLayout(vehicleInfoPanel, BoxLayout.Y_AXIS));

        // Title for the information
        JLabel vehicleInfoTitle = new JLabel("Batmobile Specifications:");
        vehicleInfoTitle.setFont(new Font("Arial", Font.BOLD, 18));
        vehicleInfoTitle.setForeground(Color.WHITE); // White font for readability
        vehicleInfoTitle.setAlignmentX(CENTER_ALIGNMENT);
        vehicleInfoPanel.add(vehicleInfoTitle);

        // Updated Batmobile information with better styling
        JLabel modelLabel = new JLabel("Model: The Bat (from The Dark Knight Trilogy)");
        modelLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Adjusted font size
        modelLabel.setForeground(Color.LIGHT_GRAY); // Light gray font color
        modelLabel.setAlignmentX(CENTER_ALIGNMENT);
        vehicleInfoPanel.add(modelLabel);

        JLabel speedLabel = new JLabel("Max Speed: 205 mph (330 km/h)");
        speedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        speedLabel.setForeground(Color.LIGHT_GRAY);
        speedLabel.setAlignmentX(CENTER_ALIGNMENT);
        vehicleInfoPanel.add(speedLabel);

        JLabel weaponryLabel = new JLabel("Armament: Machine guns, grenade launchers, oil slicks, smoke screen");
        weaponryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        weaponryLabel.setForeground(Color.LIGHT_GRAY);
        weaponryLabel.setAlignmentX(CENTER_ALIGNMENT);
        vehicleInfoPanel.add(weaponryLabel);

        JLabel armorLabel = new JLabel("Armor: Bulletproof, bomb-resistant, fireproof");
        armorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        armorLabel.setForeground(Color.LIGHT_GRAY);
        armorLabel.setAlignmentX(CENTER_ALIGNMENT);
        vehicleInfoPanel.add(armorLabel);

        JLabel fuelLabel = new JLabel("Fuel: Jet turbine-powered engine");
        fuelLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        fuelLabel.setForeground(Color.LIGHT_GRAY);
        fuelLabel.setAlignmentX(CENTER_ALIGNMENT);
        vehicleInfoPanel.add(fuelLabel);

        JLabel additionalLabel = new JLabel("Additional Features: Advanced navigation, deployable wings, stealth mode");
        additionalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        additionalLabel.setForeground(Color.LIGHT_GRAY);
        additionalLabel.setAlignmentX(CENTER_ALIGNMENT);
        vehicleInfoPanel.add(additionalLabel);

        // Add vehicle info panel to the center panel
        vehicleInfoPanel.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(vehicleInfoPanel);

        // Add the center panel to the main panel
        add(centerPanel, BorderLayout.CENTER);

        centerPanel.add(Box.createVerticalGlue()); // Add vertical glue to ensure it stays centered
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Create a dark gradient background
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 0), 0, getHeight(), new Color(30, 30, 30));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void startGame() {
        // Start the game, passing the selected vehicle (Batmobile)
        GamePanel gamePanel = new GamePanel(frame, selectedVehicle);
        frame.setContentPane(gamePanel);
        frame.revalidate();
    }
}
