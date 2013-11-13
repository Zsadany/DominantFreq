package com.dominantfreq.display.model.handlers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public final class ButtonHandlers {
	public static final EventHandler<Event> buttonMouseEnteredHandler = new EventHandler<Event>() {
		@Override
		public void handle(Event e) {
			Button button = (Button) e.getSource();
			button.setOpacity(1.0);
		}
	};
	public static final EventHandler<Event> buttonMouseExitedHandler = new EventHandler<Event>() {
		@Override
		public void handle(Event e) {
			Button button = (Button) e.getSource();
			button.setOpacity(0.7);
		}
	};

}
