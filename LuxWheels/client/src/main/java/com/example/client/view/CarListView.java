package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.controller.ClientUserController;
import com.example.client.model.CarModel;
import com.example.client.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CarListView extends JPanel {
	private static final Logger logger = LogManager.getLogger(LoginView.class);
    private JList<CarModel> itemList;
    private DefaultListModel<CarModel> model;
    private JPanel infoPanel;
    private JTextArea detailsArea;
    private JTable infoTable;
    public CarListView() {
        logger.info("CarListView generated");
        setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel("Available cars for rent");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headingLabel, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        java.util.List<CarModel> cars = ClientCarController.getAllCars();
        for (CarModel car : cars) {
        	if (car.getUser().getId() != null && !car.getUser().getId().equals(ClientUserController.loggedUser.getId())) {
            //if (car.getStatus() == CarModel.Status.OPEN) {
                model.addElement(car);}
            //}
        }

        itemList = new JList<>(model);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CarModel) {
                    setText(((CarModel) value).getSummary());
                }
                return renderer;
            }
        });

        JScrollPane listScrollPane = new JScrollPane(itemList);
        listScrollPane.setPreferredSize(new Dimension(200, 150));

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane infoScrollPane = new JScrollPane(detailsArea);
        infoScrollPane.setPreferredSize(new Dimension(300, 150));

        infoPanel = new JPanel(new BorderLayout());
        infoScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(infoScrollPane, BorderLayout.CENTER);

        JButton rentButton = new JButton(" Rent Now! ");
        rentButton.setBackground(Color.GREEN);
        rentButton.addActionListener(this::launchRentingView);
        rentButton.setForeground(Color.WHITE);
        rentButton.setFont(new Font("Arial", Font.BOLD, 14));
        rentButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(rentButton);
        buttonPanel.add(Box.createVerticalGlue());

        infoPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, infoPanel);
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.3);

        add(splitPane, BorderLayout.CENTER);

        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                CarModel selectedCar = itemList.getSelectedValue();
                updateInfoPanel(selectedCar);

            }
        });

        if (!model.isEmpty()) {
            itemList.setSelectedIndex(0);
            updateInfoPanel(itemList.getSelectedValue());
        }
    }


    private void updateInfoPanel(CarModel car) {
        if (car == null) {
            System.out.println("No car selected or car data is null");
            detailsArea.setText("Select a car to view details.");
            return;
        }else{
            detailsArea.setText(
                    "Brand: " + car.getBrand() +
                            "\nModel: " + car.getModel() +
                            "\nYear: " + car.getYear() +
                            "\nPrice per day:"+ (String.valueOf(car.getPricePerDay()))+
                    "\nLicense Plate: " + car.getLicensePlate() +
                    "\nCondition: " + car.getCarCondition() +
                    "\nLocation: " + car.getLocation() +
                    "\nOwner: " + (car.getUser() != null ? car.getUser().getName() : "N/A")
            );
        }

        //infoPanel.removeAll();


//        JTextArea descriptionArea = new JTextArea(car.getDescription());
//        descriptionArea.setWrapStyleWord(true);
//        descriptionArea.setLineWrap(true);
//        descriptionArea.setEditable(false);
//        detailsArea.add(new JScrollPane(descriptionArea));
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void addLabelAndValue(String label, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(new JLabel(value));
        infoPanel.add(panel);
    }

    private void launchRentingView(ActionEvent actionEvent){
        CarModel selectedCar = itemList.getSelectedValue();
        RentCarView registerCarView = new RentCarView(selectedCar);
        registerCarView.setVisible(true);
    }

    public void updateCarList() {
        model.clear();
        java.util.List<CarModel> cars = ClientCarController.getAllCars();
        for (CarModel car : cars) {
            //if (car.getStatus() == CarModel.Status.OPEN) {
                model.addElement(car);
            //}
        }
        if (!model.isEmpty()) {
            itemList.setSelectedIndex(0);  // Set the selection to the first item after updating
            updateInfoPanel(itemList.getSelectedValue());  // Update the info panel to reflect changes
        } else {
            infoPanel.removeAll();  // Clear the info panel if no cars are available
            infoPanel.repaint();
        }
    }

}
