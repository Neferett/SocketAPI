package net.neferett.socket.api;

import java.io.IOException;

import net.neferett.socket.SockClient;
import net.neferett.socket.api.events.MsgEvents;
import net.neferett.socket.packet.Packet;
import net.neferett.socket.packet.PacketAction;

public class FastSendMessage {

	private SockClient		client;
	private String			message;
	private final Packet	p;

	public FastSendMessage(final String addr, final int port) {
		this(addr, port, null);
	}

	public FastSendMessage(final String addr, final int port, final String message) {
		this.client = null;
		try {
			this.client = new SockClient(addr, port, new MsgEvents());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.message = message;
		this.p = new Packet(PacketAction.SEND);
		this.client.addPacket(this.p);
	}

	public void build() {
		this.p.setMessage(this.message);
		this.client.build();
	}

	public void close() {
		this.client.close();
	}

	public SockClient getClient() {
		return this.client;
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