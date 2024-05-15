package com.example.client.view;

import com.example.client.model.CarModel;
import com.toedter.calendar.JCalendar;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.stream.Stream;

public class RentCarView extends JFrame {
    public static RentCarView instance;
    private MainFrame mainFrame;
    private CarModel selectedCar;

    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;
    private JButton submitButton;
    private JButton cancelButton;
    private JCalendar availabilityCalendar;

    public RentCarView(CarModel selectedCar) {
        //this.mainFrame = mainFrame;
        this.selectedCar = selectedCar;
        setTitle("Car registration - LuxWheels");
        setSize(700, 600); // Increased size to accommodate the calendar
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        Font labelFont = new Font("SansSerif", Font.BOLD, 12);

        JLabel carModelLabel = new JLabel("Car Model:");
        JLabel selectedCarModel = new JLabel(this.selectedCar.getModel());

        JLabel carBrandLabel = new JLabel("Car Brand:");
        JLabel selectedCarBrand = new JLabel(this.selectedCar.getBrand());

        JLabel carPlateLabel = new JLabel("Car Plate:");
        JLabel selectedCarPlate = new JLabel(String.valueOf(this.selectedCar.getLicensePlate()));

        Stream.of(carModelLabel, selectedCarModel, carBrandLabel, selectedCarBrand, carPlateLabel, selectedCarPlate)
                .forEach(label -> label.setFont(labelFont));

        formPanel.add(carModelLabel);
        formPanel.add(selectedCarModel);
        formPanel.add(carBrandLabel);
        formPanel.add(selectedCarBrand);
        formPanel.add(carPlateLabel);
        formPanel.add(selectedCarPlate);

        // --------- Calendar section ---------

        JLabel startDateLabel = new JLabel("Start Date:");
        SqlDateModel startDateModel = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, p);
        startDatePicker = new JDatePickerImpl(startDatePanel, new DateComponentFormatter());

        JLabel endDateLabel = new JLabel("End Date:");
        SqlDateModel endDateModel = new SqlDateModel();
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, p);
        endDatePicker = new JDatePickerImpl(endDatePanel, new DateComponentFormatter());

        Stream.of(startDateLabel, startDatePicker, endDateLabel, endDatePicker)
                .forEach(component -> formPanel.add(component instanceof JLabel ? (JLabel) component : (Component) component));

        // --------- Buttons section ---------

        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancel();
            }
        });

        formPanel.add(submitButton);
        formPanel.add(cancelButton);

        // --------- Availability Calendar section ---------

        availabilityCalendar = new JCalendar();

        // You might want to add custom rendering or highlighting of unavailable dates here
        // availabilityCalendar.getDayChooser().addDateEvaluator(new DateEvaluator() {
        //     @Override
        //     public boolean isSpecial(Date date) {
        //         // Implement logic to determine if a date is special (e.g., unavailable)
        //         return false;
        //     }

        //     @Override
        //     public Color getSpecialForegroundColor() {
        //         return Color.RED;
        //     }

        //     @Override
        //     public Color getSpecialBackroundColor() {
        //         return Color.LIGHT_GRAY;
        //     }

        //     @Override
        //     public String getSpecialTooltip() {
        //         return "Unavailable";
        //     }
        // });

        // --------- Add panels to layout ---------

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(formPanel);
        contentPanel.add(availabilityCalendar);

        add(contentPanel, BorderLayout.NORTH);
    }

    private void handleSubmit() {
        String carModel = selectedCar.getModel();
        String carBrand = selectedCar.getBrand();
        String carPlate = String.valueOf(selectedCar.getLicensePlate());
        LocalDate startDate = (LocalDate) startDatePicker.getModel().getValue();
        LocalDate endDate = (LocalDate) endDatePicker.getModel().getValue();

        if (startDate != null && endDate != null && !endDate.isBefore(startDate)) {
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            System.out.println("Car Model: " + carModel);
            System.out.println("Car Brand: " + carBrand);
            System.out.println("Car Plate: " + carPlate);
            System.out.println("Rental Period: " + daysBetween + " days");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid date range selected.");
        }
    }

    private void handleCancel() {
        startDatePicker.getModel().setValue(null);
        endDatePicker.getModel().setValue(null);
    }


    private void createRental(ActionEvent e) {

    }

    public static RentCarView getInstance(MainFrame mainFrame) {
        if (instance == null) {
            //instance = new RentCarView(mainFrame);
        }
        return instance;
    }

    public void closeWindow() {
        this.dispose();
        RentCarView.instance = null;
    }

    /* MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RentCarView.getInstance().setVisible(true);
        });
    }
     */

}
