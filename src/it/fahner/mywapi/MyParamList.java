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

import java.util.HashMap;

/**
 * Represents a collection of {@link MyParam}s.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyParamList {
	
	/** Contains all {@link MyParam}s, mapped by their parameter name for easy access. */
	private HashMap<String, MyParam> params;
	
	/**
	 * Creates a new, empty, collection of {@link MyParam}s.
	 * @since MyWebApi 1.0
	 */
	public MyParamList() {
		params = new HashMap<String, MyParam>();
	}
	
	
	/**
	 * Merges another list with this list and returns the results as a new {@link MyParamList}.
	 * @since MyWebApi 1.0
	 * @param other The other list to merge with this one
	 * @return The resulting list
	 */
	public MyParamList merge(MyParamList other) {
		MyParamList out = new MyParamList();
		MyParam[] ownParams = all();
		MyParam[] otherParams = other.all();
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
	 * {@link MyParam#equals(Object)}.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to search for
	 * @return <code>true</code> if the parameter exists in this list and has the same value
	 */
	public boolean has(MyParam param) {
		MyParam tmp = params.get(param.getName());
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
	public MyParam get(String name) {
		return params.get(name);
	}
	
	/**
	 * Sets a parameter in this collection. Overwrites any existing values.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to set
	 * @param value The value of the parameter to set
	 * @return This {@link MyParamList} for call chaining
	 */
	public MyParamList set(String name, String value) {
		params.put(name, new MyParam(name, value));
		return this;
	}
	
	/**
	 * Sets a parameter in this list. Overwrites any existing values.
	 * @since MyWebApi 1.0
	 * @param param The parameter to set
	 * @return This {@link MyParamList} for call chaining
	 */
	public MyParamList set(MyParam param) {
		params.put(param.getName(), param);
		return this;
	}
	
	/**
	 * Removes a parameter from this list.
	 * @since MyWebApi 1.0
	 * @param name The name of the parameter to remove
	 * @return The parameter that was removed
	 */
	public MyParam remove(String name) {
		return params.remove(name);
	}
	
	/**
	 * Returns an array of all parameters currently in this list.
	 * @since MyWebApi 1.0
	 * @return All parameters contained within this list
	 */
	public MyParam[] all() {
		MyParam[] out = new MyParam[params.size()];
		params.values().toArray(out);
		return out;
	}
	
}
