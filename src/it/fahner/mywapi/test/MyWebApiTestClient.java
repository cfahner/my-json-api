package it.fahner.mywapi.test;

import it.fahner.mywapi.MyRequest;
import it.fahner.mywapi.MyWebApi;
import it.fahner.mywapi.http.HttpParamList;
import it.fahner.mywapi.http.HttpRequestMethod;
import it.fahner.mywapi.http.HttpResponse;

/**
 * Use this for testing.
 * @author Christiaan
 *
 */
public class MyWebApiTestClient {

	public static void main(String[] args) {
		MyWebApi api = new MyWebApi("http://www.fahnerit.com/api/quizit/");
		
		MyRequest sample = new MyRequest() {
			
			@Override
			public HttpParamList getUrlParameters() {
				return new HttpParamList()
					.set("call", "questions/random")
					.set("language", "en");
			}
			
			@Override
			public HttpRequestMethod getRequestMethod() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContentName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getCacheTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public HttpParamList getBodyParameters() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void fail() {
				System.err.println("Err");
			}
			
			@Override
			public void complete(HttpResponse response) {
				System.out.println(response.body);
			}
		};
		api.startRequest(sample);
	}
	
}
