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

package it.fahner.myjson;

/**
 * A class that contains all possible configuration for the MyJsonApi.
 * @since 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyJsonConfigs {
	
	/** Stores the URL that points to the Api. */
	private String location;
	
	/**
	 * Creates a new Api configurations object.
	 * @param location The URL that points to the Api
	 */
	public MyJsonConfigs(String location) {
		this.location = location;
	}
	
	/**
	 * Returns the URL that points to the Api.
	 * @since 1.0
	 * @return The Api URL
	 */
	public String getLocation() {
		return location;
	}
	
}
