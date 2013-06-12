package it.fahner.mywapi;

/**
 * Event listener. Implementations of this interface handle events that are fired when a certain
 * content name changes.
 * @since MyWebApi 1.0
 * @author C. Fahner <info@fahnerit.com>
 */
public interface MyContentListener {
	
	/**
	 * Implement this method to respond when a certain type of content (defined by
	 * it's content name) has been invalidated (and any views displaying this type
	 * of content should be updated).
	 * <p>Make sure your code runs on the UI-thread before manipulating the UI from this method.</p>
	 * @see MyRequest#getContentName()
	 * @since MyWebApi 1.0
	 * @param contentName The name of the content that has been invalidated
	 */
	public void onContentChanged(String contentName);
	
}
