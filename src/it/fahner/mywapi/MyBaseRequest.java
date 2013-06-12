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

import it.fahner.mywapi.http.HttpResponse;
import it.fahner.mywapi.http.types.HttpParamList;
import it.fahner.mywapi.http.types.HttpRequestMethod;
import it.fahner.mywapi.http.types.HttpStatusCode;
import it.fahner.mywapi.http.types.HttpStatusCodeClass;

/**
 * A basic implementation of {@link MyRequest}. Does not make any assumptions. Most methods of the
 * interface are exposed to subclasses, except for {@link #fail()} and {@link #complete(HttpResponse)}.
 * Use {@link #onResolved()} in combination with {@link #hasSucceeded()} and {@link #getResponse()} to respond
 * to the results of the request.
 * <p>Implements {@link #getContentName()} using the name of the runtime class.</p>
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public abstract class MyBaseRequest implements MyRequest {
	
	/** Flag indicating that the request has been resolved at all (ok or fail). */
	private boolean resolved = false;
	
	/** Contains the response if the request was completed. */
	private HttpResponse response;
	
	@Override
	public String getPath() {
		return null;
	}

	@Override
	public HttpRequestMethod getRequestMethod() {
		return null;
	}

	@Override
	public long getCacheTime() {
		return 0;
	}

	@Override
	public String getContentName() {
		return this.getClass().getName();
	}

	@Override
	public HttpParamList getUrlParameters() {
		return null;
	}

	@Override
	public String getBody() {
		return null;
	}

	@Override
	public final void fail() {
		this.resolved = true;
		onResolved();
	}

	@Override
	public final void complete(HttpResponse response) {
		this.resolved = true;
		this.response = response;
		onResolved();
	}
	
	/**
	 * Returns the raw HTTP response of this request, if it has succeeded.
	 * @since MyWebApi 1.0
	 * @return The {#link HttpResponse} if the request has succeeded, <code>null</code> if the request
	 *  has resolved or not yet been resolved
	 */
	protected HttpResponse getResponse() {
		return this.response;
	}
	
	/**
	 * Checks if this request has been resolved but has resolved (due to a timeout or I/O error).
	 * @since MyWebApi 1.0
	 * @return <code>true</code> if this request has resolved, <code>false</code> otherwise
	 */
	public final boolean hasFailed() {
		if (!this.resolved) { return false; }
		return !hasSucceeded();
	}
	
	/**
	 * Checks if this request has been resolved and has succeeded, indicating that a good response is available.
	 * <p>Note: Also requires the HTTP response code to be in the 2xx SUCCESS range.</p>
	 * @see HttpStatusCode HTTP status codes
	 * @see HttpStatusCodeClass HTTP status code classes / ranges
	 * @since MyWebApi 1.0
	 * @return <code>true</code> if the request has successfully received a response, <code>false</code> otherwise
	 */
	public final boolean hasSucceeded() {
		if (!this.resolved) { return false; }
		if (this.response == null) { return false; }
		if (!this.response.getStatus().getResponseClass().equals(HttpStatusCodeClass.SUCCESS)) { return false; }
		return true;
	}
	
	/**
	 * Checks if this request has been resolved.
	 * @since MyWebApi 1.0
	 * @return <code>true</code> if the request has either resolved or succeeded, <code>false</code> if nothing has
	 *  happened yet
	 */
	public final boolean isResolved() {
		return this.resolved;
	}
	
	/**
	 * The method to override if you want to respond to the events caused by this request being resolved.
	 * You can use {@link #hasSucceeded()} and {@link #getResponse()} to check the results and work from there.
	 * @since MyWebApi 1.0
	 */
	protected abstract void onResolved();

}
