package com.simon.eventbus;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();	
	
    public Greeting greeting(String name) {
    	
    	System.out.println("Hello there " + name);
    	
    	EventRepository.getEventBus().post("This is dead event");
    	EventRepository.getEventBus().post(new EventMessage(5000, "This message from hoge"));
    	EventRepository.getEventBus().post(new EventMessage(1000, "This message from fuga"));    	
    	
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }	
}
