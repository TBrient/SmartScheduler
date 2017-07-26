package alex.tyler.smartscheduler;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Tyler on 7/25/2017.
 */

public class FlexEvent extends Event {
    String eventName;
    String description;
    String location;
    Date date;
    Time startTime;
    Time endTime;

    private void setDateAndTimeBasedOnParams(FlexParameter[] params) {
        //TODO: Complete method
    }

    public FlexEvent(String eventName, String description, String location, FlexParameter[] parameters){
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        setDateAndTimeBasedOnParams(parameters);
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
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }
}
