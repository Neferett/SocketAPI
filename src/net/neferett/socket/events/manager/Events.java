package net.neferett.socket.events.manager;

import java.net.Socket;

public abstract class Events extends CallEvent {

	private final String eventname;

	public Events(final String eventname) {
		this.eventname = eventname;
	}

	public String getEventname() {
		return this.eventname;
	}

	public abstract void runEvent(Socket s, Listener l);

}
