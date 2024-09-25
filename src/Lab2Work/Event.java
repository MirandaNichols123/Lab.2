package Lab2Work;

import java.time.LocalDateTime;

public abstract class Event implements Comparable<Event>
{
    protected String name; //name of event
    protected LocalDateTime dateTime; //start date and time of event

    public Event(String name, LocalDateTime dateTime)
    {
        this.name = name;
        this.dateTime = dateTime;
    }
    public abstract String getName(); //returns name of event

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime)
    {
        this.dateTime = dateTime;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int compareTo(Event e)
    {
        //compares dateTime of event with dateTime of incoming event
        return dateTime.compareTo(e.dateTime);
    }


}
