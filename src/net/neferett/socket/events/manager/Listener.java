package net.neferett.socket.events.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.neferett.socket.packet.Packet;

public class Listener {

	private final List<Events>						events		= new ArrayList<>();

	private final HashMap<Class<?>, EventListener>	listeners	= new HashMap<>();

	private final List<Packet>						packets		= new ArrayList<>();

	public void addListener(final EventListener e) {
		this.listeners.put(e.getClass(), e);
	}

	public void addListeners(final EventListener... e) {
		Arrays.asList(e).forEach(ev -> {
			this.addListener(ev);
		});
	}

	public void addPacket(final Packet p) {
		this.packets.add(p.addListener(this));
	}

	public void addPackets(final Packet... packets) {
		Arrays.asList(packets).forEach(p -> p.addListener(this));
		this.packets.addAll(Arrays.asList(packets));
	}

	public Events getEvent(final Class<? extends Events> g) {
		return this.events.stream().filter(ev -> ev.getEventname().equals(g.getSimpleName())).findFirst().orElse(null);
	}

	public List<Events> getEvents() {
		return this.events;
	}

	public HashMap<Class<?>, EventListener> getListeners() {
		return this.listeners;
	}

	public List<Packet> getPackets() {
		return this.packets;
	}

	@SuppressWarnings("unchecked")
	public void registerEvents(final Class<? extends Events>... ga) {
		final List<Class<? extends Events>> list = Arrays.asList(ga);

		list.forEach(g -> {
			Constructor<? extends Events> constructor;
			try {
				constructor = g.getDeclaredConstructor(String.class);
				this.events.add(constructor.newInstance(g.getSimpleName()));
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}

}
