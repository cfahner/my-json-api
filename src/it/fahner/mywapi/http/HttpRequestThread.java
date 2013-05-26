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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * This class sends a single HTTP request and allows you to register a callback to it
 * that notifies you when the request has finished. Uses UTF-8 encoding by default.
 * <p>
 * Usage:<br>
 * <code>
 * HttpRequestThread myRun = new HttpRequestThread("http://api/url")<br>
 * 		.setRequestBodyContent("content=value")<br>
 * 		.setTimeout(15000) // 15 seconds timeout<br>
 * 		.setListener(this); // supply a listener<br>
 * myRun.start(); // to do the actual I/O (asynchronous)<br>
 * </code>
 * </p>
 * <p>This class uses {@link HttpURLConnection} and {@link HttpsURLConnection} internally.</p>
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class HttpRequestThread extends Thread {
	
	/**
	 * The default encoding that is assumed when sending and receiving HTTP documents.
	 * @since MyWebApi 1.0
	 */
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * The default content type assumed for the requesting document.
	 * @since MyWebApi 1.0
	 */
	public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
	
	/**
	 * The default request method used.
	 * @since MyWebApi 1.0
	 */
	public static final HttpRequestMethod DEFAULT_REQUEST_METHOD = HttpRequestMethod.GET;
	
	/**
	 * The default timeout in milliseconds of an HTTP request.
	 * @since MyWebApi 1.0
	 */
	public static final int DEFAULT_TIMEOUT = 15000;
	
	/** The connection object that has been constructed. */
	HttpURLConnection connection;
	
	/** Stores the name of the encoding to use. */
	private String encoding;
	
	/** Stores the request's content type. */
	private String contentType;
	
	/** Stores the current request method. */
	private HttpRequestMethod requestMethod;
	
	/** Contains the contents to put in the request body. */
	private String requestBodyContent;
	
	/** Contains the timeout in milliseconds after which this request is aborted. */
	private int timeoutMillis;
	
	/** Stores the callbacks to invoke when this HttpRequestThread completes its work. */
	private HttpRequestListener listener;
	
	/**
	 * Creates a new HttpRequestThread, but does not yet start any networking I/O. Remember to set a
	 * {@link HttpRequestListener} if you want to be informed about the results. Not specifying a listener
	 * means this HttpRequestThread will just fire and forget.
	 * @since MyWebApi 1.0
	 * @param url The full URL to connect to, including the UrlParameters. Needs to be usable by the
	 *  {@link URL} class.
	 */
	public HttpRequestThread(String url) {
		encoding = DEFAULT_ENCODING;
		contentType = DEFAULT_CONTENT_TYPE;
		requestMethod = DEFAULT_REQUEST_METHOD;
		requestBodyContent = "";
		timeoutMillis = DEFAULT_TIMEOUT;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			HttpURLConnection.setFollowRedirects(true);
			// we will implement our own caching since this is not reliable on every platform
			connection.setUseCaches(false);
		} catch (Exception e) {
			// Probably a malformed url exception, no exceptions should occur here anyways so crash the application
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Changes the encoding of the request and response document, default is {@link #DEFAULT_ENCODING}.
	 * Modifies the Accept-Charset and Content-Type HTTP headers of the request.
	 * If the response contains content-encoding information, this will be used to decode the response.
	 * @since MyWebApi 1.0
	 * @param encoding The new encoding to set
	 * @return This {@link HttpRequestThread} for call chaining
	 */
	public HttpRequestThread setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}
	
	/**
	 * Changes the content type of the requesting document. Default is DEFAULT_CONTENT_TYPE.
	 * Modifies the Content-Type HTTP header of the request.
	 * @since MyWebApi 1.0
	 * @param responseContentType The new content type to use
	 * @return This {@link HttpRequestThread} for call chaining.
	 */
	public HttpRequestThread setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}
	
	/**
	 * Specifies the request method of the HTTP request. The default method is GET.
	 * @since MyWebApi 1.0
	 * @param requestMethod The new request method to change to
	 * @return This {@link HttpRequestThread} for call chaining
	 */
	public HttpRequestThread setRequestMethod(HttpRequestMethod requestMethod) {
		this.requestMethod = requestMethod;
		return this;
	}
	
	/**
	 * Changes the connection timeout for this HTTP request.
	 * <p>Note: some platforms do not allow changing the timeout.</p>
	 * @since MyWebApi 1.0
	 * @param timeoutMillis The time in milliseconds before timing out
	 * @return This {@link HttpRequestThread} for call chaining
	 */
	public HttpRequestThread setTimeout(int timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
		return this;
	}
	
	/**
	 * Sets the request body to the given value.
	 * @since MyWebApi 1.0
	 * @param requestBodyContent The new content to set the request body to
	 * @return This {@link HttpRequestThread} for call chaining
	 */
	public HttpRequestThread setRequestBody(String requestBodyContent) {
		this.requestBodyContent = requestBodyContent;
		return this;
	}
	
	/**
	 * Registers a set of callbacks to be invoked when this {@link HttpRequestThread} completes its tasks.
	 * @since MyWebApi 1.0
	 * @param listener The callbacks to register
	 * @return This {@link HttpRequestThread} for call chaining
	 */
	public HttpRequestThread setMyHttpWorkerListener(HttpRequestListener listener) {
		this.listener = listener;
		return this;
	}
	
	@Override
	public void run() {
		try {
			// Specify the proper values just before sending the request
			connection.setRequestMethod(requestMethod.name());
			connection.setRequestProperty("Accept-Charset", encoding);
			connection.setRequestProperty("Content-Type", contentType + "; charset="
					+ encoding.toLowerCase(Locale.ENGLISH));
			connection.setRequestProperty("Content-Length", "" + Integer.toString(requestBodyContent.getBytes().length));
			connection.setConnectTimeout(timeoutMillis);
			
			// Send request
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(requestBodyContent);
			writer.flush();
			writer.close();
			
			// Get response
			String responseEnctype = connection.getContentEncoding();
			if (responseEnctype == null) { responseEnctype = encoding; }
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), responseEnctype));
			String line;
			StringBuilder responseBody = connection.getContentLength() > 0
					? new StringBuilder(connection.getContentLength()) : new StringBuilder();
			while ((line = reader.readLine()) != null) {
				responseBody.append(line);
				responseBody.append('\r').append('\n');
			}
			reader.close();
			
			// Create a response object and call the callbacks only when a listener has been specified
			if (listener == null) { return; }
			HttpResponse response = new HttpResponse();
			if (connection.getContentType() != null) { response.contentType = connection.getContentType(); }
			else { response.contentType = "text/plain"; }
			response.encoding = responseEnctype;
			response.body = responseBody.toString();
			response.statusCode = HttpStatusCode.fromCode(connection.getResponseCode());
			response.expires = connection.getExpiration();
			listener.onWorkerFinished(response);
		} catch (IOException e) {
			// If a listener has been set, call it's cancelled method.
			if (listener != null) { listener.onWorkerCancelled(); }
		}
	}
	
	@Override
	public String toString() {
		return "{HttpRequestThread.Request => '" + connection.getURL().toString() + "'}";
	}
	
	/**
	 * The callbacks to be invoked when a {@link HttpRequestThread} has completed it's operations.
	 * @since MyWebApi 1.0
	 * @author C. Fahner <info@fahnerit.com>
	 */
	public static interface HttpRequestListener {
		
		/**
		 * Called when the work has successfully received a response. Note that this also includes
		 * erroneous responses (for example HTTP-404 responses).
		 * @since MyWebApi 1.0
		 * @param response The response to the request that was made
		 */
		public void onWorkerFinished(HttpResponse response);
		
		/**
		 * Called when the request was cancelled. A request can be cancelled for multiple reasons:
		 * <ul>
		 *   <li>I/O problem, an error occurred while trying to do networking I/O</li>
		 *   <li>connection timeout, the remote resource could not be reached within a reasonable amount of time</li>
		 *   <li>manually cancelled, the request was cancelled by the user manually</li>
		 * </ul>
		 * @since MyWebApi 1.0
		 */
		public void onWorkerCancelled();
	}

}
