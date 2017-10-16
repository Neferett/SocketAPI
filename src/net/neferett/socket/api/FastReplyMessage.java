package net.neferett.socket.api;

import java.net.Socket;

import net.neferett.socket.packet.Packet;
import net.neferett.socket.packet.PacketAction;

public class FastReplyMessage {

	private String			message;
	private final Packet	p;

	public FastReplyMessage(final Socket s) {
		this(s, null);
	}

	public FastReplyMessage(final Socket s, final String message) {
		this.message = message;
		this.p = new Packet(PacketAction.SEND);
		this.p.setSocket(s);
	}

	public void build() {
		this.p.setMessage(this.message);
		this.p.buildPrintMessage();
		this.p.sendMessage();
	}

	public String getMessage() {
		return this.message;
	}

	public Packet getPacket() {
		return this.p;
	}

	public void setMessage(final String msg) {
		this.message = msg;
	}

}