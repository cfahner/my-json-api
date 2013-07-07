package it.fahner.mywapi;

/**
 * Generic logging class for all MyWebApi instances.
 * <p>Logging is disabled by default, use {@link #enable()} and {@link #disable()} to toggle the log.</p>
 * <p>TODO: Use MyWebApi#setLogPrefix() to customize log output for multiple instances.</p>
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyLog {
	
	/** The flag that indicates if logging is enabled. */
	private static boolean enabled = false;
	
	/**
	 * Enable the log for all MyWebApi instances.
	 * @since MyWebApi 1.0
	 */
	public static void enable() {
		enabled = true;
	}
	
	/**
	 * Disable the log for all MyWebApi instances.
	 * @since MyWebApi 1.0
	 */
	public static void disable() {
		enabled = false;
	}
	
	/**
	 * Logs an event to system.out only if logging is enabled.
	 * @since MyWebApi 1.0
	 * @param event The event to log
	 */
	public static void log(Object event) {
		if (enabled) { System.out.println("MyWebApi - " + event); }
	}
	
	/**
	 * Logs an error to system.err only if logging is enabled.
	 * @since MyWebApi 1.0
	 * @param error The error to log
	 */
	public static void error(Object error) {
		if (enabled) { System.err.println(error); }
	}
	
}
