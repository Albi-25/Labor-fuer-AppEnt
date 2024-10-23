package controller;

import java.util.List;

/**
 * The {@code Dataset} class represents a collection of {@code Event} objects.
 * It provides methods to access and modify the list of events.
 */
public class Dataset {
    private List<Event> events; // Liste der Events

    /**
     * Returns the list of events in this dataset.
     *
     * @return a {@code List} of {@code Event} objects.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of events for this dataset.
     *
     * @param events a {@code List} of {@code Event} objects to set.
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Returns a string representation of the {@code Dataset} object, including the list of events.
     *
     * @return a {@code String} representation of the dataset object.
     */
    @Override
    public String toString() {
        return "Dataset{" +
                "events=" + events +
                '}';
    }
}