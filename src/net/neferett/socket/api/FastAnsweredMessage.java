package net.neferett.socket.api;

import java.net.Socket;

import net.neferett.socket.packet.Packet;
import net.neferett.socket.packet.PacketAction;

public class FastAnsweredMessage {

	private final Packet p;

	public FastAnsweredMessage(final Socket s) {
		this.p = new Packet(PacketAction.RECEIVE);
		this.p.setSocket(s);
	}

	public String build() {
		this.p.buildMessage();
		return this.p.getMessage();
	}

	public Packet getPacket() {
		return this.p;
	}

}
