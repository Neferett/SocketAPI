package net.neferett.socket.events.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventCaller {

	private final Class<? extends Annotation>	clazz;
	private final Listener						listener;

	public EventCaller(final Listener listener) {
		this.clazz = SocketEvent.class;
		this.listener = listener;
	}

	public Listener getListener() {
		return this.listener;
	}

	public List<Method> getMethodsByEvent(final Class<?> event) {

		final List<Method> methods = new ArrayList<>();

		this.listener.getListeners().forEach((c, l) -> {
			Arrays.asList(c.getDeclaredMethods()).forEach(m -> {
				if (m.isAnnotationPresent(this.clazz) && m.getAnnotation(SocketEvent.class).ignored() == false
						&& m.getParameterTypes().length == 1
						&& m.getParameterTypes()[0].getSimpleName().equals(event.getSimpleName())
						&& Arrays.asList(m.getParameterTypes()[0].getInterfaces()).contains(EventListener.class)
						&& this.listener.getListeners().containsKey(m.getDeclaringClass()))
					methods.add(m);
			});
		});
		return methods;
	}
}
