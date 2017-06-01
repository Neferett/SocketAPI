package net.neferett.socket.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import net.neferett.socket.events.manager.Listener;

public class Packet {

	private final PacketAction	action;
	private BufferedReader		in;
	private Listener			l;
	private String				message;
	private PrintWriter			pw;
	private Socket				s;

	private Object				sendMessage;

	public Packet(final PacketAction action) {
		this.action = action;
	}

	public Packet addListener(final Listener l) {
		this.l = l;
		return this;
	}

	private void buildMessage() {
		try {
			this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		try {
			this.message = this.in.readLine();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private PacketEvents buildPacket() {
		final Class<? extends PacketEvents> g = this.action.getEvent();
		Constructor<? extends PacketEvents> constructor;
		try {
			constructor = g.getDeclaredConstructor(String.class);
			return constructor.newInstance(g.getSimpleName());
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void buildPrintMessage() {
		try {
			this.pw = new PrintWriter(this.s.getOutputStream());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public PacketAction getAction() {
		return this.action;
	}

	public Listener getListener() {
		return this.l;
	}

	public String getMessage() {
		return this.message;
	}

	public Socket getS() {
		return this.s;
	}

	public void sendMessage() {
		this.pw.println(this.sendMessage);
		this.pw.flush();
	}

	public void sendPacket() {
		if (this.action == PacketAction.RECEIVE) this.buildMessage();
		this.buildPrintMessage();
		this.buildPacket().runEvent(this);
	}

	public void setMessage(final Object message) {
		this.sendMessage = message;
	}

	public Packet setSocket(final Socket s) {
		this.s = s;
		return this;
	}

}
