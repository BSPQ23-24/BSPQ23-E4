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
import java.util.Locale;
import java.util.ResourceBundle;

public class ManageRentedCarsView extends JPanel {
    private JList<RentalModel> rentalList;
    private DefaultListModel<RentalModel> rentalListModel;
    private JTextArea detailsArea;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private ResourceBundle messages;
    private Locale locale;

    public ManageRentedCarsView(Locale locale, ResourceBundle messages) {
        this.messages = messages;
        this.locale = locale;
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        JLabel headingLabel = new JLabel(messages.getString("label.manageRentedCars"));
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
            LocalDate startDate = LocalDate.parse(selectedRental.getStartDate());
            LocalDate endDate = LocalDate.parse(selectedRental.getEndDate());
            LocalDate creationDate = LocalDate.parse(selectedRental.getCreationDate(), formatter);
            String rentalStatus = determineRentalStatus(startDate, endDate);

            detailsArea.setText(
                    messages.getString("label.brand") + ": " + selectedCar.getBrand() +
                            "\n" + messages.getString("label.model") + ": " + selectedCar.getModel() +
                            "\n" + messages.getString("label.year") + ": " + selectedCar.getYear() +
                            "\n" + messages.getString("label.licensePlate") + ": " + selectedCar.getLicensePlate() +
                            "\n" + messages.getString("label.condition") + ": " + selectedCar.getCarCondition() +
                            "\n" + messages.getString("label.location") + ": " + selectedCar.getLocation() +
                            "\n" + messages.getString("label.user") + ": " + (selectedCar.getUser() != null ? selectedCar.getUser().getName() : "N/A") +
                            "\n" + messages.getString("label.description") + ": " + selectedCar.getDescription() +
                            "\n\n" + messages.getString("label.rentalDetails") +
                            "\n" + messages.getString("label.startDate") + ": " + startDate.format(formatter) +
                            "\n" + messages.getString("label.endDate") + ": " + endDate.format(formatter) +
                            "\n" + messages.getString("label.creationDate") + ": " + creationDate.format(formatter) +
                            "\n" + messages.getString("label.rentalStatus") + ": " + rentalStatus
            );
        } else {
            detailsArea.setText(messages.getString("message.selectCar"));
        }
    }

    private String determineRentalStatus(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(startDate)) {
            return messages.getString("label.notStarted");
        } else if (today.isAfter(endDate)) {
            return messages.getString("label.ended");
        } else {
            return messages.getString("label.active");
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
