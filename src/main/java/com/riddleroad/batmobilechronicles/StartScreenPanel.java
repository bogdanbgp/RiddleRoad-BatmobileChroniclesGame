package main.java.com.riddleroad.batmobilechronicles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartScreenPanel extends JPanel {
    private JFrame frame;

    public StartScreenPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout()); // layout settings
        setBackground(new Color(0, 0, 0)); // black background, easy on the eyes

        // title label
        JLabel title = new JLabel("Riddle Road: The Batmobile Chronicles", SwingConstants.CENTER);
        title.setFont(new Font("Lobster", Font.BOLD, 30)); // cool font for title
        title.setForeground(new Color(255, 223, 186)); // light color text for visibility
        title.setOpaque(false); // no background color for title

        // title positioning
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(0, 0, 20, 0); // space below title
        add(title, gbcTitle);

        // panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); // nicely center the buttons
        buttonPanel.setOpaque(false); // no background for buttons

        // button constraints
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        gbcButton.insets = new Insets(20, 20, 20, 20); // space between buttons
        buttonPanel.add(createCustomButton("Start Game", new Color(0, 0, 0), new Color(255, 255, 255)), gbcButton); // black button

        gbcButton.gridy = 1;
        buttonPanel.add(createCustomButton("Exit", new Color(255, 82, 82), new Color(255, 255, 255)), gbcButton); // red button

        // add button panel to screen
        GridBagConstraints gbcButtonPanel = new GridBagConstraints();
        gbcButtonPanel.gridx = 0;
        gbcButtonPanel.gridy = 1;
        gbcButtonPanel.insets = new Insets(20, 0, 0, 0); // space above button panel
        add(buttonPanel, gbcButtonPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // important for proper rendering
        // gradient background effect
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 0, 0), 0, getHeight(), new Color(30, 30, 30));
        g2d.setPaint(gradient); // apply the gradient
        g2d.fillRect(0, 0, getWidth(), getHeight()); // fill with gradient
    }

    // custom button with rounded corners
    private JButton createCustomButton(String text, Color background, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // clean font
        button.setForeground(textColor);
        button.setBackground(background);
        button.setFocusPainted(false); // no focus painting
        button.setPreferredSize(new Dimension(250, 50)); // button size
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(background.darker(), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20))); // border and padding

        // make it look smooth
        button.setOpaque(true);
        button.setBorderPainted(false); // no border paint

        // button click actions
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Start Game")) {
                    showVehicleSelectionScreen(); // go to vehicle selection
                } else if (text.equals("Exit")) {
                    System.exit(0); // close the app
                }
            }
        });
            return button;
    }

    private void showVehicleSelectionScreen() {
        // method for vehicle selection screen
        BatmobileOverviewPanel vehicleSelectionPanel = new BatmobileOverviewPanel(frame);
        frame.setContentPane(vehicleSelectionPanel); // set the new screen
        frame.revalidate(); // revalidate the frame to update the view
    }
}
