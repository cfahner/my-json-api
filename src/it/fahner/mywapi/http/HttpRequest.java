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
 * Represents a simplified HTTP request, that can be resolved.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class HttpRequest {
	
	/**
	 * Constant that represents the string encoding method used for all requests.
	 * @since MyWebApi 1.0
	 */
	public static final String CHARSET = "UTF-8";
	
	/**
	 * Creates a new (unresolved) HTTP request.
	 * @since MyWebApi 1.0
	 * @param url The URL that points to the remote resource to retrieve
	 */
	public HttpRequest(String url) {
		
	}
	
	/**
	 * Tries to resolve this remote resource.
	 * <p>Note: This is a synchronous operation (and blocks the current thread).</p>
	 * @return
	 */
	public HttpResponse resolve(int timeout) {
		return new HttpResponse();
	}
	
}
