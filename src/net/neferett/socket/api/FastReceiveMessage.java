package net.neferett.socket.api;

import java.io.IOException;

import net.neferett.socket.SockClient;
import net.neferett.socket.api.events.MsgEvents;
import net.neferett.socket.packet.Packet;
import net.neferett.socket.packet.PacketAction;

public class FastReceiveMessage {

	private final MsgEvents	e;
	private final Packet	p;
	private SockClient		sock;

	public FastReceiveMessage(final String addr, final int port) {
		this.e = new MsgEvents();
		try {
			this.sock = new SockClient(addr, port, this.e);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		this.p = new Packet(PacketAction.RECEIVE);
	}

	public String build() {
		this.sock.addPacket(this.p);
		this.sock.build();
		return this.e.getMessage();
	}

	public Packet getPacket() {
		return this.p;
	}

	public SockClient getSock() {
		return this.sock;
	}

}
