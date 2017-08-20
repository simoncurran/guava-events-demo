package com.simon.eventbus;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;

@Component
public class EventRepository {

	private static final EventBus eventBus = new EventBus("example-events");

	public static EventBus getEventBus() {
		return eventBus;
	}
}
