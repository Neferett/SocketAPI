package net.neferett.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.neferett.socket.events.ConnectEvent;
import net.neferett.socket.events.manager.EventListener;
import net.neferett.socket.events.manager.Listener;
import net.neferett.socket.packet.Packet;
import net.neferett.socket.utils.Utils;

public class SockServer extends Listener {

	private final List<Packet>	packets	= new ArrayList<>();
	private final int			port;
	private final ServerSocket	server;
	private final Thread		t;
	private final Utils			utils	= new Utils();

	@SuppressWarnings("unchecked")
	public SockServer(final int port, final EventListener... e) throws IOException {
		this.port = port;
		this.server = new ServerSocket(this.port);
		this.registerEvents(ConnectEvent.class);
		this.utils.ConsoleMessage("Socket server created !");
		this.addListeners(e);
		this.t = this.buildThread();
		this.t.start();
	}

	@Override
	public void addPacket(final Packet p) {
		this.packets.add(p.addListener(this));
	}

	@Override
	public void addPackets(final Packet... packets) {
		Arrays.asList(packets).forEach(p -> p.addListener(this));
		this.packets.addAll(Arrays.asList(packets));
	}

	public Thread buildThread() {
		return new Thread(() -> {
			while (!this.server.isClosed())
				try {
					final Socket s = this.server.accept();
					this.getEvents().forEach(ev -> ev.runEvent(s, this));
					this.packets.forEach(p -> p.setSocket(s).sendPacket());
				} catch (final IOException e) {
					e.printStackTrace();
				}
		});
	}

	@SuppressWarnings("deprecation")
	public void close() {
		try {
			this.t.stop();
			this.server.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public int getPort() {
		return this.port;
	}

	public ServerSocket getServer() {
		return this.server;
	}
}
