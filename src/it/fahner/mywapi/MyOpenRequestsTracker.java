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

import it.fahner.mywapi.http.HttpRequest;

import java.util.HashSet;

/**
 * This class keeps track of requests that are being resolved (i.e. are 'busy').
 * <p>Requests do, however, need to be explicitly marked as 'open'.</p>
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyOpenRequestsTracker {
	
	/** Contains the requestIdentifiers of all currently opened requests. */
	private HashSet<String> openRequests;
	
	/**
	 * Creates a new (empty) MyOpenRequestsTracker.
	 * @since MyWebApi 1.0
	 */
	public MyOpenRequestsTracker() {
		this.openRequests = new HashSet<String>();
	}
	
	/**
	 * Stores the request as an open request.
	 * @since MyWebApi 1.0
	 * @param request The request to store
	 */
	public synchronized void storeRequest(HttpRequest request) {
		this.openRequests.add(request.getResourceIdentity());
	}
	
	/**
	 * Removes the request from the list of outstanding requests.
	 * @since MyWebApi 1.0
	 * @param request The request to remove
	 */
	public synchronized void removeRequest(HttpRequest request) {
		this.openRequests.remove(request.getResourceIdentity());
	}
	
	/**
	 * Checks if a request for the same resource is already open.
	 * @since MyWebApi 1.0
	 * @param request
	 * @return <code>true</code> if a request for the same resource has already been opened,
	 *  <code>false</code> otherwise
	 */
	public synchronized boolean isOpen(HttpRequest request) {
		return this.openRequests.contains(request.getResourceIdentity());
	}
	
	/**
	 * Returns the amount of requests currently outstanding.
	 * @since MyWebApi 1.0
	 * @return The amount of open requests
	 */
	public synchronized int getAmount() {
		return this.openRequests.size();
	}
	
}
