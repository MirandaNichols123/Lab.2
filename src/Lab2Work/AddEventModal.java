package Lab2Work;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class AddEventModal extends JDialog {
    private final EventListPanel eventListPanel;

    // Meeting input fields
    private JTextField meetingNameField;
    private JTextField meetingStartDay;
    private JTextField meetingStartMonth;
    private JTextField meetingStartYear;
    private JTextField meetingStartHour;
    private JTextField meetingStartMinute;
    private JComboBox<String> meetingStartAmPm;
    private JTextField meetingEndHour;
    private JTextField meetingEndMinute;
    private JComboBox<String> meetingEndAmPm;
    private JTextField meetingLocation;

    // Deadline input fields
    private JTextField deadlineNameField;
    private JTextField deadlineStartDay;
    private JTextField deadlineStartMonth;
    private JTextField deadlineStartYear;
    private JTextField deadlineStartHour;
    private JTextField deadlineStartMinute;
    private JComboBox<String> deadlineAmPm;

    private final JPanel cardPanel;  // Panel to hold different input forms
    private final CardLayout cardLayout;  // Layout manager for switching cards

    public AddEventModal(JFrame parent, EventListPanel eventListPanel) {
        super(parent, "Add Event", true);
        this.eventListPanel = eventListPanel;

        setLayout(new BorderLayout());

        // Create the dropdown to select event type
        String[] eventTypes = {"Meeting", "Deadline"};
        JComboBox<String> eventTypeDropdown = new JComboBox<>(eventTypes);
        eventTypeDropdown.addActionListener(_ -> updateForm((String) eventTypeDropdown.getSelectedItem()));

        // Add dropdown to the top
        add(eventTypeDropdown, BorderLayout.NORTH);

        // Create the panel to switch between forms
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create separate forms for "Meeting" and "Deadline"
        JPanel meetingForm = createMeetingForm();
        JPanel deadlineForm = createDeadlineForm();

        // Add forms to the card panel
        cardPanel.add(meetingForm, "Meeting");
        cardPanel.add(deadlineForm, "Deadline");

        add(cardPanel, BorderLayout.CENTER);

        // Show Meeting form by default
        updateForm("Meeting");

        // Add a button to submit the form
        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(_ -> handleAddEvent((String) Objects.requireNonNull(eventTypeDropdown.getSelectedItem())));
        add(addButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setSize(300, 300);
    }

    // Meeting fields: Name, Start Time, End Time, and Location
    private JPanel createMeetingForm() {
        JPanel meetingPanel = new JPanel(new GridLayout(0, 2));

        meetingPanel.add(new JLabel("Meeting Name:"));
        meetingNameField = new JTextField();
        meetingPanel.add(meetingNameField);

        // Add fields for meeting start time and end time
        addMeetingTimeFields(meetingPanel);

        meetingPanel.add(new JLabel("Location:"));
        meetingLocation = new JTextField();
        meetingPanel.add(meetingLocation);

        return meetingPanel;
    }

    private void addMeetingTimeFields(JPanel panel) {
        panel.add(new JLabel("Start Year:"));
        meetingStartYear = new JTextField();
        panel.add(meetingStartYear);

        panel.add(new JLabel("Start Month:"));
        meetingStartMonth = new JTextField();
        panel.add(meetingStartMonth);

        panel.add(new JLabel("Start Day:"));
        meetingStartDay = new JTextField();
        panel.add(meetingStartDay);

        panel.add(new JLabel("Start Hour:"));
        meetingStartHour = new JTextField();
        panel.add(meetingStartHour);

        panel.add(new JLabel("Start Minute:"));
        meetingStartMinute = new JTextField();
        panel.add(meetingStartMinute);

        panel.add(new JLabel("AM/PM:"));
        meetingStartAmPm = new JComboBox<>(new String[]{"AM", "PM"});
        panel.add(meetingStartAmPm);

        panel.add(new JLabel("End Hour:"));
        meetingEndHour = new JTextField();
        panel.add(meetingEndHour);

        panel.add(new JLabel("End Minute:"));
        meetingEndMinute = new JTextField();
        panel.add(meetingEndMinute);

        panel.add(new JLabel("AM/PM:"));
        meetingEndAmPm = new JComboBox<>(new String[]{"AM", "PM"});
        panel.add(meetingEndAmPm);
    }

    // Deadline fields: Name and Date
    private JPanel createDeadlineForm() {
        JPanel deadlinePanel = new JPanel(new GridLayout(0, 2));

        deadlinePanel.add(new JLabel("Deadline Name:"));
        deadlineNameField = new JTextField();
        deadlinePanel.add(deadlineNameField);

        // Add fields for deadline date and time
        addDeadlineTimeFields(deadlinePanel);

        return deadlinePanel;
    }

    private void addDeadlineTimeFields(JPanel panel) {
        panel.add(new JLabel("Start Year:"));
        deadlineStartYear = new JTextField();
        panel.add(deadlineStartYear);

        panel.add(new JLabel("Start Month:"));
        deadlineStartMonth = new JTextField();
        panel.add(deadlineStartMonth);

        panel.add(new JLabel("Start Day:"));
        deadlineStartDay = new JTextField();
        panel.add(deadlineStartDay);

        panel.add(new JLabel("Start Hour:"));
        deadlineStartHour = new JTextField();
        panel.add(deadlineStartHour);

        panel.add(new JLabel("Start Minute:"));
        deadlineStartMinute = new JTextField();
        panel.add(deadlineStartMinute);

        panel.add(new JLabel("AM/PM:"));
        deadlineAmPm = new JComboBox<>(new String[]{"AM", "PM"});
        panel.add(deadlineAmPm);
    }

    // Switches between two forms based on event type
    private void updateForm(String eventType) {
        cardLayout.show(cardPanel, eventType);
    }

    // Adding the event based on the selected type
    private void handleAddEvent(String eventType) {
        try {
            if (eventType.equals("Meeting")) {
                LocalDateTime startTime = parseDateTime(meetingStartYear, meetingStartMonth, meetingStartDay,
                        meetingStartHour, meetingStartMinute, meetingStartAmPm);
                LocalDateTime endTime = parseDateTime(meetingStartYear, meetingStartMonth, meetingStartDay,
                        meetingEndHour, meetingEndMinute, meetingEndAmPm);
                String name = meetingNameField.getText();
                String location = meetingLocation.getText();

                // Create the new Meeting event
                Meeting newMeeting = new Meeting(name, startTime, endTime, location);
                eventListPanel.addEvent(newMeeting);
            } else if (eventType.equals("Deadline")) {
                LocalDateTime startTime = parseDateTime(deadlineStartYear, deadlineStartMonth, deadlineStartDay,
                        deadlineStartHour, deadlineStartMinute, deadlineAmPm);
                String name = deadlineNameField.getText();

                // Create the new Deadline event
                Deadline newDeadline = new Deadline(name, startTime);
                eventListPanel.addEvent(newDeadline);
            }
            // Close the modal after adding the event
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for time fields.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to parse date and time from input fields
    private LocalDateTime parseDateTime(JTextField yearField, JTextField monthField, JTextField dayField,
                                        JTextField hourField, JTextField minuteField, JComboBox<String> amPmField) {
        int year = Integer.parseInt(yearField.getText());
        int month = Integer.parseInt(monthField.getText());
        int day = Integer.parseInt(dayField.getText());
        int hour = Integer.parseInt(hourField.getText());
        int minute = Integer.parseInt(minuteField.getText());
        String amPm = (String) amPmField.getSelectedItem();

        // Convert to 24-hour format
        assert amPm != null;
        if (amPm.equals("PM") && hour != 12) {
            hour += 12;
        } else if (amPm.equals("AM") && hour == 12) {
            hour = 0;
        }

        // Create and return the LocalDateTime object
        return LocalDateTime.of(year, month, day, hour, minute);
    }
}