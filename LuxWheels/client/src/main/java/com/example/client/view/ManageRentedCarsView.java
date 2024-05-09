package com.example.client.view;

import com.example.client.model.CarModel;

import javax.swing.*;
import java.awt.*;

public class ManageRentedCarsView extends JPanel {
    private JList<CarModel> carList;
    private DefaultListModel<CarModel> carListModel;
    private JTextArea detailsArea;

    public ManageRentedCarsView() {
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        // Heading
        JLabel headingLabel = new JLabel("Manage Your Rented Cars");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        add(headingLabel, BorderLayout.NORTH);

        carListModel = new DefaultListModel<>();
        carList = new JList<>(carListModel);
        carList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CarModel) {
                    setText(((CarModel) value).getSummary());
                }
                return this;
            }
        });
        carList.addListSelectionListener(e -> updateDetailsArea());

        JScrollPane listScrollPane = new JScrollPane(carList);
        listScrollPane.setPreferredSize(new Dimension(200, 150));
        add(listScrollPane, BorderLayout.WEST);

        // Details area setup
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        detailsScrollPane.setPreferredSize(new Dimension(300, 150));
        add(detailsScrollPane, BorderLayout.CENTER);
    }

    // Method to update the details area when a car is selected
    private void updateDetailsArea() {
        CarModel selectedCar = carList.getSelectedValue();
        if (selectedCar != null) {
            detailsArea.setText(
                    "Brand: " + selectedCar.getBrand() +
                            "\nModel: " + selectedCar.getModel() +
                            "\nYear: " + selectedCar.getYear() +
                            "\nLicense Plate: " + selectedCar.getLicensePlate() +
                            "\nCondition: " + selectedCar.getCarCondition() +
                            "\nLocation: " + selectedCar.getLocation() +
                            "\nStatus: " + selectedCar.getStatus() +
                            "\nUser: " + (selectedCar.getUser() != null ? selectedCar.getUser().getName() : "N/A") +
                            "\nDescription: " + selectedCar.getDescription()
            );
        } else {
            detailsArea.setText("Select a car to view details.");
        }
    }
}
