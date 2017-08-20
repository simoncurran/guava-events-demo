package com.simon.eventbus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;

@Component
public class Subscriber {

	private static Logger log = LoggerFactory.getLogger(Subscriber.class);

	protected static ExecutorService worker = Executors.newCachedThreadPool();

	@PostConstruct
	public void registerForEvents() {
		System.err.println("Subscriber registering for events.");
		EventRepository.getEventBus().register(this);
	}

	@PreDestroy
	public void unregisterForEvents() {
		System.err.println("Unsubscribing from events.");
		EventRepository.getEventBus().unregister(this);
		shutdown();
	}

	public static class EventHandler implements Runnable {

		final EventMessage message;

		public EventHandler(EventMessage message) {
			this.message = message;
		}

		public void run() {
			try {
				System.out.println("[start] Processing worker thread. " + message.msgcode + "/" + message.msg);
				TimeUnit.MILLISECONDS.sleep(message.msgcode); // do something
				System.out.println("[end] worker thread");
			} catch (Exception e) {
				log.error("Event failed", e);
			}
		}
	}

	@Subscribe
	public void handleEvent(final EventMessage eventMessage) {
		try {
			System.out.println("[start] dispatched event message to worker thread. " + eventMessage.msgcode + "/"
					+ eventMessage.msg);
			worker.execute(new EventHandler(eventMessage));
			System.out.println("[end] dispatched events.");
		} catch (Exception e) {
			log.error("event failed.", e);
		}
	}

	public void shutdown() {
		worker.shutdown();
		try {
			if (!worker.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
				worker.shutdownNow();
			}
			log.info("shutdown success");
		} catch (InterruptedException e) {
			log.error("shutdown failed", e);
		}
	}

}
