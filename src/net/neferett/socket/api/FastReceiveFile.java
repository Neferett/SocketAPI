package net.neferett.socket.api;

import java.net.Socket;

import net.neferett.socket.packet.Packet;
import net.neferett.socket.packet.PacketAction;

public class FastReceiveFile {

	private final Packet	p;
	private final String	path;

	public FastReceiveFile(final Socket s, final String path, final String filename) {
		this.path = path;
		this.p = new Packet(PacketAction.RECEIVE_FILE);
		this.p.setSocket(s);
		this.p.setFilename(filename);
	}

	public FastReceiveFile(final Socket s, final String path, final String filename, final Packet.Runnable r) {
		this(s, path, filename);
		this.p.setRunnable(r);
	}

	public void build() {
		this.p.setPath(this.path);
		this.p.sendPacket();
	}

	public Packet getPacket() {
		return this.p;
	}

}