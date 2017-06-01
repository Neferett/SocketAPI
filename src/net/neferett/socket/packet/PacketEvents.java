package net.neferett.socket.packet;

import net.neferett.socket.events.manager.CallEvent;

public abstract class PacketEvents extends CallEvent {
	private final String eventname;

	public PacketEvents(final String eventname) {
		this.eventname = eventname;
	}

	public String getEventname() {
		return this.eventname;
	}

	public abstract void runEvent(Packet p);
}
