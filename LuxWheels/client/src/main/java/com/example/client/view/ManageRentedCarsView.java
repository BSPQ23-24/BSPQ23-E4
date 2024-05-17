package com.example.client.view;

import com.example.client.controller.ClientRentalController;
import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.example.client.model.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ManageRentedCarsView extends JPanel {
    private JList<RentalModel> rentalList;
    private DefaultListModel<RentalModel> rentalListModel;
    private JTextArea detailsArea;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ManageRentedCarsView() {
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        JLabel headingLabel = new JLabel("Manage Your Rented Cars");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        add(headingLabel, BorderLayout.NORTH);

        rentalListModel = new DefaultListModel<>();
        rentalList = new JList<>(rentalListModel);
        rentalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rentalList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof RentalModel) {
                    setText(((RentalModel) value).getCar().getSummary());
                }
                return this;
            }
        });
        rentalList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetailsArea();
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadData();
            }
        });

        JScrollPane listScrollPane = new JScrollPane(rentalList);
        listScrollPane.setPreferredSize(new Dimension(200, 150));
        add(listScrollPane, BorderLayout.WEST);

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        detailsScrollPane.setPreferredSize(new Dimension(300, 150));
        add(detailsScrollPane, BorderLayout.CENTER);
    }

    private void updateDetailsArea() {
        RentalModel selectedRental = rentalList.getSelectedValue();
        if (selectedRental != null) {
            CarModel selectedCar = selectedRental.getCar();
            LocalDate startDate = selectedRental.getStartDate();
            LocalDate endDate = selectedRental.getEndDate();
            LocalDate creationDate = LocalDate.parse(selectedRental.getCreationDate(), formatter);
            String rentalStatus = determineRentalStatus(startDate, endDate);

            detailsArea.setText(
                    "Brand: " + selectedCar.getBrand() +
                            "\nModel: " + selectedCar.getModel() +
                            "\nYear: " + selectedCar.getYear() +
                            "\nLicense Plate: " + selectedCar.getLicensePlate() +
                            "\nCondition: " + selectedCar.getCarCondition() +
                            "\nLocation: " + selectedCar.getLocation() +
                            "\nUser: " + (selectedCar.getUser() != null ? selectedCar.getUser().getName() : "N/A") +
                            "\nDescription: " + selectedCar.getDescription() +
                            "\n\nRental Details:" +
                            "\nStart Date: " + startDate.format(formatter) +
                            "\nEnd Date: " + endDate.format(formatter) +
                            "\nCreation Date: " + creationDate.format(formatter) +
                            "\nRental Status: " + rentalStatus
            );
        } else {
            detailsArea.setText("Select a car to view details.");
        }
    }

    private String determineRentalStatus(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(startDate)) {
            return "Not yet started";
        } else if (today.isAfter(endDate)) {
            return "Ended";
        } else {
            return "Currently active";
        }
    }

    public void loadData() {
        UserSession userSession = UserSession.getInstance();
        if (userSession.getEmail() != null) {
            List<RentalModel> rentals = ClientRentalController.getAllRentals();
            List<RentalModel> rentalsByUser = new ArrayList<>();
            for (RentalModel rental : rentals) {
                if(rental.getUser().getEmail().equals(userSession.getEmail())) {
                    rentalsByUser.add(rental);
                }
            }
            rentalListModel.clear();
            rentalsByUser.forEach(rentalListModel::addElement);
            if (!rentalListModel.isEmpty()) {
                rentalList.setSelectedIndex(0);
                updateDetailsArea();
            }
        }
    }
}
