package net.neferett.socket.api;

import java.io.File;
import java.net.Socket;

import net.neferett.socket.packet.Packet;
import net.neferett.socket.packet.PacketAction;

public class FastSendFile {

	private final File		f;
	private final Packet	p;

	public FastSendFile(final Socket s) {
		this(s, null);
	}

	public FastSendFile(final Socket s, final File f) {
		this.f = f;
		this.p = new Packet(PacketAction.SEND_FILE);
		this.p.setSocket(s);
	}

	public void build() {
		this.p.setFile(this.f);
		this.p.sendPacket();
	}

	public Packet getPacket() {
		return this.p;
	}

}