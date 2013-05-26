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

package it.fahner.mywapi.http;

/**
 * Defines a simple HTTP response. An instance of this class is returned when an HTTP request
 * has been resolved by an {@link HttpRequestThread}. Only exposes the information meaningful to MyWebApi.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class HttpResponse {
	
	/**
	 * Contains the name of the remote resource that this response contains (a URL).
	 * @since MyWebApi 1.0
	 */
	public String requestUrl;
	
	/**
	 * The content type of the response.
	 * @since MyWebApi 1.0
	 */
	public String contentType;
	
	/**
	 * The content encoding of the response.
	 * @since MyWebApi 1.0
	 */
	public String encoding;
	
	/**
	 * Contains the expiration date of the resource. Equals zero if unknown.
	 * The value is the number of milliseconds since January 1, 1970 GMT.
	 * @since MyWebApi 1.0
	 */
	public long expires;
	
	/**
	 * The content body of the response.
	 * @since MyWebApi 1.0
	 */
	public String body;
	
	/**
	 * The HTTP status code of the response.
	 * @since MyWebApi 1.0
	 */
	public HttpStatusCode statusCode;
	
	/**
	 * Allows storage of a custom object in this {@link HttpResponse}.
	 * @since MyWebApi 1.0
	 */
	public Object customObject;
	
	/**
	 * Create a new HttpResponse object.
	 * @since MyWebApi 1.0
	 */
	public HttpResponse() {}
	
	@Override
	public String toString() {
		return "{HttpResponse <= URL('" + requestUrl + "') }";
	}
	
	/**
	 * Returns the amount of milliseconds left until this HTTP response expires. This is based on the
	 * <code>'Expires'</code> header returned by the server and the current {@link HttpStatusCode}.
	 * @see HttpStatusCode#isAlwaysCacheable() Cacheable HTTP status codes
	 * @since MyWebApi 1.0
	 * @return The amount of milliseconds left before this response expires based on server information,
	 *  always returns <code>0</code> if the current {@link HttpStatusCode} is not reliably cacheable.
	 */
	public long cacheableMillisLeft() {
		if (!statusCode.isAlwaysCacheable()) { return 0; }
		long timeLeft = expires - System.currentTimeMillis();
		return timeLeft > 0 ? timeLeft : 0;
	}
	
}