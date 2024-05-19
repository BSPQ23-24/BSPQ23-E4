package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.model.CarModel;
import com.example.client.model.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class HostedCarsView extends JPanel {
    private static final Logger logger = LogManager.getLogger(LoginView.class);
    private DefaultListModel<CarModel> carListModel;
    private JList<CarModel> carList;
    private JPanel infoPanel;
    private UserModel currentUser;
    private Locale locale;
    private ResourceBundle messages;

    public HostedCarsView(UserModel user, Locale locale, ResourceBundle messages) {
        this.locale = locale;
        this.messages = messages;
        logger.info("HostedCarsView generated");
        setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel(messages.getString("label.availableCars"));
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        add(headingLabel, BorderLayout.NORTH);
        logger.info("HostedCarsView generated");
        this.currentUser = user;
        this.setLayout(new BorderLayout());

        carListModel = new DefaultListModel<>();
        loadUserCars();

        carList = new JList<>(carListModel);
        carList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CarModel) {
                    setText(((CarModel) value).getSummary());
                }
                return renderer;
            }
        });

        JScrollPane listScrollPane = new JScrollPane(carList);

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        JScrollPane infoScrollPane = new JScrollPane(infoPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, infoScrollPane);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

        carList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                CarModel selectedCar = carList.getSelectedValue();
                updateInfoPanel(selectedCar);
            }
        });

        if (!carListModel.isEmpty()) {
            carList.setSelectedIndex(0);
            updateInfoPanel(carList.getSelectedValue());
        }
    }

    // Loads the list of cars hosted by the current user
    public void loadUserCars() {
        List<CarModel> allCars = ClientCarController.getAllCars();
        carListModel.clear();

        for (CarModel car : allCars) {
            if (car.getUser().getId() != null && car.getUser().getId().equals(currentUser.getId())) {
                carListModel.addElement(car);
            }
        }
    }

    private void updateInfoPanel(CarModel car) {
        if (car == null) {
            System.out.println(messages.getString("message.noCarSelected"));
            return;
        }
        infoPanel.removeAll();

        addLabelAndValue(messages.getString("label.brand"), car.getBrand());
        addLabelAndValue(messages.getString("label.model"), car.getModel());
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        JButton modifyButton = new JButton(messages.getString("button.modifyCar"));
        modifyButton.setBackground(Color.GREEN);
        JButton deleteButton = new JButton(messages.getString("button.deleteCar"));
        deleteButton.setBackground(Color.RED);

        deleteButton.addActionListener(e -> {
            ClientCarController.deleteCar(car.getLicensePlate());
            logger.info("Delete Car");
            logger.info(car.getLicensePlate());
            this.loadUserCars();
        });

        buttonPanel.add(modifyButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(deleteButton);

        infoPanel.add(buttonPanel);
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void addLabelAndValue(String label, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(new JLabel(value));
        infoPanel.add(panel);
    }
}
