package com.example.client.view;

import com.example.client.controller.ClientRentalController;
import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.example.client.model.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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

        JScrollPane listScrollPane = new JScrollPane(rentalList);
        listScrollPane.setPreferredSize(new Dimension(200, 150));

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        detailsScrollPane.setPreferredSize(new Dimension(300, 150));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);

        JButton deleteRental = new JButton("Delete Rental");
        deleteRental.setBackground(Color.RED);
        deleteRental.setForeground(Color.WHITE);
        deleteRental.setFont(new Font("Arial", Font.BOLD, 14));
        deleteRental.setFocusPainted(false);
        deleteRental.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRental();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(deleteRental);
        buttonPanel.add(Box.createVerticalGlue());

        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, detailsPanel);
        splitPane.setResizeWeight(0.3);
        splitPane.setDividerLocation(600);

        add(splitPane, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadData();
            }
        });
    }

    private void updateDetailsArea() {
        RentalModel selectedRental = rentalList.getSelectedValue();
        if (selectedRental != null) {
            CarModel selectedCar = selectedRental.getCar();
            LocalDate startDate = LocalDate.parse(selectedRental.getStartDate());
            LocalDate endDate = LocalDate.parse(selectedRental.getEndDate());
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
                if (rental.getUser().getEmail().equals(userSession.getEmail())) {
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

    private void deleteSelectedRental() {
        RentalModel selectedRental = rentalList.getSelectedValue();
        if (selectedRental != null) {
            int confirmation = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this rental?",
                    "Delete Rental",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirmation == JOptionPane.YES_OPTION) {
                // Call the controller to delete the rental
                //ClientRentalController.deleteRental(selectedRental);
                loadData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a rental to delete.", "No Rental Selected", JOptionPane.WARNING_MESSAGE);
        }
    }
}
