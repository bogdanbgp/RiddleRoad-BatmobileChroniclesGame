package main.java.com.riddleroad.batmobilechronicles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class BatmobileOverviewPanel extends JPanel {
    private JFrame frame;
    private String selectedVehicle;
    private ImageIcon batmobileImageIcon; // batmobile image

    public BatmobileOverviewPanel(JFrame frame) {
        this.frame = frame;
        this.selectedVehicle = "Batmobile"; // default vehicle

        // try to load the batmobile image from resources
        try {
            // get image from the resources folder
            URL batmobileImageUrl = getClass().getResource("/images/batman.png");
            if (batmobileImageUrl != null) {
                batmobileImageIcon = new ImageIcon(batmobileImageUrl); // set the image if found
            } else {
                System.out.println("Image not found: /images/batman.png"); // print error if image not found
            }
        } catch (Exception e) {
            e.printStackTrace(); // print any exception errors
        }

        setBackground(new Color(0, 0, 0)); // black background for gotham vibes
        setLayout(new BorderLayout()); // set panel layout

        // create center panel to center everything
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // vertical box layout
        centerPanel.setOpaque(false); // no background for center panel

        centerPanel.add(Box.createVerticalGlue()); // space before header

        // header label for batmobile
        JLabel headerLabel = new JLabel("<html><div style='text-align: center; color: white; font-size: 24px; font-family: Arial;'>"
                + "This is your vehicle: The Batmobile<br/>"
                + "Assigned to you as Gotham's ultimate vehicle, engineered for speed, stealth, and firepower."
                + "</div></html>");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // big font for header
        headerLabel.setForeground(Color.WHITE); // white text
        headerLabel.setAlignmentX(CENTER_ALIGNMENT); // center align header
        centerPanel.add(headerLabel); // add header to panel

        centerPanel.add(Box.createVerticalStrut(20)); // space after header

        // batmobile image button
        JButton button = new JButton(batmobileImageIcon); // set image icon for button
        button.setPreferredSize(new Dimension(batmobileImageIcon.getIconWidth() + 20, batmobileImageIcon.getIconHeight() + 20)); // add padding
        button.setContentAreaFilled(false); // transparent background for button
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // black border for button

        // action when button is clicked
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // start the game when clicked
            }
        });

        // center the button
        button.setAlignmentX(CENTER_ALIGNMENT);
        centerPanel.add(button); // add button to panel

        // label to click on batmobile to proceed
        JLabel clickToProceedLabel = new JLabel("Click on the Batmobile to proceed");
        clickToProceedLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // smaller font
        clickToProceedLabel.setForeground(Color.WHITE); // white text
        clickToProceedLabel.setAlignmentX(CENTER_ALIGNMENT); // center align
        centerPanel.add(clickToProceedLabel); // add label to panel

        centerPanel.add(Box.createVerticalStrut(30)); // space after the label

        // vehicle info panel
        JPanel vehicleInfoPanel = new JPanel();
        vehicleInfoPanel.setOpaque(false); // no background for info panel
        vehicleInfoPanel.setLayout(new BoxLayout(vehicleInfoPanel, BoxLayout.Y_AXIS)); // vertical layout

        // title for vehicle info
        JLabel vehicleInfoTitle = new JLabel("Batmobile Specifications:");
        vehicleInfoTitle.setFont(new Font("Arial", Font.BOLD, 18)); // bold font for title
        vehicleInfoTitle.setForeground(Color.WHITE); // white text
        vehicleInfoTitle.setAlignmentX(CENTER_ALIGNMENT); // center align title
        vehicleInfoPanel.add(vehicleInfoTitle); // add title to panel

        // some info about the batmobile
        JLabel modelLabel = new JLabel("Model: The Bat (from The Dark Knight Trilogy)");
        modelLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // normal font
        modelLabel.setForeground(Color.LIGHT_GRAY); // light gray text
        modelLabel.setAlignmentX(CENTER_ALIGNMENT); // center align
        vehicleInfoPanel.add(modelLabel); // add model info to panel

        JLabel speedLabel = new JLabel("Max Speed: 205 mph (330 km/h)");
        speedLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // normal font
        speedLabel.setForeground(Color.LIGHT_GRAY); // light gray text
        speedLabel.setAlignmentX(CENTER_ALIGNMENT); // center align
        vehicleInfoPanel.add(speedLabel); // add speed info to panel

        JLabel weaponryLabel = new JLabel("Armament: Machine guns, grenade launchers, oil slicks, smoke screen");
        weaponryLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // normal font
        weaponryLabel.setForeground(Color.LIGHT_GRAY); // light gray text
        weaponryLabel.setAlignmentX(CENTER_ALIGNMENT); // center align
        vehicleInfoPanel.add(weaponryLabel); // add weaponry info to panel

        JLabel armorLabel = new JLabel("Armor: Bulletproof, bomb-resistant, fireproof");
        armorLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // normal font
        armorLabel.setForeground(Color.LIGHT_GRAY); // light gray text
        armorLabel.setAlignmentX(CENTER_ALIGNMENT); // center align
        vehicleInfoPanel.add(armorLabel); // add armor info to panel

        JLabel fuelLabel = new JLabel("Fuel: Jet turbine-powered engine");
        fuelLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // normal font
        fuelLabel.setForeground(Color.LIGHT_GRAY); // light gray text
        fuelLabel.setAlignmentX(CENTER_ALIGNMENT); // center align
        vehicleInfoPanel.add(fuelLabel); // add fuel info to panel

        JLabel additionalLabel = new JLabel("Additional Features: Advanced navigation, deployable wings, stealth mode");
        additionalLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // normal font
        additionalLabel.setForeground(Color.LIGHT_GRAY); // light gray text
        additionalLabel.setAlignmentX(CENTER_ALIGNMENT); // center align
        vehicleInfoPanel.add(additionalLabel); // add additional features info to panel

        // add vehicle info panel to the main panel
        vehicleInfoPanel.setAlignmentX(CENTER_ALIGNMENT); // center align
        centerPanel.add(vehicleInfoPanel); // add info panel to center panel

        // add the center panel to the main panel
        add(centerPanel, BorderLayout.CENTER);

        centerPanel.add(Box.createVerticalGlue()); // vertical space at the bottom to keep it centered
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // dark gradient background for a Gotham effect
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 0), 0, getHeight(), new Color(30, 30, 30));
        g2d.setPaint(gradient); // set gradient paint
        g2d.fillRect(0, 0, getWidth(), getHeight()); // fill the background with gradient
    }

    private void startGame() {
        // start the game with the selected vehicle (batmobile)
        GamePanel gamePanel = new GamePanel(frame, selectedVehicle);
        frame.setContentPane(gamePanel); // set the game panel
        frame.revalidate(); // revalidate the frame to update the view
    }
}
