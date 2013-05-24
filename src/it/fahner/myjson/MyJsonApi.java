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

package it.fahner.myjson;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	/** Stores all things that listen to this Api. */
	private static HashMap<String, ArrayList<MyJsonApiListener>> listeners;
	
	/**
	 * Initializes the MyJsonApi. It is possible to pass new configurations later.
	 * @since 1.0
	 * @param configs Configurations to use from now on
	 */
	public static void initialize(MyJsonConfigs configs) {
		MyJsonApi.configs = configs;
	}
	
	public static void startListening(MyJsonApiListener listener) {
		
	}
	
	public static void stopListening(MyJsonApiListener listener) {
		
	}
	
	private static MyJsonConfigs getConfigs() {
		if (configs == null) { throw new RuntimeException("MyJsonApi not initialized!"); }
		return configs;
	}
	
	private static String getLocation() {
		return getConfigs().getLocation();
	}
	
}
