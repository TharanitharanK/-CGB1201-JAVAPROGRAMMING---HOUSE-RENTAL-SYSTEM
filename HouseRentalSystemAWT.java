import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HouseRentalSystem extends JFrame {
    private ArrayList<House> houses;
    private DefaultListModel<String> houseListModel;

    public HouseRentalSystem() {
        houses = new ArrayList<>();
        houseListModel = new DefaultListModel<>();

        setTitle("House Rental System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3, 5, 5));
        JLabel titleLabel = new JLabel("House Rental System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel);

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel availableHousesLabel = new JLabel("Available Houses:", JLabel.LEFT);
        JList<String> houseList = new JList<>(houseListModel);
        houseList.setVisibleRowCount(10);
        JScrollPane scrollPane = new JScrollPane(houseList);

        centerPanel.add(availableHousesLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel (Buttons)
        JPanel bottomPanel = new JPanel();
        JButton addHouseButton = new JButton("Add House");
        JButton searchHouseButton = new JButton("Search House");
        JButton exitButton = new JButton("Exit");

        bottomPanel.add(addHouseButton);
        bottomPanel.add(searchHouseButton);
        bottomPanel.add(exitButton);

        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        addHouseButton.addActionListener(e -> addHouse());
        searchHouseButton.addActionListener(e -> searchHouse());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void addHouse() {
        JTextField locationField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] message = {
            "Location:", locationField,
            "Price:", priceField,
            "Description:", descriptionField,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add New House", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String location = locationField.getText();
            String price = priceField.getText();
            String description = descriptionField.getText();

            if (!location.isEmpty() && !price.isEmpty() && !description.isEmpty()) {
                House house = new House(location, price, description);
                houses.add(house);
                houseListModel.addElement(house.toString());
                JOptionPane.showMessageDialog(this, "House added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchHouse() {
        String location = JOptionPane.showInputDialog(this, "Enter location to search:");
        if (location != null && !location.isEmpty()) {
            StringBuilder result = new StringBuilder("Search Results:\n");
            boolean found = false;

            for (House house : houses) {
                if (house.getLocation().equalsIgnoreCase(location)) {
                    result.append(house).append("\n");
                    found = true;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(this, result.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No houses found in the given location.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Location cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HouseRentalSystem frame = new HouseRentalSystem();
            frame.setVisible(true);
        });
    }
}

class House {
    private String location;
    private String price;
    private String description;

    public House(String location, String price, String description) {
        this.location = location;
        this.price = price;
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return location + " - $" + price + " - " + description;
    }
}
