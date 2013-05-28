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

package it.fahner.mywapi.myutil;

import it.fahner.mywapi.MyRequest;
import it.fahner.mywapi.MyWebApiListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Represents a collection of {@link MyWebApiListener}s. The listeners in the collection are
 * weakly referenced and are automatically cleaned up once a lister becomes <code>null</code>.
 * @see WeakReference
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyWebApiListenerCollection {
	
	/** Stores all weak references to the listeners. */
	private ArrayList<WeakReference<MyWebApiListener>> listeners;
	
	/**
	 * Creates a new MyWebApiListenerCollection without any listeners registered to it.
	 * @since MyWebApi 1.0
	 */
	public MyWebApiListenerCollection() {
		this.listeners = new ArrayList<WeakReference<MyWebApiListener>>();
	}
	
	/**
	 * Puts a new listener into this collection. Only stores a {@link WeakReference} to it,
	 * so it does not have to be explicitly removed (and can be safely dereferenced).
	 * @since MyWebApi 1.0
	 * @param listener The listener to put
	 */
	public synchronized void put(MyWebApiListener listener) {
		this.listeners.add(new WeakReference<MyWebApiListener>(listener));
	}
	
	/**
	 * Notifies all listeners contained in this collection about a {@link MyRequest} that
	 * has been resolved.
	 * <p>As a side effect, this method also removes all {@link WeakReference}s in this collection that
	 * point to <code>null</code>, since it is has to iterate anyway.</p>
	 * @since MyWebApi 1.0
	 * @param resolved The request that has been resolved
	 */
	public synchronized void invokeAllResolved(MyRequest resolved) {
		ArrayList<WeakReference<MyWebApiListener>> pointToNull = new ArrayList<WeakReference<MyWebApiListener>>();
		for (WeakReference<MyWebApiListener> ref : listeners) {
			if (ref == null) { continue; }
			if (ref.get() == null) { pointToNull.add(ref); continue; }
			ref.get().onRequestResolved(resolved);
		}
		listeners.removeAll(pointToNull);
	}
	
	/**
	 * Notifies all listeners contained in this collection about a content name that has
	 * been invalidated (and it's cache has been cleaned).
	 * <p>As a side effect, this method also removes all {@link WeakReference}s in this collection that
	 * point to <code>null</code>, since it is has to iterate anyway.</p>
	 * @since MyWebApi 1.0
	 * @param contentName The name of the content that has been invalidated
	 */
	public synchronized void invokeAllContentInvalidated(String contentName) {
		ArrayList<WeakReference<MyWebApiListener>> pointToNull = new ArrayList<WeakReference<MyWebApiListener>>();
		for (WeakReference<MyWebApiListener> ref : listeners) {
			if (ref == null) { continue; }
			if (ref.get() == null) { pointToNull.add(ref); continue; }
			ref.get().onContentInvalidated(contentName);
		}
		listeners.removeAll(pointToNull);
	}
	
}
