package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.model.CarModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;


public class CarListView extends JPanel {
    private JList<CarModel> itemList;
    private DefaultListModel<CarModel> model;
    private JTextArea infoPanel;

    public CarListView() {
        setLayout(new BorderLayout());

        model = new DefaultListModel<>();
        List<CarModel> cars = ClientCarController.getAllCars();
        for (CarModel car : cars) {
            model.addElement(car);
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

        infoPanel = new JTextArea(10, 20);
        infoPanel.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(infoPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, infoScrollPane);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

        itemList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    CarModel selectedCar = itemList.getSelectedValue();
                    updateInfoPanel(selectedCar);
                }
            }
        });

        if (!model.isEmpty()) {
            itemList.setSelectedIndex(0);
            updateInfoPanel(itemList.getSelectedValue());
        }
    }

    private void updateInfoPanel(CarModel car) {
        infoPanel.setText("Details of " + car.getBrand());
    }
}
