package alex.tyler.smartscheduler;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Tyler on 7/25/2017.
 */

public abstract class Event {
    public abstract Date getDate();
    public abstract Time getStartTime();
    public abstract Time getEndTime();
    public abstract String getEventName();
    public abstract String getDescription();
    public abstract String getLocation();
}
