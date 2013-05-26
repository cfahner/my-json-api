/*
 Copyright 2013 FahnerIT

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package it.fahner.mywapi;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main entry point for the MyWebApi library. All requests, threads, caches and listeners
 * are managed here.
 * <p>You must ALWAYS configure MyWebApi before using it. For example:</p>
 * <p><code>
 * MyWebApi.initialize(new MyWebConfigs("http://hostname.com/api/location/"));
 * </code></p>
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public final class MyWebApi {
	
	/**
	 * Represents the current version of this library.
	 * @since MyWebApi 1.0
	 */
	public static final String VERSION = "1.0";
	
	/** Contains the initialization information. */
	private static MyWebConfigs configs;
	
	/** Stores all things that listen to this Api. */
	private static HashMap<String, ArrayList<WeakReference<MyWebApiListener>>> listeners;
	
	/**
	 * Initializes the MyWebApi. You can only call this method once. Use the setter methods provided by
	 * MyWebApi to change some settings later.
	 * @since MyWebApi 1.0
	 * @param configs Configurations to use from now on
	 */
	public static void initialize(MyWebConfigs configs) {
		MyWebApi.configs = configs;
		listeners = new HashMap<String, ArrayList<WeakReference<MyWebApiListener>>>();
	}
	
	/**
	 * Registers a listener to MyWebApi. The listener will (from now on) receive updates
	 * from MyWebApi when MyRequests sent by that listener are resolved.
	 * @since MyWebApi 1.0
	 * @param listener The listener to register
	 */
	public static void startListening(MyWebApiListener listener) {
		forceInit();
		String cls = listener.getClass().getName();
		ArrayList<WeakReference<MyWebApiListener>> activeListeners = listeners.get(cls);
		if (activeListeners == null) { activeListeners = new ArrayList<WeakReference<MyWebApiListener>>(); }
		activeListeners.add(new WeakReference<MyWebApiListener>(listener));
		listeners.put(cls, activeListeners);
	}
	
	/**
	 * Shorthand getter for getting the Api location.
	 * @return The URL that points to the API
	 */
	private static String getBaseURL() {
		forceInit();
		return configs.getLocation();
	}
	
	/** Forces an initialization to have happened. */
	private static void forceInit() {
		if (configs == null) { throw new RuntimeException("MyWebApi not initialized!"); }
	}
	
}
