package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.model.CarModel;
import com.example.client.model.CarModel.CarCondition;
import com.example.client.model.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HostedCarsView extends JPanel {
	private static final Logger logger = LogManager.getLogger(LoginView.class);
    private DefaultListModel<CarModel> carListModel;
    private JList<CarModel> carList;
    private JPanel infoPanel;
    private UserModel currentUser;

    public HostedCarsView(UserModel user) {
        logger.info("CarListView generated");
        setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel("Available Cars for Rent");
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
            System.out.println("No car selected or car data is null");
            return;
        }
        infoPanel.removeAll();


        addLabelAndTextField("Brand:", car.getBrand());
        addLabelAndTextField("Model:", car.getModel());
        addLabelAndTextField("Year:", car.getYear());
        addLabelAndTextField("Condition:", car.getCarCondition().toString());
        addLabelAndTextField("Location:", car.getLocation());
        if (car.getUser() != null) {
            addLabelAndTextField("Owner:", car.getUser().getName());
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING)); 
        
        JButton modifyButton = new JButton("Modify car");
        modifyButton.setBackground(Color.GREEN);
        JButton deleteButton = new JButton("Delete car");
        deleteButton.setBackground(Color.RED);
        
        deleteButton.addActionListener(e -> {
            ClientCarController.deleteCar(car.getLicensePlate());
            logger.info("Delete Car");
            logger.info(car.getLicensePlate());
            this.loadUserCars();
            
        });
        
        modifyButton.addActionListener(e -> this.modifyCar());
        
        buttonPanel.add(modifyButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); 
        buttonPanel.add(deleteButton);
        
        
        infoPanel.add(buttonPanel);
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void addLabelAndTextField(String label, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labelComponent = new JLabel(label);
        JTextField textField = new JTextField(value, 20); // Establece el ancho preferido a 20 caracteres
        panel.add(labelComponent);
        panel.add(textField);
        infoPanel.add(panel);
    }
    
    private void modifyCar() {
        CarModel car = carList.getSelectedValue(); // Obtén el coche seleccionado en la lista

        if (car != null) { // Asegúrate de que se haya seleccionado un coche
            for (Component component : infoPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel panel = (JPanel) component;
                    for (Component innerComponent : panel.getComponents()) {
                        if (innerComponent instanceof JTextField) {
                            JTextField textField = (JTextField) innerComponent;
                            String label = ((JLabel) panel.getComponent(0)).getText();
                            String valor = textField.getText();
                            System.out.println(label);
                            if(label.equals("Owner:")) {
                            	 textField.setEditable(false);
                            }
                            
                            // Actualiza los atributos correspondientes del objeto CarModel
                            switch(label) {
                                case "Brand:":
                                    car.setBrand(valor);
                                    break;
                                case "Model:":
                                    car.setModel(valor);
                                    break;
                                case "Year:":
                                    car.setYear(valor);
                                    break;
                                case "Condition:":
                                	CarCondition condition = CarCondition.valueOf(valor);
                                    car.setCarCondition(condition);
                                    break;
                                case "Location:":
                                    car.setLocation(valor);
                                    break;
                            }
                        }
                    }
                }
                
            }
            System.out.println(car.getBrand());
            ClientCarController.updateCar(car);
            this.loadUserCars();
            
            

        } else {
            System.out.println("No car selected");
        }
    }


    


    
    
}
