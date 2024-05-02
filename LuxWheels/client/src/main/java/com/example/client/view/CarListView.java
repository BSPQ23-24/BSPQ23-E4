package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.model.CarModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.swing.*;
import java.awt.*;

public class CarListView extends JPanel {
	private static final Logger logger = LogManager.getLogger(LoginView.class);
    private JList<CarModel> itemList;
    private DefaultListModel<CarModel> model;
    private JPanel infoPanel;

    public CarListView() {
        logger.info("CarListView generated");
        setLayout(new BorderLayout());

        model = new DefaultListModel<>();
        java.util.List<CarModel> cars = ClientCarController.getAllCars();
        for (CarModel car : cars) {
            if (car.getStatus() == CarModel.Status.OPEN) {
                model.addElement(car);
            }
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
            System.out.println("No car selected or car data is null");
            return;
        }
        infoPanel.removeAll();

        addLabelAndValue("Brand:", car.getBrand());
        addLabelAndValue("Model:", car.getModel());
        addLabelAndValue("Year:", car.getYear());
        addLabelAndValue("Condition:", car.getCarCondition().toString());
        addLabelAndValue("Location:", car.getLocation());
        if (car.getUser() != null) {
            addLabelAndValue("Owner:", car.getUser().getName());
        }
        addLabelAndValue("Status:", car.getStatus().toString());

        JTextArea descriptionArea = new JTextArea(car.getDescription());
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        infoPanel.add(new JScrollPane(descriptionArea));

        JButton rentButton = new JButton("Rent Now!");
        rentButton.setBackground(Color.GREEN);
        rentButton.addActionListener(e -> {
            //TODO
        });
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

    public void updateCarList() {
        model.clear();
        java.util.List<CarModel> cars = ClientCarController.getAllCars();
        for (CarModel car : cars) {
            if (car.getStatus() == CarModel.Status.OPEN) {
                model.addElement(car);
            }
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
