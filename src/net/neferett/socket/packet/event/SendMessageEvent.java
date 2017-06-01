package net.neferett.socket.packet.event;

import net.neferett.socket.events.manager.EventListener;
import net.neferett.socket.events.manager.Listener;
import net.neferett.socket.packet.Packet;
import net.neferett.socket.packet.PacketEvents;
import net.neferett.socket.utils.Utils;

public class SendMessageEvent extends PacketEvents implements EventListener {

	private Packet		p;

	private final Utils	utils	= new Utils();

	public SendMessageEvent(final String eventname) {
		super(eventname);
	}

	public Listener getListener() {
		return this.p.getListener();
	}

	public Packet getPacket() {
		return this.p;
	}

	public Utils getUtils() {
		return this.utils;
	}

	@Override
	public void runEvent(final Packet p) {
		this.p = p;
		this.callEvent(p.getListener(), this.getClass(), this);
	}

}
