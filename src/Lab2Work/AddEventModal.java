package Lab2Work;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

public class AddEventModal extends JDialog {
    private final EventListPanel eventListPanel;

    public AddEventModal(JFrame parent, EventListPanel eventListPanel) {
        super(parent, "Add Event", true);
        this.eventListPanel = eventListPanel;

        setLayout(new GridLayout(0, 2)); // Use a grid layout for input fields

        // Input fields for event details
        JTextField nameField = new JTextField();
        addKeyListenerToField(nameField);
        // Start Date and Time Input
        JTextField startYearField = new JTextField();
        addKeyListenerToField(startYearField);
        JTextField startMonthField = new JTextField();
        addKeyListenerToField(startMonthField);
        JTextField startDayField = new JTextField();
        addKeyListenerToField(startDayField);
        JTextField startHourField = new JTextField();
        addKeyListenerToField(startHourField);
        JTextField startMinuteField = new JTextField();
        addKeyListenerToField(startMinuteField);

        // AM/PM dropdown
        String[] amPmOptions = {"AM", "PM"};
        JComboBox<String> startAmPmDropdown = new JComboBox<>(amPmOptions);
        // End Date and Time Input
        JTextField endHourField = new JTextField();
        addKeyListenerToField(endHourField);
        JTextField endMinuteField = new JTextField();
        addKeyListenerToField(endMinuteField);
        // AM/PM dropdown for end time
        JComboBox<String> endAmPmDropdown = new JComboBox<>(amPmOptions);
        JTextField locationField = new JTextField();
        addKeyListenerToField(locationField);

        add(new JLabel("Event Name:"));
        add(nameField);
        add(new JLabel("Start Year(xxxx):"));
        add(startYearField);
        add(new JLabel("Start Month(xx):"));
        add(startMonthField);
        add(new JLabel("Start Day(xx):"));
        add(startDayField);
        add(new JLabel("Start Hour (1-12):"));
        add(startHourField);
        add(new JLabel("Start Minute(xx):"));
        add(startMinuteField);
        add(new JLabel("Start AM/PM:"));
        add(startAmPmDropdown);
        add(new JLabel("End Hour (1-12):"));
        add(endHourField);
        add(new JLabel("End Minute(xx):"));
        add(endMinuteField);
        add(new JLabel("End AM/PM:"));
        add(endAmPmDropdown);
        add(new JLabel("Location: "));
        add(locationField);

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(e -> {
            try {
                // Collect input values and create a new event
                String name = nameField.getText();
                String location = locationField.getText();
                int startYear = Integer.parseInt(startYearField.getText());
                int startMonth = Integer.parseInt(startMonthField.getText());
                int startDay = Integer.parseInt(startDayField.getText());
                int startHour = Integer.parseInt(startHourField.getText());
                int startMinute = Integer.parseInt(startMinuteField.getText());
                String startAmPm = (String) startAmPmDropdown.getSelectedItem();
                if ("PM".equals(startAmPm) && startHour < 12) {
                    startHour += 12; // Convert to military time
                } else if ("AM".equals(startAmPm) && startHour == 12) {
                    startHour = 0; // Midnight case
                }
                LocalDateTime startTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute);

                int endHour = Integer.parseInt(endHourField.getText());
                int endMinute = Integer.parseInt(endMinuteField.getText());
                String endAmPm = (String) endAmPmDropdown.getSelectedItem();
                if ("PM".equals(endAmPm) && endHour < 12) {
                    endHour += 12; // Convert to military time
                } else if ("AM".equals(endAmPm) && endHour == 12) {
                    endHour = 0; // Midnight case
                }
                LocalDateTime endTime = LocalDateTime.of(startYear, startMonth, startDay, endHour, endMinute);

                // Create and add the new event
                Meeting newMeeting = new Meeting(name, startTime, endTime, location);
                eventListPanel.addEvent(newMeeting);

                // Close the dialog after adding the event
                dispose(); // Close the modal
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for date and time.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(addButton);
        pack(); // Adjust dialog size
        setLocationRelativeTo(parent); // Center dialog
    }

    private void addKeyListenerToField(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume(); // Ignore Enter key
                }
            }
        });
    }
}

