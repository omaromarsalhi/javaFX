package pidev.javafx.tools;

import javafx.event.Event;
import javafx.event.EventType;

public class CustomMouseEvent<T> extends Event {
    public static final EventType<CustomMouseEvent<?>> CUSTOM_MOUSE_EVENT =
            new EventType<>(Event.ANY, "CUSTOM_MOUSE_EVENT");

    private final T eventData;

    public CustomMouseEvent(T eventData) {
        super(CUSTOM_MOUSE_EVENT);
        this.eventData = eventData;
    }

    public T getEventData() {
        return eventData;
    }
}

