package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.model.CarModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class CarListView extends JPanel {
    private static final Logger logger = LogManager.getLogger(CarListView.class);
    private JList<CarModel> itemList;
    private DefaultListModel<CarModel> model;
    private JPanel infoPanel;
    private Locale locale;
    private ResourceBundle messages;

    public CarListView(Locale locale, ResourceBundle messages) {
        this.locale = locale;
        this.messages = messages;
        logger.info("CarListView generated");
        setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel(messages.getString("label.availableCars"));
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        add(headingLabel, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        java.util.List<CarModel> cars = ClientCarController.getAllCars();
        for (CarModel car : cars) {
            //if (car.getStatus() == CarModel.Status.OPEN) {
            model.addElement(car);
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

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        JScrollPane infoScrollPane = new JScrollPane(infoPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, infoScrollPane);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.5);

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
            System.out.println(messages.getString("label.noCarSelected"));
            return;
        }
        infoPanel.removeAll();

        addLabelAndValue(messages.getString("label.brand"), car.getBrand());
        addLabelAndValue(messages.getString("label.model"), car.getModel());
        addLabelAndValue(messages.getString("label.pricePerDay"), String.valueOf(car.getPricePerDay()));
        addLabelAndValue(messages.getString("label.year"), car.getYear());
        addLabelAndValue(messages.getString("label.condition"), car.getCarCondition().toString());
        addLabelAndValue(messages.getString("label.location"), car.getLocation());
        if (car.getUser() != null) {
            addLabelAndValue(messages.getString("label.owner"), car.getUser().getName());
        }

        JTextArea descriptionArea = new JTextArea(car.getDescription());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        infoPanel.add(new JScrollPane(descriptionArea));

        JButton rentButton = new JButton(messages.getString("button.rentNow"));
        rentButton.setBackground(Color.GREEN);
        rentButton.addActionListener(this::launchRentingView);
        infoPanel.add(rentButton);

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
        RentCarView rentCarView = new RentCarView(selectedCar, locale, messages);
        rentCarView.setVisible(true);
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
