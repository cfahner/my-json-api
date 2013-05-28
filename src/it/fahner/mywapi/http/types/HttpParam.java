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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * A simple parameter that has a name and a value (which are {@link java.lang.String}s).
 * @author C. Fahner <info@fahnerit.com>
 * @since MyWebApi 1.0
 */
public final class HttpParam {

	/** Contains the parameter name. */
	private String name;
	
	/** Contains the parameter value. */
	private String value;
	
	/**
	 * Create a new simple parameter.
	 * @since MyWebApi 1.0
	 * @param name The parameter name
	 * @param value The parameter value
	 */
	public HttpParam(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null) { return false; }
		if (!(arg0 instanceof HttpParam)) { return false; }
		HttpParam param = (HttpParam) arg0;
		return getName().equals(param.getName()) && getValue().equals(param.getValue());
	}
	
	@Override
	public String toString() {
		return "{HttpParam: '" + getName() + "=" + getValue() + "' }";
	}
	
	/**
	 * Returns the name of the parameter.
	 * @since MyWebApi 1.0
	 * @return The parameter name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the value of the parameter.
	 * @since MyWebApi 1.0
	 * @return The parameter value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns this parameter in URL encoded form.
	 * @since MyWebApi 1.0
	 * @return The encoded parameter as: <code>"name=value"</code>, URL escaped where necessary.
	 *  Returns <code>""</code> when the platform does not support UTF-8 encoding.
	 */
	public String toUrlEncodedString() {
		try { return URLEncoder.encode(getName(), "UTF-8") + "=" + URLEncoder.encode(getValue(), "UTF-8"); }
		catch (UnsupportedEncodingException e) { return ""; }
	}
	
}
