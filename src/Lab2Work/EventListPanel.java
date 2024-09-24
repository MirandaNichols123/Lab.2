package Lab2Work;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EventListPanel extends JPanel
{
    private final ArrayList<Event> events;
    private final JPanel displayPanel;
    private JComboBox<String> sortDropDown;
    private JCheckBox filterCompleted;
    private JCheckBox filterDeadlines;
    private JCheckBox filterMeetings;

    EventListPanel()
    {
        events = new ArrayList<>();
        setLayout(new BorderLayout());

        // Initialize and add control panel (containing sorting, filtering, and "Add Event" button)
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);

        // Initialize and add display panel (where EventPanels will be shown)
        displayPanel = createDisplayPanel();
        add(displayPanel, BorderLayout.CENTER);
    }

    // Method to create the control panel
    private JPanel createControlPanel()
    {
        JPanel controlPanel = new JPanel(new FlowLayout());

        // Sorting dropdown
        sortDropDown = new JComboBox<>(new String[]{"Sort by Name", "Sort by Date", "Reverse Name", "Reverse Date"});
        sortDropDown.addActionListener(_ -> sortEvents());  // Lambda to trigger sorting

        // Filtering checkboxes
        filterCompleted = new JCheckBox("Hide Completed");
        filterCompleted.setFont(new Font("Serif", Font.PLAIN, 12));
        filterCompleted.addActionListener(_ -> updateEventDisplay());

        filterDeadlines = new JCheckBox("Hide Deadlines");
        filterDeadlines.setFont(new Font("Serif", Font.PLAIN, 12));
        filterDeadlines.addActionListener(_ -> updateEventDisplay());

        filterMeetings = new JCheckBox("Hide Meetings");
        filterMeetings.setFont(new Font("Serif", Font.PLAIN, 12));
        filterMeetings.addActionListener(_ -> updateEventDisplay());

        // "Add Event" button
        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(_ -> showAddEventModal());

        // Add all components to controlPanel
        controlPanel.add(sortDropDown);
        controlPanel.add(filterCompleted);
        controlPanel.add(filterDeadlines);
        controlPanel.add(filterMeetings);
        controlPanel.add(addEventButton);

        return controlPanel;
    }

    // Method to create the event display panel
    private JPanel createDisplayPanel()
    {
        // Stack event panels vertically
        return new JPanel(new GridLayout(0, 1));
    }

    // Method to add an event to the list
    public void addEvent(Event event)
    {
        events.add(event);
        updateEventDisplay();  // Refresh the display to include the new event
    }
    // Method to sort events based on the dropdown selection
    private void sortEvents()
    {
        String selectedOption = (String) sortDropDown.getSelectedItem();
        if (selectedOption != null)
        {
            switch (selectedOption)
            {
                case "Sort by Name":
                    events.sort(Comparator.comparing(Event::getName));
                    break;
                case "Sort by Date":
                    events.sort(Comparator.comparing(Event::getDateTime));
                    break;
                case "Reverse Name":
                    events.sort(Comparator.comparing(Event::getName).reversed());
                    break;
                case "Reverse Date":
                    events.sort(Comparator.comparing(Event::getDateTime).reversed());
                    break;
            }
            updateEventDisplay();  // Re-display the events after sorting
        }
    }

    // Method to update the event display (with sorting and filters applied)
    private void updateEventDisplay()
    {
        displayPanel.removeAll();  // Clear the previous event panels

        for (Event event : events)
        {
            // Check filters: hide events based on selected filters
            if ((filterCompleted.isSelected() && event instanceof Completable && ((Completable) event).isComplete())
                    || (filterDeadlines.isSelected() && event instanceof Deadline)
                    || (filterMeetings.isSelected() && event instanceof Meeting)) {
                continue;  // Skip this event
            }

            EventPanel eventPanel = new EventPanel(event);  // Create the EventPanel
            displayPanel.add(eventPanel);  // Add the EventPanel to the display panel

            // Add a border between event panels
            eventPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK));
        }

        revalidate();  // Refresh the display
        repaint();  // Redraw the panel
    }

    // Method to open the modal for adding a new event
    private void showAddEventModal()
    {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddEventModal modal = new AddEventModal(frame, this);
        modal.setVisible(true);
    }
}

