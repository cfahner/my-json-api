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

import it.fahner.mywapi.http.HttpRequest;
import it.fahner.mywapi.http.HttpRequestTimeoutException;
import it.fahner.mywapi.http.types.HttpParamList;
import it.fahner.mywapi.myutil.MyOpenRequestsTracker;
import it.fahner.mywapi.myutil.MyWebApiListenerCollection;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The main entry point for the MyWebApi library. All requests, threads, caches and listeners
 * are managed here.
 * <p>Most basic instantiation:</p>
 * <p><code>
 * MyWebApi apiInstance = new MyWebApi("http://hostname.com/api/location/");
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
	
	/**
	 * The request timeout in milliseconds used when no timeout has been set specifically.
	 * <p>Some platforms may enforce their own timeout value.</p>
	 * @since MyWebApi 1.0
	 */
	public static final int DEFAULT_TIMEOUT = 15000;
	
	/** Contains the base URL of this MyWebApi. */
	private String baseUrl;
	
	/** Contains all URL parameters that need to be included in every request. */
	private HttpParamList persistentUrlParams;
	
	/** Contains the time in milliseconds before a single request is cancelled. */
	private int timeoutMillis;
	
	/** Flag indicating if this class should use a cache. */
	private boolean useCache;
	
	/** Stores all registered {@link MyWebApiListener}s. */
	private MyWebApiListenerCollection listeners;
	
	/** Keeps track of all currently opened requests. */
	private MyOpenRequestsTracker openRequests;
	
	/**
	 * Creates a new access point to a web-based API.
	 * @since MyWebApi 1.0
	 * @param configs A configurations object for this API
	 */
	public MyWebApi(String baseUrl) {
		this.baseUrl = baseUrl;
		this.persistentUrlParams = new HttpParamList();
		this.timeoutMillis = DEFAULT_TIMEOUT;
		this.listeners = new MyWebApiListenerCollection();
		this.openRequests = new MyOpenRequestsTracker();
	}
	
	/**
	 * Converts a {@link MyRequest} into an {@link HttpRequest} that we can get the response for.
	 * @debug This function is made public for debugging purposes only, do not use in production!
	 * @since MyWebApi 1.0
	 * @param myReq The request to convert
	 * @return The HttpRequest that represents the resource the MyRequest wants to retrieve
	 */
	public HttpRequest convertToHttpRequest(MyRequest myReq) {
		String urlToUse = baseUrl;
		String query = myReq.getUrlParameters().merge(persistentUrlParams).toUrlQuery();
		try {
			// Try to use the base URL + the path specified by the request + the query
			urlToUse = myReq.getPath() != null
					? new URL(urlToUse + myReq.getPath() + query).toExternalForm()
					: new URL(urlToUse + query).toExternalForm();
		} catch (MalformedURLException e) {
			// If base+path+query is malformed, just use base+query, which (if malformed) will fail
			// automatically when it is passed to the HttpRequest
			System.err.println("MyWebApi: malformed full URL, reverting to base URL");
			urlToUse += query;
		}
		HttpRequest out = myReq.getRequestMethod() != null
				? new HttpRequest(urlToUse, myReq.getRequestMethod())
				: new HttpRequest(urlToUse);
		if (myReq.getBody() != null) { out.setBody(myReq.getBody()); }
		return out;
	}
	
	/**
	 * Registers a callback to be invoked when any {@link MyRequest}s are resolved.
	 * @since MyWebApi 1.0
	 * @param listener The callback to register
	 */
	public void startListening(MyWebApiListener listener) {
		listeners.put(listener);
	}
	
	/**
	 * Starts a single request. Invokes the callback of every listener when the request has finished.
	 * <p>If MyWebApi is still waiting for another request that points to the same resource (to the same URL
	 * with the same parameters), no new request will be started.</p>
	 * <p>If an response is stored in the cache and has not yet expired, that response is returned
	 * instead of sending a new request (unless the cache is disabled).</p>
	 * <p>The encoding used for the body of the request sent is {@link HttpRequestThread#CHARSET}.</p>
	 * <p>The content type of the request is {@link HttpRequestThread#DEFAULT_CONTENT_TYPE}.</p>
	 * @since MyWebApi 1.0
	 * @param request An implementation of MyRequest that needs to be resolved
	 */
	public void startRequest(final MyRequest request) {
		final HttpRequest http = convertToHttpRequest(request);
		if (openRequests.isOpen(http)) { return; }
		// TODO: if (cache.has && useCache) { .. invoke immediately }
		openRequests.storeRequest(http);
		// Try to get the response (on a separate thread, so we don't block the main thread)
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try { request.complete(http.getResponse(timeoutMillis)); }
				catch (HttpRequestTimeoutException e) { request.fail(); }
				openRequests.removeRequest(http);
				listeners.invokeAll(request);
			}
			
		}).start();
	}
	
	/**
	 * Sets a URL parameter that is included with every request.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to set
	 * @param value The value of the parameter to set
	 */
	public void setPersistentUrlParameter(String name, String value) {
		this.persistentUrlParams.set(name, value);
	}
	
	/**
	 * Removes a parameter from the list of persistent URL parameters. This parameter
	 * will no longer be included with every request.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to remove
	 */
	public void removePersistentUrlParameter(String name) {
		this.persistentUrlParams.remove(name);
	}
	
	/**
	 * Sets the amount of time to wait before a request is cancelled.
	 * @since MyWebApi 1.0
	 * @param milliseconds Amount of time to wait in milliseconds
	 */
	public void setTimeout(int milliseconds) {
		this.timeoutMillis = milliseconds;
	}
	
	/**
	 * Changes the enabled state of the cache
	 * @since MyWebApi 1.0
	 * @param enable <code>true</code> if you want to enable the cache, <code>false</code> to
	 *  disable
	 */
	public void setCacheEnabled(boolean enable) {
		this.useCache = enable;
	}
	
}
