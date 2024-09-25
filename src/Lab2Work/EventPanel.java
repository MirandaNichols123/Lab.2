package Lab2Work;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel
{
    private final Event event;
    private final JButton completeButton;

    public EventPanel(Event event)
    {
        this.event = event;
        setLayout(new BorderLayout());
        //used for arranging components
        setPreferredSize(new Dimension(300, 100));
        this.completeButton = new JButton("Complete");
        updatePanel();
    }
    private void updatePanel()
    {
        removeAll();

        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 5, 5)); //creates grid layout with 5px spacing
        contentPanel.setOpaque(false);//transparent background

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"); //date-time formatter
        contentPanel.add(new JLabel("Name: " + event.getName()));
        contentPanel.add(new JLabel("Date Time: " + event.getDateTime().format(formatter)));

        if (event instanceof Meeting meeting)
        {
            long minutes = meeting.getDuration().toMinutes();
            contentPanel.add(new JLabel("Duration: " + minutes + " minutes"));
            contentPanel.add(new JLabel("Location: " + meeting.getLocation()));
        }
        if (event instanceof Completable completable)
        {
            contentPanel.add(new JLabel("Complete: " + completable.isComplete()));
            // Add the complete button
            JPanel buttonPanel = createButtonPanel(completable); //use the refactored method to create the button panel
            add(buttonPanel, BorderLayout.EAST);
        }
        add(contentPanel, BorderLayout.WEST);
        updateUrgency();
        revalidate();
        repaint();
    }
    //create the panel for the "Complete" button if the event is not complete
    private JPanel createButtonPanel(Completable completable)
    {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        if (!completable.isComplete())//show the button if the event is incomplete
        {
            completeButton.setFont(new Font("Serif", Font.BOLD, 12)); //set the button font

            //adds lambda to handle completion when the button is clicked
            completeButton.addActionListener(_ -> {
                completable.complete();
                updatePanel();
            });

            buttonPanel.add(completeButton);//add button to the button panel
        }

        buttonPanel.setOpaque(false);//make the button panel transparent
        return buttonPanel;
    }
    public void updateUrgency()
    {
        //gets an advanced time to get the color green
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime month = now.plusMonths(1);
        //for deadline and meeting get the color background

        LocalDateTime eventDateTime = event.getDateTime();
        if (eventDateTime.isAfter(month))
        {
            setBackground(Color.GREEN); //distant event(more than one month)
        } else if (eventDateTime.isBefore(now))
        {
            setBackground(Color.RED); //overdue event
        } else
        {
            setBackground(Color.YELLOW); //imminent event
        }
    }
}
