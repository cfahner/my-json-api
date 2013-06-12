package it.fahner.mywapi.myutil;

import it.fahner.mywapi.MyContentListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Represents a collection of {@link MyContentListener}s. The listeners in the collection are
 * weakly referenced and are automatically cleaned up once a lister becomes <code>null</code>.
 * @see WeakReference
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public class MyContentListenerCollection {
	
	/** Stores all weak references to the listeners. */
	private ArrayList<WeakReference<MyContentListener>> listeners;
	
	/**
	 * Creates a new MyRequestListenerCollection without any listeners registered to it.
	 * @since MyWebApi 1.0
	 */
	public MyContentListenerCollection() {
		this.listeners = new ArrayList<WeakReference<MyContentListener>>();
	}
	
	/**
	 * Puts a new listener into this collection. Only stores a {@link WeakReference} to it,
	 * so it does not have to be explicitly removed (and can be safely dereferenced).
	 * @since MyWebApi 1.0
	 * @param listener The listener to put
	 */
	public synchronized void put(MyContentListener listener) {
		this.listeners.add(new WeakReference<MyContentListener>(listener));
	}
	
	/**
	 * Notifies all listeners contained in this collection about a content name that has changed.
	 * This is usually caused by a cache invalidation.
	 * <p>As a side effect, this method also removes all {@link WeakReference}s in this collection that
	 * point to <code>null</code>, since it is has to iterate anyway.</p>
	 * @since MyWebApi 1.0
	 * @param resolved The request that has been resolved
	 */
	public synchronized void invokeAll(String contentName) {
		ArrayList<WeakReference<MyContentListener>> pointToNull = new ArrayList<WeakReference<MyContentListener>>();
		for (WeakReference<MyContentListener> ref : listeners) {
			if (ref == null) { continue; }
			if (ref.get() == null) { pointToNull.add(ref); continue; }
			ref.get().onContentChanged(contentName);
		}
		listeners.removeAll(pointToNull);
	}
}
