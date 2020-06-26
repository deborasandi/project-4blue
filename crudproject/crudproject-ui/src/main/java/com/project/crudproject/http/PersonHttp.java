package com.project.crudproject.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.project.crudproject.Person;

public class PersonHttp {
	public static void create(String url, Person person) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"name\":\"" + person.getName() + "\",");
		json.append("\"birthDate\":\"" + person.getBirthDate() + "\",");
		json.append("\"age\":\"" + person.getAge() + "\",");
		json.append("\"genre\":\"" + person.getGenre() + "\",");
		json.append("\"address\":\"" + person.getAddress() + "\"");
		json.append("}");

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Accept", "application/json");

		httpPost.setEntity(entity);
		entity.setContentEncoding("application/json");
		entity.setContentType("application/json");

		CloseableHttpResponse response;

		try {
			response = client.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());
			client.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<Person> list(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {

			HttpGet request = new HttpGet(url);

			request.addHeader("custom-key", "mkyong");
			request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

			CloseableHttpResponse response = httpClient.execute(request);

			try {
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					String result = EntityUtils.toString(entity);

					Gson gson = new Gson();
					Person[] persons = gson.fromJson(result, Person[].class);

					List<Person> list = new ArrayList<>();

					for (int i = 0; i < persons.length; i++) {
						list.add(persons[i]);
					}

					return list;
				}

			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void delete(String url, Long id) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url + "/" + id);

		System.out.println("Executing request " + httpDelete.getRequestLine());

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
		System.out.println(responseBody);

	}

	public static void update(String url, Person person) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"name\":\"" + person.getName() + "\",");
		json.append("\"birthDate\":\"" + person.getBirthDate() + "\",");
		json.append("\"age\":\"" + person.getAge() + "\",");
		json.append("\"genre\":\"" + person.getGenre() + "\",");
		json.append("\"address\":\"" + person.getAddress() + "\"");
		json.append("}");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPut httpPut = new HttpPut(url + "/" + person.getId());
		httpPut.setHeader("Accept", "application/json");
		httpPut.setHeader("Content-type", "application/json");

		StringEntity entity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
		httpPut.setEntity(entity);

		entity.setContentEncoding("application/json");
		entity.setContentType("application/json");

		System.out.println("Executing request " + httpPut.getRequestLine());

		HttpResponse response;
		try {
			response = httpclient.execute(httpPut);
			System.out.println(response.getStatusLine().getReasonPhrase());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
