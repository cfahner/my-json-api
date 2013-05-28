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

package it.fahner.mywapi.http.types;

/**
 * A small immutable class that specifies properties of the content of an HTTP request or response.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public final class HttpContentType {
	
	/**
	 * The default charset used, if no specific charset was given.
	 * @since MyWebApi 1.0
	 */
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	/** Contains the actual content type (MIME). */
	private String contentType;
	
	/** Contains the name of the charset used. */
	private String charset;
	
	/**
	 * Creates a new {@link HttpContentType} instance using {@link #DEFAULT_CHARSET}.
	 * @param contentType The content type to specify
	 */
	public HttpContentType(String contentType) {
		this(contentType, DEFAULT_CHARSET);
	}
	
	/**
	 * Creates a new {@link HttpContentType} instance.
	 * @since MyWebApi 1.0
	 * @param contentType The content type to specify
	 * @param charset The character set used
	 */
	public HttpContentType(String contentType, String charset) {
		this.contentType = contentType;
		this.charset = charset;
	}
	
	/**
	 * Returns the content type string embedded in this {@link HttpContentType}.
	 * @since MyWebApi 1.0
	 * @return The content type string (a.k.a. MIME-Type)
	 */
	public String getContentType() {
		return contentType;
	}
	
	/**
	 * Returns the character set parameter of this content type. If not specified, this returns
	 * {@link #DEFAULT_CHARSET}.
	 * @since MyWebApi 1.0
	 * @return The character set
	 */
	public String getCharset() {
		return charset;
	}
	
	@Override
	public String toString() {
		return "Content-Type: " + contentType + "; charset=" + charset + ";";
	}
}
