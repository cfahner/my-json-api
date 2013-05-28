package it.fahner.mywapi.http;

public class HttpConsistencyException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public HttpConsistencyException() {
		super("Cannot modify an HttpRequest or HttpResponse after it has been used.");
	}
	
}
