package alex.tyler.smartscheduler;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Tyler on 7/25/2017.
 */

public class HardEvent extends Event {
    String eventName;
    String description;
    String location;
    Date date;
    Time startTime;
    Time endTime;

    public HardEvent(String eventName, String description, String location, Date date, Time startTime, Time endTime){
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }

    @Override
    public Time getEndTime() {
        return endTime;
    }
}
