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
 * An HTTP request method supported by HttpWorker.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public enum HttpRequestMethod {
	
	/** HTTP's GET request method. */
	GET,
	
	/** HTTP's POST request method. */
	POST,
	
	/** HTTP's HEAD request method. */
	HEAD,
	
	/** HTTP's OPTIONS request method. */
	OPTIONS,
	
	/** HTTP's PUT request method. */
	PUT,
	
	/** HTTP's DELETE request method. */
	DELETE,
	
	/** HTTP's TRACE request method. Some platforms don't allow this method. */
	TRACE;
	
}