package Lab2Work;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class AddEventModal extends JDialog
{
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

    public AddEventModal(JFrame parent, EventListPanel eventListPanel)
    {
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
        setSize(300,300);
    }
    //Meeting fields: Name, Start Time, End Time, and Location
    private JPanel createMeetingForm()
    {
        JPanel meetingPanel = new JPanel(new GridLayout(0, 2));

        meetingPanel.add(new JLabel("Meeting Name:"));
        meetingNameField = new JTextField();
        meetingPanel.add(meetingNameField);

        meetingPanel.add(new JLabel("Start Year:"));
        meetingStartYear = new JTextField();
        meetingPanel.add(meetingStartYear);

        meetingPanel.add(new JLabel("Start Month:"));
        meetingStartMonth = new JTextField();
        meetingPanel.add(meetingStartMonth);

        meetingPanel.add(new JLabel("Start Day:"));
        meetingStartDay = new JTextField();
        meetingPanel.add(meetingStartDay);

        meetingPanel.add(new JLabel("Start Hour:"));
        meetingStartHour = new JTextField();
        meetingPanel.add(meetingStartHour);

        meetingPanel.add(new JLabel("Start Minute:"));
        meetingStartMinute = new JTextField();
        meetingPanel.add(meetingStartMinute);

        meetingPanel.add(new JLabel("AM/PM:"));
        meetingStartAmPm = new JComboBox<>(new String[]{"AM", "PM"});
        meetingPanel.add(meetingStartAmPm);

        meetingPanel.add(new JLabel("End Hour:"));
        meetingEndHour = new JTextField();
        meetingPanel.add(meetingEndHour);

        meetingPanel.add(new JLabel("End Minute:"));
        meetingEndMinute = new JTextField();
        meetingPanel.add(meetingEndMinute);

        meetingPanel.add(new JLabel("AM/PM:"));
        meetingEndAmPm = new JComboBox<>(new String[]{"AM", "PM"});
        meetingPanel.add(meetingEndAmPm);

        meetingPanel.add(new JLabel("Location:"));
        meetingLocation = new JTextField();
        meetingPanel.add(meetingLocation);

        return meetingPanel;
    }
    //Deadline fields: Name and Date
    private JPanel createDeadlineForm()
    {
        JPanel deadlinePanel = new JPanel(new GridLayout(0, 2));

        deadlinePanel.add(new JLabel("Deadline Name:"));
        deadlineNameField = new JTextField();
        deadlinePanel.add(deadlineNameField);

        deadlinePanel.add(new JLabel("Start Year:"));
        deadlineStartYear = new JTextField();
        deadlinePanel.add(deadlineStartYear);

        deadlinePanel.add(new JLabel("Start Month:"));
        deadlineStartMonth = new JTextField();
        deadlinePanel.add(deadlineStartMonth);

        deadlinePanel.add(new JLabel("Start Day:"));
        deadlineStartDay = new JTextField();
        deadlinePanel.add(deadlineStartDay);

        deadlinePanel.add(new JLabel("Start Hour:"));
        deadlineStartHour = new JTextField();
        deadlinePanel.add(deadlineStartHour);

        deadlinePanel.add(new JLabel("Start Minute:"));
        deadlineStartMinute = new JTextField();
        deadlinePanel.add(deadlineStartMinute);

        deadlinePanel.add(new JLabel("AM/PM:"));
        deadlineAmPm = new JComboBox<>(new String[]{"AM", "PM"});
        deadlinePanel.add(deadlineAmPm);

        return deadlinePanel;
    }

    //Switches between two forms based on event type
    private void updateForm(String eventType)
    {
        cardLayout.show(cardPanel, eventType);
    }

    //adding the event based on the selected type
    private void handleAddEvent(String eventType)
    {
        try {
            if (eventType.equals("Meeting"))
            {
                //inputs for a startmeeting
                String name = meetingNameField.getText();
                int startYear = Integer.parseInt(meetingStartYear.getText());
                int startMonth = Integer.parseInt(meetingStartMonth.getText());
                int startDay = Integer.parseInt(meetingStartDay.getText());
                int startHour = Integer.parseInt(meetingStartHour.getText());
                int startMinute = Integer.parseInt(meetingStartMinute.getText());
                String startAmPm = (String) meetingStartAmPm.getSelectedItem();

                //24-hour format
                assert startAmPm != null;
                if (startAmPm.equals("PM") && startHour != 12)
                {
                    startHour += 12;
                } else if (startAmPm.equals("AM") && startHour == 12)
                {
                    startHour = 0;
                }
                //collect inputs for endMeeting
                int endHour = Integer.parseInt(meetingEndHour.getText());
                int endMinute = Integer.parseInt(meetingEndMinute.getText());
                String endAmPm = (String) meetingEndAmPm.getSelectedItem();

                //24-hour format
                assert endAmPm != null;
                if (endAmPm.equals("PM") && endHour != 12)
                {
                    endHour += 12;
                } else if (endAmPm.equals("AM") && endHour == 12)
                {
                    endHour = 0;
                }
                String location = meetingLocation.getText();

                //create the start and end time objects
                LocalDateTime startTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute);
                LocalDateTime endTime = LocalDateTime.of(startYear, startMonth, startDay, endHour, endMinute);

                //add the new Meeting event
                Meeting newMeeting = new Meeting(name, startTime, endTime, location);
                eventListPanel.addEvent(newMeeting);

            }
            else if (eventType.equals("Deadline"))
            {
                //collected inputs for Deadline
                String name = deadlineNameField.getText();
                int startYear = Integer.parseInt(deadlineStartYear.getText());
                int startMonth = Integer.parseInt(deadlineStartMonth.getText());
                int startDay = Integer.parseInt(deadlineStartDay.getText());
                int startHour = Integer.parseInt(deadlineStartHour.getText());
                int startMinute = Integer.parseInt(deadlineStartMinute.getText());
                String deadlineAmPmValue = (String) deadlineAmPm.getSelectedItem();

                //24-hour format
                assert deadlineAmPmValue != null;
                if (deadlineAmPmValue.equals("PM") && startHour != 12)
                {
                    startHour += 12;
                } else if (deadlineAmPmValue.equals("AM") && startHour == 12)
                {
                    startHour = 0;
                }

                //create the start time object
                LocalDateTime startTime = LocalDateTime.of(startYear, startMonth, startDay, startHour, startMinute);

                //add the new Deadline event
                Deadline newDeadline = new Deadline(name, startTime);
                eventListPanel.addEvent(newDeadline);
            }
            //Close the modal after adding the event
            dispose();
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for time fields.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
