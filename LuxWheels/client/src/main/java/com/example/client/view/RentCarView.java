package com.example.client.view;

import com.example.client.controller.ClientCarController;
import com.example.client.controller.ClientRentalController;
import com.example.client.model.CarModel;
import com.example.client.model.RentalModel;
import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class RentCarView extends JFrame {
    public static RentCarView instance;
    private MainFrame mainFrame;
    private CarModel selectedCar;

    private JTextField startDateField;
    private JTextField endDateField;
    private JButton submitButton;
    private JButton cancelButton;
    private JCalendar availabilityCalendar;

    private LocalDate startDate;
    private LocalDate endDate;
    private Set<LocalDate> selectedDates;
    private Set<LocalDate> unavailableDates;
    private boolean selectingStartDate = true;

    public RentCarView(CarModel selectedCar) {
        this.selectedCar = selectedCar;
        this.selectedDates = new HashSet<>();
        this.unavailableDates = new HashSet<>();

        fillUnavailableDates(ClientRentalController.getRentalsByLicensePlate(selectedCar.getLicensePlate()));

        setTitle("Car registration - LuxWheels");
        setSize(700, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void fillUnavailableDates(List<RentalModel> rentals) {
        for (RentalModel rental : rentals) {
            LocalDate firstDate = LocalDate.parse(rental.getStartDate());
            LocalDate lastDate = LocalDate.parse(rental.getEndDate());
            for (LocalDate d = firstDate; !d.isAfter(lastDate); d = d.plusDays(1)) {
                unavailableDates.add(d);
            }
        }
    }

    private void initUI() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
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

        // Date text fields
        JLabel startDateLabel = new JLabel("Start Date:");
        startDateField = new JTextField();
        startDateField.setEditable(false);

        JLabel endDateLabel = new JLabel("End Date:");
        endDateField = new JTextField();
        endDateField.setEditable(false);

        formPanel.add(startDateLabel);
        formPanel.add(startDateField);
        formPanel.add(endDateLabel);
        formPanel.add(endDateField);

        // Buttons
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

        // Availability Calendar
        availabilityCalendar = new JCalendar();
        availabilityCalendar.setFont(labelFont);
        updateCalendar();

        // Add listeners to update the calendar when the month or year is changed, and if day is clicked
        availabilityCalendar.getDayChooser().addPropertyChangeListener("day", evt -> handleCalendarClick());
        availabilityCalendar.getMonthChooser().addPropertyChangeListener("month", evt -> updateCalendar());
        availabilityCalendar.getYearChooser().addPropertyChangeListener("year", evt -> updateCalendar());

        // Add panels to layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(formPanel);
        contentPanel.add(availabilityCalendar);

        add(contentPanel, BorderLayout.NORTH);
    }

    private void handleCalendarClick() {
        Date selectedDate = availabilityCalendar.getDate();
        LocalDate localDate = convertToLocalDate(selectedDate);

        if (localDate == null || unavailableDates.contains(localDate)) {
            clearSelectedDates();
            JOptionPane.showMessageDialog(this, "Unavailable date clicked. Please try again.");
            selectingStartDate = true;
            return;
        }

        if (selectingStartDate) {
            selectedDates.clear();
            startDate = localDate;
            endDate = null;
            startDateField.setText(startDate.toString());
            endDateField.setText("");
            selectingStartDate = false;
            selectedDates.add(localDate);
            updateCalendar();
        } else {
            endDate = localDate;
            if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
                JOptionPane.showMessageDialog(this, "End date must be after start date. Please select the dates again.");
                startDate = null;
                endDate = null;
                startDateField.setText("");
                endDateField.setText("");
                selectingStartDate = true;
            } else {
                endDateField.setText(endDate.toString());
                updateSelectedDates();
                if (datesIntersectWithUnavailableDates()) {
                    JOptionPane.showMessageDialog(this, "Selected dates intersect with unavailable dates. Please choose different dates.");
                    clearSelectedDates();
                } else {
                    updateCalendar();
                }
                selectingStartDate = true; // Reset to allow new selection if needed
            }
        }
    }

    private LocalDate convertToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void updateSelectedDates() {
        selectedDates.clear();
        if (startDate != null && endDate != null) {
            LocalDate date = startDate;
            while (!date.isAfter(endDate)) {
                selectedDates.add(date);
                date = date.plusDays(1);
            }
        }
    }

    private void clearSelectedDates() {
        startDate = null;
        endDate = null;
        selectedDates.clear();
        startDateField.setText("");
        endDateField.setText("");
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
            if (comp instanceof JButton) {
                ((JButton) comp).setForeground(Color.BLACK);
            }
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
                    for (Component comp : dayComponents) {
                        if (comp instanceof JButton) {
                            JButton button = (JButton) comp;
                            try {
                                int day = Integer.parseInt(button.getText());
                                if (day == dayOfMonth) {
                                    button.setBackground(new Color(10, 7, 56));
                                    button.setForeground(new Color(245, 245, 245));
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
                    for (Component comp : dayComponents) {
                        if (comp instanceof JButton) {
                            JButton button = (JButton) comp;
                            try {
                                int day = Integer.parseInt(button.getText());
                                if (day == dayOfMonth) {
                                    button.setBackground(new Color(54, 209, 96));
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
        if (startDate != null && endDate != null) {

            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            Double rentalPrice = selectedCar.getPricePerDay() * daysBetween;

            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Final rental price: " + rentalPrice + " - Please confirm by clicking 'Yes'",
                    "Confirm Car Rental",
                    JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                String carModelsModel = selectedCar.getModel();
                String carBrand = selectedCar.getBrand();
                String carPlate = String.valueOf(selectedCar.getLicensePlate());
                System.out.println("-------- Renting car --------");
                System.out.println("Rented car: " + carBrand + " " + carModelsModel);
                System.out.println("Car Plate: " + carPlate);
                System.out.println("Start date: " + startDate.toString() + " - End date: " + endDate.toString());
                System.out.println("Rental Period: " + String.valueOf(daysBetween) + " days");

                ClientRentalController.createRental(selectedCar, startDate, endDate, rentalPrice);
                System.out.println("Rental creation button clicked");
                closeWindow();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select both start and end dates.");
            return;
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
}