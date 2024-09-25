package Lab2Work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        //initialize and add control panel
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);

        //initialize and add display panel
        displayPanel = createDisplayPanel();
        add(displayPanel, BorderLayout.CENTER);
    }
    //create the control panel
    private JPanel createControlPanel()
    {
        JPanel controlPanel = new JPanel(new FlowLayout());

        //sorting dropdown
        sortDropDown = new JComboBox<>(new String[]{"Sort by Name", "Sort by Date", "Reverse Name", "Reverse Date"});
        sortDropDown.addActionListener(_ -> sortEvents());  // Lambda to trigger sorting

        //filtering checkboxes
        filterCompleted = new JCheckBox("Hide Completed");
        filterCompleted.setFont(new Font("Serif", Font.PLAIN, 12));
        //anonymous class
        filterCompleted.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                updateEventDisplay();
            }
        });

        filterDeadlines = new JCheckBox("Hide Deadlines");
        filterDeadlines.setFont(new Font("Serif", Font.PLAIN, 12));
        filterDeadlines.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                updateEventDisplay();
            }
        });

        filterMeetings = new JCheckBox("Hide Meetings");
        filterMeetings.setFont(new Font("Serif", Font.PLAIN, 12));
        filterMeetings.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                updateEventDisplay();
            }
        });

        //add button
        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(_ -> showAddEventModal());

        //add all components to controlPanel
        controlPanel.add(sortDropDown);
        controlPanel.add(filterCompleted);
        controlPanel.add(filterDeadlines);
        controlPanel.add(filterMeetings);
        controlPanel.add(addEventButton);

        return controlPanel;
    }
    //create the event display panel
    private JPanel createDisplayPanel()
    {
        //stack event panels vertically
        return new JPanel(new GridLayout(0, 1));
    }
    //add an event to the list
    public void addEvent(Event event)
    {
        events.add(event);
        updateEventDisplay();
    }
    //sort events based on the dropdown selection
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
            updateEventDisplay();
        }
    }
    //update the event display
    private void updateEventDisplay()
    {
        displayPanel.removeAll(); //clear the previous event panels

        for (Event event : events)
        {
            //check filters
            if ((filterCompleted.isSelected() && event instanceof Completable && ((Completable) event).isComplete())
                    || (filterDeadlines.isSelected() && event instanceof Deadline)
                    || (filterMeetings.isSelected() && event instanceof Meeting)) {
                continue;
            }

            EventPanel eventPanel = new EventPanel(event);
            displayPanel.add(eventPanel);

            //add a border between event panels
            eventPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK));
        }

        revalidate(); //refresh the display
        repaint(); //redraw the panel
    }
    //open the modal for adding a new event
    private void showAddEventModal()
    {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddEventModal modal = new AddEventModal(frame, this);
        modal.setVisible(true);
    }
}

