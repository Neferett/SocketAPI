package net.neferett.socket.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
	protected Calendar cal;

	public void ConsoleMessage(final String message) {
		this.cal = Calendar.getInstance();
		final SimpleDateFormat date = new SimpleDateFormat("hh:mm:ss");
		System.out.println("[" + date.format(this.cal.getTime()) + "]  " + message);
	}
}
