//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;
//
//public class VehicleSelectionPanel extends JPanel {
//    private JFrame frame;
//    private String selectedVehicle;
//    private Image car1Image, car2Image, car3Image;
//
//    public VehicleSelectionPanel(JFrame frame) {
//        this.frame = frame;
//        this.selectedVehicle = "Car1"; // Default selected car
//
//        // Load images for the vehicles (three cars)
//        try {
//            car1Image = ImageIO.read(new File("/Users/bogdyhh/Desktop/MovieTriviaGame/vehicles/car1.png"));
//            car2Image = ImageIO.read(new File("/Users/bogdyhh/Desktop/MovieTriviaGame/vehicles/car2.png"));
//            car3Image = ImageIO.read(new File("/Users/bogdyhh/Desktop/MovieTriviaGame/vehicles/car3.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        setBackground(Color.WHITE);
//        setLayout(new BorderLayout());
//
//        JLabel headerLabel = new JLabel("Select your vehicle:");
//        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        add(headerLabel, BorderLayout.NORTH);
//
//        JPanel vehicleSelectionPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 row, 3 columns
//        vehicleSelectionPanel.setBackground(Color.LIGHT_GRAY);
//
//        String[] vehicleTypes = {"Car1", "Car2", "Car3"};
//        for (String vehicleType : vehicleTypes) {
//            final String selectedType = vehicleType;
//
//            // Create button with custom painting
//            JButton button = new JButton() {
//                @Override
//                protected void paintComponent(Graphics g) {
//                    super.paintComponent(g);
//                    if (selectedType.equals("Car1")) {
//                        g.drawImage(car1Image, 10, 10, 80, 60, null);
//                    } else if (selectedType.equals("Car2")) {
//                        g.drawImage(car2Image, 10, 10, 80, 60, null);
//                    } else if (selectedType.equals("Car3")) {
//                        g.drawImage(car3Image, 10, 10, 80, 60, null);
//                    }
//                }
//            };
//
//            button.setPreferredSize(new Dimension(100, 100)); // Set size for the button
//            button.setContentAreaFilled(false); // Make button background transparent
//            button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Border around button
//            button.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    selectedVehicle = selectedType;
//                    startGame();
//                }
//            });
//
//            vehicleSelectionPanel.add(button);
//        }
//        add(vehicleSelectionPanel, BorderLayout.CENTER);
//    }
//
//    private void startGame() {
//        GamePanel gamePanel = new GamePanel(frame, selectedVehicle);
//        frame.setContentPane(gamePanel);
//        frame.revalidate();
//    }
//
//}
