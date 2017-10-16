package net.neferett.socket.packet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import net.neferett.socket.events.manager.Listener;

public class Packet {

	public interface Runnable {
		public void run(int i);
	}

	private final PacketAction	action;
	private File				f;
	private String				filename;
	private BufferedReader		in;
	private Listener			l;
	private String				message;
	private String				path;
	private PrintWriter			pw;
	private Runnable			r;
	private Socket				s;

	private Object				sendMessage;

	public Packet(final PacketAction action) {
		this.action = action;
	}

	public Packet addListener(final Listener l) {
		this.l = l;
		return this;
	}

	public void buildMessage() {
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

	public void buildPrintMessage() {
		try {
			this.pw = new PrintWriter(this.s.getOutputStream());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void file_hl(final InputStream in, final OutputStream out) throws IOException {

		final byte buf[] = new byte[1024];

		int n;
		int a = 0;

		while ((n = in.read(buf)) != -1) {
			a += n;
			out.write(buf, 0, n);
			this.r.run(a);
		}
		out.close();
	}

	public PacketAction getAction() {
		return this.action;
	}

	public String getFilename() {
		return this.filename;
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

	public void receptFile() {
		System.out.println("Receiving file in " + this.path);

		try {

			new File(this.path).mkdir();
			new File(this.path + "\\" + this.filename).createNewFile();

			this.file_hl(this.s.getInputStream(), new FileOutputStream(this.path + "\\" + this.filename));
		} catch (final IOException e) {
			e.printStackTrace();
		}

		System.out.println("Received file in " + this.path);
	}

	public void sendFile() {
		try {
			this.file_hl(new FileInputStream(this.f), this.s.getOutputStream());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage() {
		this.pw.println(this.sendMessage);
		this.pw.flush();
	}

	public void sendPacket() {
		if (this.action == PacketAction.RECEIVE)
			this.buildMessage();
		else if (this.action == PacketAction.RECEIVE_FILE) {
			this.receptFile();
			return;
		} else if (this.action == PacketAction.SEND_FILE) {
			this.sendFile();
			return;
		}
		this.buildPrintMessage();
		this.buildPacket().runEvent(this);
	}

	public void setFile(final File f) {
		this.f = f;
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}

	public void setMessage(final Object message) {
		this.sendMessage = message;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public void setRunnable(final Runnable r) {
		this.r = r;
	}

	public Packet setSocket(final Socket s) {
		this.s = s;
		return this;
	}

}
