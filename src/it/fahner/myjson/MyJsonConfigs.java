package it.fahner.myjson;

/**
 * A class that contains all possible configuration for the MyJsonApi.
 * @since 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyJsonConfigs {
	
	/** Stores the URL that points to the Api. */
	private String location;
	
	/**
	 * Creates a new Api configurations object.
	 * @param location The URL that points to the Api
	 */
	public MyJsonConfigs(String location) {
		this.location = location;
	}
	
	/**
	 * Returns the URL that points to the Api.
	 * @since 1.0
	 * @return The Api URL
	 */
	public String getLocation() {
		return location;
	}
	
}
