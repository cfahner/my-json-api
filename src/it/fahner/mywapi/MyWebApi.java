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

import it.fahner.mywapi.http.HttpRequestThread;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The main entry point for the MyWebApi library. All requests, threads, caches and listeners
 * are managed here.
 * <p>Most basic instantiation:</p>
 * <p><code>
 * MyWebApi apiInstance = new MyWebApi(new MyWebConfigs("http://hostname.com/api/location/"));
 * </code></p>
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyWebApi {
	
	/**
	 * Represents the current version of this library.
	 * @since MyWebApi 1.0
	 */
	public static final String VERSION = "1.0";
	
	/** Contains the initialization information. */
	private MyWebConfigs configs;
	
	/** Stores all things that listen to this API. */
	private ArrayList<WeakReference<MyWebApiListener>> listeners;
	
	/** Stores the unique HttpRequest identifiers for all currently open requests. */
	private HashSet<String> requests;
	
	/**
	 * Creates a new access point to a web-based API.
	 */
	public MyWebApi(MyWebConfigs configs) {
		this.configs = configs;
		listeners = new ArrayList<WeakReference<MyWebApiListener>>();
	}
	
	public HttpRequestThread convertToHttpRequest(MyRequest myReq) {
		String urlToUse = getBaseURL();
		try {
			URL url = new URL(getBaseURL() + myReq.getPath());
		} catch (MalformedURLException e) {
			System.err.println("MyWebApi: malformed URL, reverting to base URL");
		}
		HttpRequestThread out = new HttpRequestThread(urlToUse);
		
		return out;
	}
	
	/**
	 * Registers a listener to MyWebApi. The listener will (from now on) receive updates
	 * from MyWebApi when MyRequests sent by that listener are resolved.
	 * @since MyWebApi 1.0
	 * @param listener The listener to register
	 */
	public void startListening(MyWebApiListener listener) {
		listeners.add(new WeakReference<MyWebApiListener>(listener));
	}
	
	/**
	 * Starts a single request. Invokes the callbacks for all listeners when the request
	 * has been resolved.
	 * <p>If MyWebApi is still waiting for another request that points to the same resource (to the same URL
	 * with the same parameters), no new request will be started.</p>
	 * <p>If an response is stored in the cache and has not yet expired, that response is returned
	 * instead of sending a new request.</p>
	 * @param request An implementation of MyRequest that needs to be resolved
	 */
	public void startRequest(MyRequest request) {
		
	}
	
	/**
	 * Shorthand getter for getting the Api location.
	 * @return The URL that points to the API
	 */
	private String getBaseURL() {
		return configs.getBaseUrl();
	}
	
}
