package pidev.javafx.tools;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class EventBus {
    private static final EventBus instance = new EventBus();

    private final Map<String, EventHandler<?>> handlers = new HashMap<>();

    public static EventBus getInstance() {
        return instance;
    }

    public <T extends Event> void subscribe(String eventType, EventHandler<T> handler) {
        handlers.put(eventType, handler);
    }

    public <T extends Event> void publish(String eventType, T data) {
        EventHandler<T> handler = (EventHandler<T>) handlers.get(eventType);
        if (handler != null) {
            handler.handle(data);
        }
    }

}

