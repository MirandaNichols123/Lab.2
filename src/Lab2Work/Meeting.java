package Lab2Work;

import java.time.Duration;
import java.time.LocalDateTime;

public class Meeting extends Event implements Completable
{
    private LocalDateTime endDateTime;
    private String location;
    private boolean completed;

    public Meeting(String name, LocalDateTime startDateTime, LocalDateTime endDateTime, String location)
    {
        super(name, startDateTime);
        this.endDateTime = endDateTime;
        this.location = location;
        completed = false;
    }
    public void complete()
    {
        this.completed = true;
    }
    public boolean isComplete()
    {
        return this.completed;
    }
    public LocalDateTime getEndDateTime()
    {
        return endDateTime;
    }
    public Duration getDuration()
    {
        return Duration.between(dateTime, endDateTime);
    }
    public String getLocation()
    {
        return location;
    }
    public void setEndDateTime(LocalDateTime end)
    {
        this.endDateTime = end;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }
    public String getName()
    {
        return name;
    }
}
