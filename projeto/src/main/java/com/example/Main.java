package com.example;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Main {

	public static void main(String[] args) {
		sendGet();


		try {
			sendPut("http://localhost:8080/gateway/project/person/6");
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//sendDelete("http://localhost:8080/gateway/project/person/4");
		
//		try {
//			sendPost("http://localhost:8080/gateway/project/person");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private static void sendGet() {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {

			HttpGet request = new HttpGet("http://localhost:8080/gateway/project/person");

			// add request headers
			request.addHeader("custom-key", "mkyong");
			request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

			CloseableHttpResponse response = httpClient.execute(request);

			try {

				// Get HttpResponse Status
				System.out.println(response.getProtocolVersion()); // HTTP/1.1
				System.out.println(response.getStatusLine().getStatusCode()); // 200
				System.out.println(response.getStatusLine().getReasonPhrase()); // OK
				System.out.println(response.getStatusLine().toString()); // HTTP/1.1 200 OK

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// return it as a String
					String result = EntityUtils.toString(entity);
					System.out.println(result);
				}

			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void sendPost(String url) throws IOException {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"name\":\"Sakura\",");
		json.append("\"birthDate\":\"2007-01-05\",");
		json.append("\"age\":\"3\",");
		json.append("\"genre\":\"F\",");
		json.append("\"address\":\"Rua Rezala Simão\"");
		json.append("}");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Accept", "application/json");
		
		httpPost.setEntity(entity);
		entity.setContentEncoding("application/json");
	    entity.setContentType("application/json");

		CloseableHttpResponse response = client.execute(httpPost);
		System.out.println(response.getStatusLine().getStatusCode());
		client.close();
	}

	private static void sendDelete(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);

		System.out.println("Executing request " + httpDelete.getRequestLine());

		// Create a custom response handler
		ResponseHandler<String> responseHandler = response -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		};
		String responseBody = null;
		try {
			responseBody = httpclient.execute(httpDelete, responseHandler);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("----------------------------------------");
		System.out.println(responseBody);

	}
	
	private static void sendPut(String url) throws ClientProtocolException, IOException {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"name\":\"Sakureti\",");
		json.append("\"birthDate\":\"2007-01-05\",");
		json.append("\"age\":\"3\",");
		json.append("\"genre\":\"F\",");
		json.append("\"address\":\"Rua Rezala Simão\"");
		json.append("}");
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		 
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
 
        StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
        httpPut.setEntity(entity);
        
		entity.setContentEncoding("application/json");
	    entity.setContentType("application/json");
	    
        System.out.println("Executing request " + httpPut.getRequestLine());
 
        HttpResponse response = httpclient.execute(httpPut);
        
        System.out.println(response.getStatusLine().getReasonPhrase());
	}

}
