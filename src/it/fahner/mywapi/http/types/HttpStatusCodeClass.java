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
 * Represents a classification of {@link HttpStatusCode}s.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public enum HttpStatusCodeClass {
	
	/**
	 * 1xx Informational HTTP response class.
	 * @since MyWebApi 1.0
	 */
	INFORMATIONAL(100),
	
	/**
	 * 2xx Success HTTP response class.
	 * @since MyWebApi 1.0
	 */
	SUCCESS(200),
	
	/**
	 * 3xx Redirection HTTP response class.
	 * @since MyWebApi 1.0
	 */
	REDIRECTION(300),
	
	/**
	 * 4xx Client Error HTTP response class.
	 * @since MyWebApi 1.0
	 */
	CLIENT_ERROR(400),
	
	/**
	 * 5xx Server Error HTTP response class.
	 * @since MyWebApi 1.0
	 */
	SERVER_ERROR(500);
	
	/** Stores the minimum http status code this HttpStatusCodeClass represents. */
	private int minCode;
	
	/**
	 * Create a new HttpStatusCodeClass.
	 * @param minCode The minimum HTTP status code for this class
	 */
	private HttpStatusCodeClass(int minCode) {
		this.minCode = minCode;
	}
	
	@Override
	public String toString() {
		return "{HttpStatusCodeClass: " + name() + "(" + getMinimumCode()
				+ "-" + getMaximumCode() + ") }";
	}
	
	/**
	 * Returns the lowest HTTP status code that can be in this {@link HttpStatusCodeClass}.
	 * @since MyWebApi 1.0
	 * @return The minimum HTTP status code for this response class
	 */
	public int getMinimumCode() {
		return this.minCode;
	}
	
	/**
	 * Returns the highest HTTP status code that can be in this {@link HttpStatusCodeClass}.
	 * @since MyWebApi 1.0
	 * @return The maximum HTTP status code for this response class
	 */
	public int getMaximumCode() {
		return this.minCode + 99;
	}
	
	/**
	 * Checks if a given HTTP status code belongs to this response class.
	 * @since MyWebApi 1.0
	 * @param statusCode The HTTP status code to check
	 * @return TRUE if the given status code belongs to this response class, FALSE if not
	 */
	public boolean isInThisClass(HttpStatusCode statusCode) {
		return statusCode.getCode() >= getMinimumCode() && statusCode.getCode() <= getMaximumCode();
	}
}
