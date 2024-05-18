package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.controller.ClientRentalController;
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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

public class RentCarView extends JFrame {
    public static RentCarView instance;
    private MainFrame mainFrame;
    private CarModel selectedCar;

    private JTextField carModelField;
    private JTextField carBrandField;
    private JTextField carPlateField;
    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;
    private JButton submitButton;
    private JButton cancelButton;
    private JCalendar availabilityCalendar;

    private LocalDate startDate;
    private LocalDate endDate;
    private Set<LocalDate> selectedDates;
    private Set<LocalDate> unavailableDates;

    public RentCarView(CarModel selectedCar) {
        //this.mainFrame = mainFrame;
        this.selectedCar = selectedCar;
        this.selectedDates = new HashSet<>();
        this.unavailableDates = new HashSet<>();

        System.out.println("Selected car: carLicensePlate=" + selectedCar.getLicensePlate());

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

        // --------- JDatePicker section ---------

        SqlDateModel startDateModel = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startDateModel, p);
        startDatePicker = new JDatePickerImpl(startDatePanel, new DateComponentFormatter());

        SqlDateModel endDateModel = new SqlDateModel();
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endDateModel, p);
        endDatePicker = new JDatePickerImpl(endDatePanel, new DateComponentFormatter());

        formPanel.add(new JLabel("Select Start Date:"));
        formPanel.add(startDatePicker);
        formPanel.add(new JLabel("Select End Date:"));
        formPanel.add(endDatePicker);

        // --------- Availability Calendar section ---------

        availabilityCalendar = new JCalendar();
        availabilityCalendar.setFont(labelFont);
        availabilityCalendar.getDayChooser().addPropertyChangeListener("day", evt -> updateCalendar());
        updateCalendar();

        // Add listeners to update the calendar when the month or year is changed
        availabilityCalendar.getMonthChooser().addPropertyChangeListener("month", evt -> updateCalendar());
        availabilityCalendar.getYearChooser().addPropertyChangeListener("year", evt -> updateCalendar());

        // Add PropertyChangeListeners to the date pickers
        startDatePicker.getModel().addChangeListener(e -> {
            LocalDate newStartDate = convertToLocalDate((Date) startDatePicker.getModel().getValue());
            if (newStartDate != null && (endDate == null || newStartDate.isBefore(endDate) || newStartDate.equals(endDate))) {
                startDate = newStartDate;
                updateSelectedDates();
                if (datesIntersectWithUnavailableDates()) {
                    JOptionPane.showMessageDialog(this, "Selected dates intersect with unavailable dates. Please choose different dates.");
                    clearSelectedDates();
                } else {
                    updateCalendar();
                }
            }
        });

        endDatePicker.getModel().addChangeListener(e -> {
            LocalDate newEndDate = convertToLocalDate((Date) endDatePicker.getModel().getValue());
            if (newEndDate != null && (startDate == null || newEndDate.isAfter(startDate) || newEndDate.equals(startDate))) {
                endDate = newEndDate;
                updateSelectedDates();
                if (datesIntersectWithUnavailableDates()) {
                    JOptionPane.showMessageDialog(this, "Selected dates intersect with unavailable dates. Please choose different dates.");
                    clearSelectedDates();
                } else {
                    updateCalendar();
                }
            }
        });

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

        // --------- Add panels to layout ---------

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(formPanel);
        contentPanel.add(availabilityCalendar);

        add(contentPanel, BorderLayout.NORTH);
    }

    private LocalDate convertToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        } else {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    private void updateSelectedDates() {
        selectedDates.clear();
        if (startDate != null) {
            selectedDates.add(startDate);
        }
        if (endDate != null) {
            selectedDates.add(endDate);
        }
        if (startDate != null && endDate != null) {
            LocalDate date = startDate.plusDays(1);
            while (date.isBefore(endDate)) {
                selectedDates.add(date);
                date = date.plusDays(1);
            }
        }
    }

    private void clearSelectedDates() {
        startDate = null;
        endDate = null;
        selectedDates.clear();
        updateCalendar();
    }

    private boolean datesIntersectWithUnavailableDates() {
        for (LocalDate date : selectedDates) {
            if (unavailableDates.contains(date)) {
                return true;
            }
        }
        return false;
    }

    private void updateCalendar() {
        // Clear previous selections
        for (Component comp : availabilityCalendar.getDayChooser().getDayPanel().getComponents()) {
            comp.setBackground(Color.WHITE);
        }

        // Highlight unavailable dates
        for (LocalDate date : unavailableDates) {
            if (date != null) {
                Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                if (month == availabilityCalendar.getMonthChooser().getMonth() &&
                        year == availabilityCalendar.getYearChooser().getYear()) {
                    // Loop over all the components of the day panel
                    Component[] dayComponents = availabilityCalendar.getDayChooser().getDayPanel().getComponents();
                    for (int i = 0; i < dayComponents.length; i++) {
                        Component comp = dayComponents[i];
                        if (comp instanceof JButton) {
                            JButton button = (JButton) comp;
                            try {
                                int day = Integer.parseInt(button.getText());
                                if (day == dayOfMonth) {
                                    button.setBackground(Color.BLUE);
                                    break; // Exit loop once the date is found and highlighted
                                }
                            } catch (NumberFormatException e) {
                                // Ignore non-day buttons (e.g., empty buttons or header buttons)
                            }
                        }
                    }
                }
            }
        }

        // Highlight selected dates
        for (LocalDate date : selectedDates) {
            if (date != null) {
                Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                if (month == availabilityCalendar.getMonthChooser().getMonth() &&
                        year == availabilityCalendar.getYearChooser().getYear()) {
                    // Loop over all the components of the day panel
                    Component[] dayComponents = availabilityCalendar.getDayChooser().getDayPanel().getComponents();
                    for (int i = 0; i < dayComponents.length; i++) {
                        Component comp = dayComponents[i];
                        if (comp instanceof JButton) {
                            JButton button = (JButton) comp;
                            try {
                                int day = Integer.parseInt(button.getText());
                                if (day == dayOfMonth) {
                                    button.setBackground(Color.LIGHT_GRAY);
                                    break; // Exit loop once the date is found and highlighted
                                }
                            } catch (NumberFormatException e) {
                                // Ignore non-day buttons (e.g., empty buttons or header buttons)
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleSubmit() {
        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to register this car?",
                "Confirm Car Registration",
                JOptionPane.YES_NO_OPTION
        );
        if (option == JOptionPane.YES_OPTION) {
            Double carPrice = 0.0;
            long daysBetween = 0;

            if (startDate != null && endDate != null) {

                daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
                String carModelsModel = selectedCar.getModel();
                String carBrand = selectedCar.getBrand();
                String carPlate = String.valueOf(selectedCar.getLicensePlate());
                carPrice = selectedCar.getPricePerDay();
                System.out.println("-------- Renting car --------");
                System.out.println("Rented car: " + carBrand + " " + carModelsModel);
                System.out.println("Car Plate: " + carPlate);
                System.out.println("Start date: " + startDate.toString() + " - End date: " + endDate.toString());
                System.out.println("Rental Period: " + String.valueOf(daysBetween) + " days");

            } else {
                JOptionPane.showMessageDialog(this, "Please select both start and end dates.");
            }

            ClientRentalController.createRental(selectedCar, startDate, endDate, carPrice * daysBetween);
            System.out.println("Rental creation button clicked");
            closeWindow();

            // this.mainFrame.getCarListView().updateCarList();
        }
    }

    private void handleCancel() {
        clearSelectedDates();
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
