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

import it.fahner.mywapi.http.HttpParamList;

/**
 * A configuration object for MyWebApi. Contains at least the URL that points to the Api.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyWebConfigs {
	
	/**
	 * The default request timeout in milliseconds to use when none are specified.
	 * @since MyWebApi 1.0
	 */
	public static final int DEFAULT_TIMEOUT = 15000;
	
	/** Stores the URL that points to the Api. */
	private String baseUrl;
	
	/** Stores the time to wait before a request should timeout (in milliseconds). */
	private int requestTimeout;
	
	/** Stores the parameters that always will be appended to the URL. */
	private HttpParamList persistentUrlParams;
	
	/** Flag indication wether or not to use caches. */
	private boolean useCache;
	
	/**
	 * Creates a new Api configurations object.
	 * @since MyWebApi 1.0
	 * @param baseUrl The base URL that points to the Api
	 */
	public MyWebConfigs(String baseUrl) {
		this.baseUrl = baseUrl;
		this.requestTimeout = DEFAULT_TIMEOUT;
		this.persistentUrlParams = new HttpParamList();
		this.useCache = false;
	}
	
	/**
	 * Returns the URL that points to the Api.
	 * @since MyWebApi 1.0
	 * @return The Api URL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * Returns the currently set request timeout in milliseconds. This is set to
	 * DEFAULT_TIMEOUT by default.
	 * @since MyWebApi 1.0
	 * @return The timeout in milliseconds
	 */
	public int getTimeout() {
		return this.requestTimeout;
	}
	
	/**
	 * Specifies the request timeout in milliseconds.
	 * @since MyWebApi 1.0
	 * @param requestTimeout The new timeout in milliseconds
	 * @return This MyWebConfigs for call chaining
	 */
	public MyWebConfigs setTimeout(int requestTimeout) {
		this.requestTimeout = requestTimeout;
		return this;
	}
	
	/**
	 * Returns the HttpParamList that contains all persistent URL parameters.
	 * @since MyWebApi 1.0
	 * @return This MyWebConfigs for call chaining
	 */
	public HttpParamList getPersistentUrlParams() {
		return this.persistentUrlParams;
	}
	
	/**
	 * Sets the parameter list that contains all persistent URL parameters.
	 * @since MyWebApi 1.0
	 * @param params The new parameter list to use
	 * @return This MyWebConfigs for call chaining
	 */
	public MyWebConfigs setPersistentUrlParams(HttpParamList params) {
		this.persistentUrlParams = params;
		return this;
	}
	
	/**
	 * Returns the flag that indicates cache usage.
	 * @since MyWebApi 1.0
	 * @return TRUE when caches can be used, FALSE otherwise
	 */
	public boolean useCache() {
		return this.useCache;
	}
	
	/**
	 * Sets the flag indication wether or not to use caches.
	 * @since MyWebApi 1.0
	 * @param useCache TRUE to use caches, FALSE otherwise
	 * @return This MyWebConfigs for call chaining
	 */
	public MyWebConfigs useCache(boolean useCache) {
		this.useCache = useCache;
		return this;
	}
	
}
