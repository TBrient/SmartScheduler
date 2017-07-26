package alex.tyler.smartscheduler;

import java.util.ArrayList;

/**
 * Created by Tyler on 7/25/2017.
 */

public class DataStorage {
    private ArrayList events = new ArrayList<Event>();

    public void addEvent(Event event) {
        events.add(event);
    }
}
