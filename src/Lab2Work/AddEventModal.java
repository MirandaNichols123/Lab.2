package Lab2Work;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class AddEventModal extends JDialog
{
    private final EventListPanel eventListPanel;

    //meeting input fields
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

    //deadline input fields
    private JTextField deadlineNameField;
    private JTextField deadlineStartDay;
    private JTextField deadlineStartMonth;
    private JTextField deadlineStartYear;
    private JTextField deadlineStartHour;
    private JTextField deadlineStartMinute;
    private JComboBox<String> deadlineAmPm;

    private final JPanel cardPanel; //panel to hold different input forms
    private final CardLayout cardLayout; //layout manager for switching cards

    public AddEventModal(JFrame parent, EventListPanel eventListPanel)
    {
        super(parent, "Add Event", true);
        this.eventListPanel = eventListPanel;

        setLayout(new BorderLayout());

        //create the dropdown to select event type
        String[] eventTypes = {"Meeting", "Deadline"};
        JComboBox<String> eventTypeDropdown = new JComboBox<>(eventTypes);
        eventTypeDropdown.addActionListener(_ -> updateForm((String) eventTypeDropdown.getSelectedItem()));

        //add dropdown to the top
        add(eventTypeDropdown, BorderLayout.NORTH);

        //create the panel to switch between forms
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //create separate forms for "Meeting" and "Deadline"
        JPanel meetingForm = createMeetingForm();
        JPanel deadlineForm = createDeadlineForm();

        //add forms to the card panel
        cardPanel.add(meetingForm, "Meeting");
        cardPanel.add(deadlineForm, "Deadline");

        add(cardPanel, BorderLayout.CENTER);

        //show Meeting form by default
        updateForm("Meeting");

        //add a button to submit the form
        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(_ -> handleAddEvent((String) Objects.requireNonNull(eventTypeDropdown.getSelectedItem())));
        add(addButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setSize(300, 300);
    }
    //meeting fields
    private JPanel createMeetingForm()
    {
        JPanel meetingPanel = new JPanel(new GridLayout(0, 2));

        meetingPanel.add(new JLabel("Meeting Name:"));
        meetingNameField = new JTextField();
        meetingPanel.add(meetingNameField);

        //add fields for meeting start time and end time
        addMeetingTimeFields(meetingPanel);

        meetingPanel.add(new JLabel("Location:"));
        meetingLocation = new JTextField();
        meetingPanel.add(meetingLocation);

        return meetingPanel;
    }
    //adding meeting time feilds
    private void addMeetingTimeFields(JPanel panel)
    {
        panel.add(new JLabel("Start Year(xxxx):"));
        meetingStartYear = new JTextField();
        panel.add(meetingStartYear);

        panel.add(new JLabel("Start Month(xx):"));
        meetingStartMonth = new JTextField();
        panel.add(meetingStartMonth);

        panel.add(new JLabel("Start Day(xx):"));
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
    //deadline fields
    private JPanel createDeadlineForm()
    {
        JPanel deadlinePanel = new JPanel(new GridLayout(0, 2));

        deadlinePanel.add(new JLabel("Deadline Name:"));
        deadlineNameField = new JTextField();
        deadlinePanel.add(deadlineNameField);

        //add fields for deadline date and time
        addDeadlineTimeFields(deadlinePanel);

        return deadlinePanel;
    }
    //adding time fields for deadline
    private void addDeadlineTimeFields(JPanel panel)
    {
        panel.add(new JLabel("Start Year(xxxx):"));
        deadlineStartYear = new JTextField();
        panel.add(deadlineStartYear);

        panel.add(new JLabel("Start Month(xx):"));
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

    //switches between two forms based on event type
    private void updateForm(String eventType)
    {
        cardLayout.show(cardPanel, eventType);
    }

    //adding the event based on the selected type
    private void handleAddEvent(String eventType)
    {
            if (eventType.equals("Meeting"))
            {
                LocalDateTime startTime = parseDateTime(meetingStartYear, meetingStartMonth, meetingStartDay,
                        meetingStartHour, meetingStartMinute, meetingStartAmPm);
                LocalDateTime endTime = parseDateTime(meetingStartYear, meetingStartMonth, meetingStartDay,
                        meetingEndHour, meetingEndMinute, meetingEndAmPm);
                String name = meetingNameField.getText();
                String location = meetingLocation.getText();

                //create the new Meeting event
                Meeting newMeeting = new Meeting(name, startTime, endTime, location);
                eventListPanel.addEvent(newMeeting);
            }
            else if (eventType.equals("Deadline"))
            {
                LocalDateTime startTime = parseDateTime(deadlineStartYear, deadlineStartMonth, deadlineStartDay,
                        deadlineStartHour, deadlineStartMinute, deadlineAmPm);
                String name = deadlineNameField.getText();

                //create the new Deadline event
                Deadline newDeadline = new Deadline(name, startTime);
                eventListPanel.addEvent(newDeadline);
            }
            //close the modal after adding the event
            dispose();
    }

    //helper method to parse date and time from input fields
    private LocalDateTime parseDateTime(JTextField yearField, JTextField monthField, JTextField dayField,
                                        JTextField hourField, JTextField minuteField, JComboBox<String> amPmField)
    {
        int year = Integer.parseInt(yearField.getText());
        int month = Integer.parseInt(monthField.getText());
        //check if the month is valid (between 1 and 12)
        if (month < 1 || month > 12)
        {
            //show an error dialog if the month is invalid
            JOptionPane.showMessageDialog(this, "Month must be between 1 and 12.", "Invalid Month", JOptionPane.ERROR_MESSAGE);
            throw new NumberFormatException("Invalid month entered");
        }

        int day = Integer.parseInt(dayField.getText());
        if (day < 1 || day > 31)
        {
            JOptionPane.showMessageDialog(this, "Day must be between 1 and 31.", "Invalid Day", JOptionPane.ERROR_MESSAGE);
            throw new NumberFormatException("Invalid day entered");
        }

        int hour = Integer.parseInt(hourField.getText());
        if (hour < 1 || hour > 12)
        {
            JOptionPane.showMessageDialog(this, "Hour must be between 1 and 12.", "Invalid Hour", JOptionPane.ERROR_MESSAGE);
            throw new NumberFormatException("Invalid hour entered");
        }
        int minute = Integer.parseInt(minuteField.getText());
        if (minute < 0 || minute > 59)
        {
            // Show an error dialog if the day is invalid
            JOptionPane.showMessageDialog(this, "Minute must be between 0 and 59.", "Invalid minute", JOptionPane.ERROR_MESSAGE);
            throw new NumberFormatException("Invalid minute entered");
        }
        String amPm = (String) amPmField.getSelectedItem();

        //convert to 24-hour format
        assert amPm != null;
        if (amPm.equals("PM") && hour != 12)
        {
            hour += 12;
        } else if (amPm.equals("AM") && hour == 12)
        {
            hour = 0;
        }

        //create and return the LocalDateTime object
        return LocalDateTime.of(year, month, day, hour, minute);
    }
}