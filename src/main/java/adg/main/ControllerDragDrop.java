package main.java.adg.main;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputEvent;

public class ControllerDragDrop extends Event {
    public static final EventType<DragEvent> DRAG_STARTED = new EventType<>(Event.ANY, "DRAG_STARTED");
    public static final EventType<DragEvent> DRAG_OVER = new EventType<>(Event.ANY, "DRAG_OVER");
    public static final EventType<DragEvent> DRAG_DROPPED = new EventType<>(Event.ANY, "DRAG_DROPPED");

    private final Object dragContent;

    public ControllerDragDrop(EventType<? extends Event> eventType, Object dragContent) {
        super(eventType);
        this.dragContent = dragContent;
    }

    public Object getDragContent() {
        return dragContent;
    }
}