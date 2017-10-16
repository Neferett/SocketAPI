package net.neferett.socket.packet;

import net.neferett.socket.packet.event.ReceiveFileEvent;
import net.neferett.socket.packet.event.ReceiveMessageEvent;
import net.neferett.socket.packet.event.SendFileEvent;
import net.neferett.socket.packet.event.SendMessageEvent;

public enum PacketAction {

	RECEIVE(ReceiveMessageEvent.class),
	RECEIVE_FILE(ReceiveFileEvent.class),
	SEND(SendMessageEvent.class),
	SEND_FILE(SendFileEvent.class);

	private Class<? extends PacketEvents> event;

	PacketAction(final Class<? extends PacketEvents> g) {
		this.event = g;
	}

	public Class<? extends PacketEvents> getEvent() {
		return this.event;
	}

}
