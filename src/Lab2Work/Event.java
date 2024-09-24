package Lab2Work;

import java.time.LocalDateTime;

public abstract class Event implements Comparable<Event>
{
    protected String name;
    protected LocalDateTime dateTime;

    public Event(String name, LocalDateTime dateTime)
    {
        this.name = name;
        this.dateTime = dateTime;
    }
    public abstract String getName();

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
        return dateTime.compareTo(e.dateTime);
    }


}
