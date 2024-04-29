package com.example.client.view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
public class CarListView extends JPanel {
    private JList<String> itemList;
    private DefaultListModel<String> model;
    private JTextArea infoPanel;

    public CarListView() {
        setLayout(new BorderLayout());

        // Model to hold items
        model = new DefaultListModel<>();
        addItem("Car 1 - BMW");
        addItem("Car 2 - Audi");
        addItem("Car 3 - Mercedes");

        itemList = new JList<>(model);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
                    String selectedItem = itemList.getSelectedValue();
                    updateInfoPanel(selectedItem);
                }
            }
        });

        if (model.getSize() > 0) {
            itemList.setSelectedIndex(0);
            updateInfoPanel(itemList.getSelectedValue());
        }
    }

    private void addItem(String item) {
        model.addElement(item);
    }

    // Method to update the information panel based on the selected item
    private void updateInfoPanel(String item) {
        // Placeholder for actual content fetching logic
        infoPanel.setText("Details of " + item + ":\n\nHere you can put more detailed information about the car.");
        // Here you could add more detailed dynamic data, potentially fetching it from a database or a service
    }
}
