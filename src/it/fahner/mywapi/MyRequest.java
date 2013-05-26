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
import it.fahner.mywapi.http.HttpRequestMethod;
import it.fahner.mywapi.http.HttpResponse;
import it.fahner.mywapi.http.HttpStatusCode;

/**
 * Defines an interface for implementing your own MyWebApi requests. A base implementation for this
 * interface is available as the abstract class MyBaseRequest.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public interface MyRequest {
	
	/**
	 * Allows the request to further specify the URL. This value gets directly appended to the base
	 * URL provided by your {@link MyWebConfigs}.
	 * <p>If it causes the full URL to become invalid, this value will be ignored. I.e. if it
	 * cannot be converted into an instance of {@link java.net.URL}.</p>
	 * <p><b>This CANNOT contain a URL query (<code>?param=value&x=y</code>).</b>
	 * To add URL parameters, use {@link #getUrlParameters()} to specify extra parameters.</p>
	 * @since MyWebApi 1.0
	 * @return The path to append to the base URL
	 */
	public String getPath();
	
	/**
	 * Returns the preferred HttpRequestMethod to use for this request. This value may be
	 * overridden based on the circumstances.
	 * @since MyWebApi 1.0
	 * @return The preferred HTTP request method
	 */
	public HttpRequestMethod getRequestMethod();
	
	/**
	 * Returns the time in milliseconds that the response of this request can be cached. This value is
	 * requested AFTER the response has been resolved. This allows you to check the contents of the
	 * response before allowing it to be cached.
	 * <p>You can use the {@link HttpStatusCode#isAlwaysCacheable()} method of the {@link HttpStatusCode}
	 * to check cacheability based on the HTTP status code.</p>
	 * <p>You can also choose to base your caching time based on the results of
	 * {@link HttpResponse#cacheableMillisLeft()}, or at least use that value as a reasonable default.</p>
	 * @since MyWebApi 1.0
	 * @return The time in milliseconds that the response can be cached
	 */
	public long getCacheTime();
	
	/**
	 * Returns a unique name to use when caching responses for this request. This name can then later
	 * be used to invalidate/clear the cache for a specific content name.
	 * @since MyWebApi 1.0
	 * @return The unique name that associates this request to a specific kind of content
	 */
	public String getContentName();
	
	/**
	 * Returns the parameters to use when writing the query part of the full URL.
	 * @since MyWebApi 1.0
	 * @return The list of parameters to append to the URL
	 */
	public HttpParamList getUrlParameters();
	
	/**
	 * Returns the list of parameters to put in the body of the request.
	 * @since MyWebApi 1.0
	 * @return The list of parameters to use as the request body
	 */
	public HttpParamList getBodyParameters();
	
	/**
	 * Called when this request has been resolved and has failed.
	 * @since MyWebApi 1.0
	 */
	public void fail();
	
	/**
	 * Called when this request has been resolved and has succeeded.
	 * @since MyWebApi 1.0
	 * @param response The remote resource that this request was trying to resolve
	 */
	public void complete(HttpResponse response);
	
}
