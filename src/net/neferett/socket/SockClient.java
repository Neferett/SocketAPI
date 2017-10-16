package net.neferett.socket;

import java.io.IOException;
import java.net.Socket;

import net.neferett.socket.events.ConnectEvent;
import net.neferett.socket.events.manager.EventListener;
import net.neferett.socket.events.manager.Listener;

public class SockClient extends Listener {

	private final String	addr;
	private final int		port;
	private final Socket	sock;

	@SuppressWarnings("unchecked")
	public SockClient(final String addr, final int port, final EventListener... ev) throws IOException {
		this.addr = addr;
		this.port = port;
		this.sock = new Socket(this.addr, this.port);
		if (ev != null)
			this.addListeners(ev);
		this.registerEvents(ConnectEvent.class);
	}

	public void build() {
		this.getEvents().forEach(ev -> ev.runEvent(this.sock, this));
		this.getPackets().forEach(p -> p.setSocket(this.sock).sendPacket());
	}

	public Thread buildThread() {
		return new Thread(() -> {
			this.getEvents().forEach(ev -> ev.runEvent(this.sock, this));
			this.getPackets().forEach(p -> p.setSocket(this.sock).sendPacket());
			this.close();
		});
	}

	public void close() {
		try {
			this.sock.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public String getAddr() {
		return this.addr;
	}

	public int getPort() {
		return this.port;
	}

	public Socket getSock() {
		return this.sock;
	}
}
