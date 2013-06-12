package it.fahner.mywapi.test;

import it.fahner.mywapi.MyRequest;
import it.fahner.mywapi.MyWebApi;
import it.fahner.mywapi.MyRequestListener;
import it.fahner.mywapi.http.HttpResponse;
import it.fahner.mywapi.http.types.HttpParamList;
import it.fahner.mywapi.http.types.HttpRequestMethod;

/**
 * Use this for testing.
 * @author Christiaan
 *
 */
public class MyWebApiTester {

	public static void main(String[] args) {
		final MyWebApi api = new MyWebApi("http://www.fahnerit.com/api/quizit/");
		
		final boolean secondOne = false;
		final MyRequest sample = new MyRequest() {
			
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
				return 600000;
			}
			
			@Override
			public String getBody() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void fail() {
				System.err.println("Err");
			}
			
			@Override
			public void complete(HttpResponse response) {
				System.out.println(response.getCreateTime());
			}
		};
		
		api.startListening(new MyRequestListener() {
			
			@Override
			public void onRequestResolved(MyRequest request) {
				System.out.println(request.getUrlParameters());
				try {
					Thread.sleep(250);
					api.startRequest(sample);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		api.startRequest(sample);
	}
	
}
