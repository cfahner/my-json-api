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

package it.fahner.mywapi.myutil;

import it.fahner.mywapi.http.HttpRequest;
import it.fahner.mywapi.http.HttpResponse;
import it.fahner.mywapi.http.HttpResponseCache;

import java.util.HashMap;

/**
 * A class that links content names to instances of {@link HttpResponseCache}.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyWebCache {
	
	private HashMap<String, HttpResponseCache> caches;
	
	public MyWebCache() {
		this.caches = new HashMap<String, HttpResponseCache>();
	}
	
	/**
	 * Adds an entry to this caches.
	 * @since MyWebApi 1.0
	 * @param contentName The name to group the entry under
	 * @param response The data that needs to be cached
	 * @param expireAfter The time in milliseconds after which the caches entry must be removed
	 */
	public synchronized void add(String contentName, HttpResponse response, long expireAfter) {
		HttpResponseCache cache = this.caches.get(contentName);
		if (cache == null) { cache = new HttpResponseCache(); }
		cache.store(response, expireAfter);
		this.caches.put(contentName, cache);
	}
	
	/**
	 * Checks if a cached response is available for the content name and request specified.
	 * @since MyWebApi 1.0
	 * @param contentName The content name under which the cached entries are supposed to be stored
	 * @param request The request to check for if a response is available
	 * @return <code>true</code> when a cached response is available, <code>false</code> if not
	 */
	public synchronized boolean hasResponse(String contentName, HttpRequest request) {
		if (!caches.containsKey(contentName)) { return false; }
		return caches.get(contentName).hasResponseFor(request);
	}
	
	/**
	 * Returns the cached response (if available) for the specified request.
	 * @param contentName The content name under which this response is supposed to be stored
	 * @param request The request to check for if a cached response exists
	 * @return The {@link HttpResponse} if found, <code>null</code> if no cached response is available
	 */
	public synchronized HttpResponse getResponse(String contentName, HttpRequest request) {
		if (!caches.containsKey(contentName)) { return null; }
		return caches.get(contentName).getResponseFor(request);
	}
	
	/**
	 * Removes all cached content for a specified content name.
	 * @since MyWebApi 1.0
	 * @param contentName The content name to remove all caches entries for
	 */
	public synchronized void removeAll(String contentName) {
		this.caches.remove(contentName);
	}
	
	/**
	 * Removes all cached content.
	 * @since MyWebApi 1.0
	 */
	public synchronized void clear() {
		this.caches.clear();
	}
	
	/**
	 * Returns the total amount of responses currently cached.
	 * @since MyWebApi 1.0
	 * @return The amount of responses currently cached
	 */
	public synchronized int size() {
		int sum = 0;
		for (String key : caches.keySet()) {
			sum += caches.get(key).size();
		}
		return sum;
	}
	
}
