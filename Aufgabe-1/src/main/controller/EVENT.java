
package controller;

/**
 * The {@code Event} class represents an event with information about a specific customer and workload.
 * Each event contains a customer ID, a workload ID, a timestamp, and an event type (e.g., "start" or "stop").
 */
public class Event {
    private String customerId; // Kunden-ID
    private String workloadId; // Workload-ID
    private long timestamp; // Zeitstempel
    private String eventType; // Typ des Events (z.B. "start" oder "stop")

    /**
     * Returns the customer ID associated with this event.
     *
     * @return the customer ID as a {@code String}.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns the workload ID associated with this event.
     *
     * @return the workload ID as a {@code String}.
     */
    public String getWorkloadId() {
        return workloadId;
    }

    /**
     * Returns the timestamp of the event.
     *
     * @return the timestamp as a {@code long}.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the type of the event (e.g., "start" or "stop").
     *
     * @return the event type as a {@code String}.
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Returns a string representation of the {@code Event} object, containing the customer ID,
     * workload ID, timestamp, and event type.
     *
     * @return a {@code String} representation of the event object.
     */
    @Override
    public String toString() {
        return "Event{" +
                "customerId='" + customerId + '\'' +
                ", workloadId='" + workloadId + '\'' +
                ", timestamp=" + timestamp +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
