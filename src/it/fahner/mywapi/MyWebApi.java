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
import it.fahner.mywapi.http.HttpResponse;
import it.fahner.mywapi.http.types.HttpParamList;
import it.fahner.mywapi.myutil.MyContentListenerCollection;
import it.fahner.mywapi.myutil.MyOpenRequestsTracker;
import it.fahner.mywapi.myutil.MyRequestListenerCollection;
import it.fahner.mywapi.myutil.MyWebCache;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The main entry point for the MyWebApi library. All requests, threads, caches and requestListeners
 * are managed here.
 * <p>Most basic setup:</p>
 * <p><code>
 * MyWebApi apiInstance = new MyWebApi("http://hostname.com/api/location/");
 * apiInstance.startListening(this);
 * </code></p>
 * <p>To start a new request, pass an implementation of {@link MyRequest} to
 * {@link #startRequest(MyRequest)}. It is recommended to extend {@link MyBaseRequest} for your own requests
 * instead of using the actual interface, since that will reduce the chances of your code breaking due to an
 * interface change.</p>
 * <p>When any request is resolved, all requestListeners are notified using their
 * {@link MyRequestListener#onRequestResolved(MyRequest)} method.</p>
 * <p>Caching is enabled by default, but can only work if {@link MyRequest#getCacheTime()} returns
 * a value greater than zero AND {@link MyRequest#getContentName()} returns a non-<code>null</code> value.</p>
 * <p>If a completed request causes some content to become invalid (as a result of the operation of that request),
 * use {@link #invalidateContent(String)} to invalidate the cache for that type of content. This will also notify
 * all other requestListeners through their {@link MyRequestListener#onContentChanged(String)} method.</p>
 * <p>Persisting the cache through multiple sessions requires you to serialize the returned value
 * of {@link #getCache()} and store it to disk. Later re-instantiate the cache and apply it to the API using
 * {@link #setCache(MyWebCache)}. A better API will be provided in a future release.</p>
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
	
	/** Flag indicating if this class should use a cache. Defaults to <code>true</code>. */
	private boolean useCache;
	
	/** Flag indicating if it's allowed to have duplicate requests in progress. */
	private boolean allowDuplicates;
	
	/** Stores all registered {@link MyRequestListener}s. */
	private MyRequestListenerCollection requestListeners;
	
	/** Stores all registered {@link MyContentListener}s. */
	private MyContentListenerCollection contentListeners;
	
	/** Keeps track of all currently opened requests. */
	private MyOpenRequestsTracker openRequests;
	
	/** Keeps track of all cached content. */
	private MyWebCache cache;
	
	/**
	 * Creates a new access point to a web-based API.
	 * <p>The cache will start enabled.</p>
	 * @since MyWebApi 1.0
	 * @param configs A configurations object for this API
	 */
	public MyWebApi(String baseUrl) {
		this.baseUrl = baseUrl;
		this.persistentUrlParams = new HttpParamList();
		this.timeoutMillis = DEFAULT_TIMEOUT;
		this.useCache = true;
		this.allowDuplicates = false;
		this.requestListeners = new MyRequestListenerCollection();
		this.contentListeners = new MyContentListenerCollection();
		this.openRequests = new MyOpenRequestsTracker();
		this.cache = new MyWebCache();
		MyLog.log("New instance for URL '" + baseUrl + "'");
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
			MyLog.error("Malformed full URL, reverting to base URL (" + myReq + ")");
			urlToUse += query;
		}
		MyLog.log("Created HttpRequest for URL '" + urlToUse + "' (" + myReq + ")");
		HttpRequest out = myReq.getRequestMethod() != null
				? new HttpRequest(urlToUse, myReq.getRequestMethod())
				: new HttpRequest(urlToUse);
		if (myReq.getBody() != null) { out.setBody(myReq.getBody()); }
		MyLog.log(".. with request body: " + out.getBody());
		return out;
	}
	
	/**
	 * Registers a callback to be invoked when any {@link MyRequest}s are resolved.
	 * <p>There is currently no way to directly remove a listener from the API other than unsetting all
	 * references to it and waiting for it to be garbage collected.</p>
	 * <p>This behavior should be added in a future release.</p>
	 * @since MyWebApi 1.0
	 * @param listener The callback to register
	 */
	public void startListening(MyRequestListener listener) {
		MyLog.log("A request listener was attached (" + listener + ")");
		requestListeners.put(listener);
	}
	
	/**
	 * Registers a callback to be invoked when any content under their associated content names
	 * is changed.
	 * @since MyWebApi 1.0
	 * @param listener The callback to register
	 */
	public void startContentListening(MyContentListener listener) {
		MyLog.log("A content listener was attached (" + listener + ")");
		contentListeners.put(listener);
	}
	
	/**
	 * Starts a single request. Invokes the callback of every listener when the request has finished.
	 * <p>If MyWebApi is still waiting for another request that points to the same resource (to the same URL
	 * with the same parameters), no new request will be started.</p>
	 * <p>If an response is stored in the cache and has not yet expired, that response is returned
	 * instead of sending a new request (unless the cache is disabled).</p>
	 * @since MyWebApi 1.0
	 * @param request An implementation of MyRequest that needs to be resolved
	 */
	public void startRequest(final MyRequest request) {
		MyLog.log("MyRequest started (" + request + ")");
		final HttpRequest http = convertToHttpRequest(request);
		if (!allowDuplicates && openRequests.isOpen(http)) {
			MyLog.log("MyRequest ignored, already open (" + request + ")");
			return;
		}
		
		// Check if the cache has a valid response ready now (if it is used)
		if (useCache && request.getContentName() != null && cache.hasResponse(request.getContentName(), http)) {
			MyLog.log("MyRequest completed from cache (" + request + ")");
			request.complete(cache.getResponse(request.getContentName(), http));
			requestListeners.invokeAll(request);
			return;
		}
		
		// Try to get the response (on a separate thread, so we don't block the main thread)
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				openRequests.storeRequest(http);
				try {
					HttpResponse response = http.getResponse(timeoutMillis);
					request.complete(response);
					long cacheTime = request.getCacheTime();
					if (useCache && cacheTime > 0) {
						MyLog.log("MyRequest response cached for " + cacheTime + " ms (" + request + ")");
						MyLog.log(".. cached using name '" + request.getContentName() + "'");
						cache.add(request.getContentName(), response, cacheTime);
					}
				} catch (HttpRequestTimeoutException e) {
					request.fail();
					MyLog.log("MyRequest timed out (" + request + ")");
				}
				openRequests.removeRequest(http);
				requestListeners.invokeAll(request);
			}
			
		}).start();
	}
	
	/**
	 * Invalidates all cached responses that are stored under the given content name.
	 * <p>Also notifies all content listeners that content with the given name has been invalidated.</p>
	 * @since MyWebApi 1.0
	 * @param contentName The content name to invalidate
	 */
	public void invalidateContent(String contentName) {
		cache.removeAll(contentName);
		contentListeners.invokeAll(contentName);
	}
	
	/**
	 * Returns the entire caching structure.
	 * @deprecated Temporary function that allows persisting of the cache, will be
	 *  replaced by better API in future versions
	 * @since MyWebApi 1.0
	 * @return The entire cache
	 */
	public MyWebCache getCache() {
		return this.cache;
	}
	
	/**
	 * Sets the cache of this web API.
	 * @deprecated Temporary function that allows setting the cache to a persisted version,
	 *  will be replaced by better API in future versions
	 * @since MyWebApi 1.0
	 * @param cache The cache to use
	 */
	public void setCache(MyWebCache cache) {
		this.cache = cache;
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
	 * Changes the enabled state of the cache.
	 * <p>Clears the cache when it is disabled.</p>
	 * @since MyWebApi 1.0
	 * @param enable <code>true</code> if you want to enable the cache, <code>false</code> to
	 *  disable
	 */
	public void setCacheEnabled(boolean enable) {
		this.useCache = enable;
		if (!useCache) { cache.clear(); }
	}
	
	/**
	 * Specifies the behavior of the API instance when it encounters a new request that is already in
	 * progress as another instance (based on the URL it points to and it's request body).
	 * <p>Is set to <code>false</code> by default.</p>
	 * @since MyWebApi 1.0
	 * @param allowDuplicates <code>true</code> to allow the same request to be "in flight" multiple times,
	 *   <code>false</code> to prevent duplicate requests from being sent at the same time
	 */
	public void setAllowDuplicates(boolean allowDuplicates) {
		this.allowDuplicates = allowDuplicates;
		MyLog.log("Request duplication has been " + (allowDuplicates ? "disallowed" : "allowed"));
	}
	
}
