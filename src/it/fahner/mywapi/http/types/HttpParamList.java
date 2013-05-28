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

import java.util.HashMap;

/**
 * Represents a collection of {@link HttpParam}s.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public final class HttpParamList {
	
	/** Contains all {@link HttpParam}s, mapped by their parameter name for fast access. */
	private HashMap<String, HttpParam> params;
	
	/**
	 * Creates a new, empty, collection of {@link HttpParam}s.
	 * @since MyWebApi 1.0
	 */
	public HttpParamList() {
		params = new HashMap<String, HttpParam>();
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder("{HttpParamList: [\n");
		HttpParam[] all = all();
		for (int i = 0; i < all.length; i += 1) {
			out.append('\t').append(all[i]);
			if (i != all.length - 1) { out.append("\n"); }
		}
		return out.append("\n] }").toString();
	}
	
	/**
	 * Merges another list with this list and returns the results as a new {@link HttpParamList}.
	 * @since MyWebApi 1.0
	 * @param other The other list to merge with this one
	 * @return The resulting list
	 */
	public HttpParamList merge(HttpParamList other) {
		HttpParamList out = new HttpParamList();
		HttpParam[] ownParams = all();
		HttpParam[] otherParams = other.all();
		for (int i = 0; i < ownParams.length; i += 1) { out.set(ownParams[i]); }
		for (int i = 0; i < otherParams.length; i += 1) { out.set(otherParams[i]); }
		return out;
	}
	
	/**
	 * Checks if this parameter list contains a parameter with the specified name.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to search for
	 * @return <code>true</code> if the parameter exists in this list
	 */
	public boolean has(String name) {
		return params.containsKey(name);
	}
	
	/**
	 * Checks if this parameter list contains the specified parameter. This method uses
	 * {@link HttpParam#equals(Object)}.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to search for
	 * @return <code>true</code> if the parameter exists in this list and has the same value
	 */
	public boolean has(HttpParam param) {
		HttpParam tmp = params.get(param.getName());
		if (tmp == null) { return false; }
		return tmp.equals(param);
	}
	
	/**
	 * Returns the parameter contained in this parameter list, if it exists.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to find
	 * @return The parameter associated to the given name, <code>null</code> when
	 *  no parameter with that name was found
	 */
	public HttpParam get(String name) {
		return params.get(name);
	}
	
	/**
	 * Sets a parameter in this collection. Overwrites any existing values.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to set
	 * @param value The value of the parameter to set
	 * @return This {@link HttpParamList} for call chaining
	 */
	public HttpParamList set(String name, String value) {
		params.put(name, new HttpParam(name, value));
		return this;
	}
	
	/**
	 * Sets a parameter in this list. Overwrites any existing values.
	 * @since MyWebApi 1.0
	 * @param param The parameter to set
	 * @return This {@link HttpParamList} for call chaining
	 */
	public HttpParamList set(HttpParam param) {
		params.put(param.getName(), param);
		return this;
	}
	
	/**
	 * Removes a parameter from this list.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to remove
	 * @return The parameter that was removed
	 */
	public HttpParam remove(String name) {
		return params.remove(name);
	}
	
	/**
	 * Returns an array of all parameters currently in this list.
	 * @since MyWebApi 1.0
	 * @return All parameters contained within this list
	 */
	public HttpParam[] all() {
		return (HttpParam[]) params.values().toArray(new HttpParam[params.size()]);
	}
	
	/**
	 * Returns this parameter list as a URL query.
	 * @since MyWebApi 1.0
	 * @return This parameter list as <code>"?one=valueOne&two=valueTwo"</code>.
	 *  <p>Returns <code>""</code> when UTF-8 encoding is not supported on this platform.</p>
	 */
	public String toUrlQuery() {
		return "?" + toUrlEncodedString();
	}
	
	/**
	 * Returns this parameter list as an URL encoded string.
	 * @since MyWebApi 1.0
	 * @return This parameter list as <code>"one=valOne&two=valTwo"</code>.
	 *  <p>Returns <code>""</code> when UTF-8 encoding is not supported on this platform.</p>
	 */
	public String toUrlEncodedString() {
		StringBuilder out = new StringBuilder();
		HttpParam[] all = all();
		for (int i = 0; i < all.length; i += 1) {
			out.append(all[i].toUrlEncodedString());
			if (i != (all.length - 1)) { out.append("&"); }
		}
		return out.toString();
	}
	
}
