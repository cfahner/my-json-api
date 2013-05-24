package it.fahner.myjson;

/**
 * The main entry point for the MyJsonApi library. All requests, threads, caches and listeners
 * are managed here.
 * You must ALWAYS configure this class before using it. Example:
 * <code>
 * MyJsonApi.setConfigs(new MyJsonConfigs("http://hostname.com/api/location/"));
 * </code>
 * @since 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public final class MyJsonApi {
	
	/**
	 * Represents the current version of this library.
	 * @since 1.0
	 */
	public static final String VERSION = "1.0";
	
	/** Contains the initialization information. */
	private static MyJsonConfigs configs;
	
	/**
	 * Initializes the MyJsonApi. It is possible to pass new configurations later.
	 * @param configs
	 */
	public static void initialize(MyJsonConfigs configs) {
		MyJsonApi.configs = configs;
	}
	
	private static MyJsonConfigs getConfigs() {
		if (configs == null) { throw new RuntimeException("MyJsonApi not initialized!"); }
		return configs;
	}
	
	private static String getLocation() {
		return getConfigs().getLocation();
	}
	
}
