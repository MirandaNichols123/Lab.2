package Lab2Work;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class EventPlanner
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        //initialize the eventListPanel
        EventListPanel eventListPanel = new EventListPanel();

        //add the EventListPanel to the JFrame
        frame.add(eventListPanel, BorderLayout.CENTER);

        addDefaultEvents(eventListPanel);

        frame.setVisible(true);
    }
    //get default information
    public static void addDefaultEvents(EventListPanel eventListPanel)
    {
        List<Event> defaultEvents = new ArrayList<>();

        //create some default events
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime min = now.minusMinutes(60);
        LocalDateTime tomorrow = now.plusDays(1);

        defaultEvents.add(new Deadline("Deadline 1", tomorrow));
        defaultEvents.add(new Meeting("Meeting 1", min, now, "Room 101"));

        //add events to the EventListPanel
        for (Event event : defaultEvents)
        {
            eventListPanel.addEvent(event);
        }
    }
}
