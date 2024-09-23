package Lab2Work;

import java.time.LocalDateTime;

public class Deadline extends Event implements Completable
{
    private boolean complete;

    public Deadline(String name, java.time.LocalDateTime deadline)
    {
        super(name, deadline);
        this.complete = false;
    }
    public void complete()
    {
        this.complete = true;
    }
    public boolean isComplete()
    {
        return this.complete;
    }

    @Override
    public String getName() {
        return name;
    }
}
