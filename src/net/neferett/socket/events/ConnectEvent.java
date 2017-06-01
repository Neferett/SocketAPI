package net.neferett.socket.events;

import java.net.Socket;

import net.neferett.socket.events.manager.EventListener;
import net.neferett.socket.events.manager.Events;
import net.neferett.socket.events.manager.Listener;

public class ConnectEvent extends Events implements EventListener {

	private Socket s;

	public ConnectEvent(final String eventname) {
		super(eventname);
	}

	public Socket getSocket() {
		return this.s;
	}

	@Override
	public void runEvent(final Socket s, final Listener l) {
		this.s = s;
		this.callEvent(l, this.getClass(), this);
	}

}
