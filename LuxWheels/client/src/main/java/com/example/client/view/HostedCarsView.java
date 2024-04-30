package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.model.CarModel;
import com.example.client.model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HostedCarsView extends JPanel {
    private DefaultListModel<CarModel> carListModel;
    private JList<CarModel> carList;
    private JScrollPane scrollPane;
    private UserModel currentUser;

    public HostedCarsView(UserModel user) {
        this.currentUser = user;
        this.setLayout(new BorderLayout());

        carListModel = new DefaultListModel<>();
        loadUserCars();

        carList = new JList<>(carListModel);
        carList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        carList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CarModel) {
                    CarModel car = (CarModel) value;
                    setText(car.getSummary());
                }
                return component;
            }
        });

        scrollPane = new JScrollPane(carList);
        add(scrollPane, BorderLayout.CENTER);

        if (!carListModel.isEmpty()) {
            carList.setSelectedIndex(0);
        }
    }

    // Loads the list of cars hosted by the current user
    private void loadUserCars() {
        List<CarModel> allCars = ClientCarController.getAllCars();
        carListModel.clear();

        for (CarModel car : allCars) {
            if (car.getUserId() != null && car.getUserId().equals(currentUser.getId())) {
                carListModel.addElement(car);
            }
        }
    }
}
