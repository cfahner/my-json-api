package it.fahner.mywapi.http;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A basic cache that links {@link HttpRequest}s to {@link HttpResponse}s and automatically
 * ensures that cached content expires when it needs to.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public final class HttpResponseCache {
	
	/** Stores all cached objects. */
	private HashMap<String, HttpResponse> cache;
	
	/** Stores the times when content expires (determined by key). */
	private HashMap<String, Long> expireTimes;
	
	/**
	 * Creates a new empty HTTP response caching structure.
	 * @since MyWebApi 1.0
	 */
	public HttpResponseCache() {
		this.cache = new HashMap<String, HttpResponse>();
		this.expireTimes = new HashMap<String, Long>();
	}
	
	/**
	 * Caches an HTTP response for a specific amount of time.
	 * @since MyWebApi 1.0
	 * @param key The request used to retrieve the response
	 * @param response The response to store (this is actually cached)
	 * @param expireAfter The amount of time to store the response (in milliseconds)
	 */
	public synchronized void store(HttpRequest request, HttpResponse response, long expireAfter) {
		cache.put(request.getResourceIdentity(), response);
		expireTimes.put(request.getResourceIdentity(), System.currentTimeMillis() + expireAfter);
	}
	
	/**
	 * Returns the cached response for the specified request. Returns <code>null</code> if no cached
	 * response is available (anymore).
	 * @since MyWebApi 1.0
	 * @param request The request to get the cached response for
	 * @return The cached {@link HttpResponse} or <code>null</code> when no response has been cached (or has expired)
	 */
	public synchronized HttpResponse getResponseFor(HttpRequest request) {
		clean();
		return cache.get(request.getResourceIdentity());
	}
	
	/**
	 * Cleans all cached elements that have expired.
	 */
	private synchronized void clean() {
		if (expireTimes.size() <= 0) { return; } // prevent instantiation of objects below
		ArrayList<String> toClean = new ArrayList<String>();
		for (String key : expireTimes.keySet()) {
			if (expireTimes.get(key).longValue() < System.currentTimeMillis()) { toClean.add(key); }
		}
		for (String removeResId : toClean) {
			cache.remove(removeResId);
			expireTimes.remove(removeResId);
		}
	}
	
}
