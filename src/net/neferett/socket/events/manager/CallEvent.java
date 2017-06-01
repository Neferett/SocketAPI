package net.neferett.socket.events.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CallEvent {

	public void callEvent(final Listener l, final Class<?> clazz, final Object o) {
		final EventCaller ev = new EventCaller(l);

		final List<Method> m = ev.getMethodsByEvent(clazz);

		m.forEach(method -> {
			try {
				method.invoke(l.getListeners().get(method.getDeclaringClass()), o);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| SecurityException e) {
				e.printStackTrace();
			}
		});
	}

}
