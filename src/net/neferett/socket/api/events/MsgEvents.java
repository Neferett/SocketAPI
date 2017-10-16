package net.neferett.socket.api.events;

import net.neferett.socket.events.manager.EventListener;
import net.neferett.socket.events.manager.SocketEvent;
import net.neferett.socket.packet.Packet;
import net.neferett.socket.packet.event.ReceiveMessageEvent;
import net.neferett.socket.packet.event.SendMessageEvent;

public class MsgEvents implements EventListener {

	private String message;

	public String getMessage() {
		return this.message;
	}

	@SocketEvent
	public void onReceive(final ReceiveMessageEvent e) {
		this.message = e.getMessage();
	}

	@SocketEvent
	public void onSend(final SendMessageEvent e) {
		final Packet p = e.getPacket();
		p.sendMessage();
	}

}
