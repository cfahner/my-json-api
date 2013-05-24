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
 * Event listener. Implementations of this interface can handle events fired by
 * MyJsonApi.
 * <p>Note: Remember to start listening to MyJsonApi by calling it's startListening method.</p>
 * @since MyJsonApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public interface MyJsonApiListener {
	
	/**
	 * Implement this method to run code when a MyRequest has been resolved.
	 * <p>
	 * The implementation will receive the original MyRequest it has sent previously, but now in a resolved state.
	 * Note: Compare the runtime class of the resolved MyRequest to one of your own implementations to check what
	 * kind of MyRequest has been resolved.
	 * </p>
	 * @see MyRequest MyRequest documentation
	 * @since MyJsonApi 1.0
	 * @param request The MyRequest that has been resolved
	 */
	public void onRequestResolved(MyRequest request);
	
}
