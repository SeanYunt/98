package net.yunt;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.jayway.restassured.*;
import com.jayway.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.RestAssured.*;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class ServiceTest {

	private static String GAME_SERVICE_URL = "https://w0ayb2ph1k.execute-api.us-west-2.amazonaws.com/production";

	@DataProvider
	public static Object[][] inputs() {
		return new Object[][] {
			{ "?moves=[1,1]", "[1, 1," },
			{ "?moves=[2,2]", "[2, 2," },
			{ "?moves=[0,1]", "[0, 1," },
			{ "?moves=[0,0,3,2,1,3,2,0]", "[0, 0, 3, 2, 1, 3, 2, 0,"},
		};
	}
	
	@Test
	public void serviceIsRunning() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(GAME_SERVICE_URL);
			System.out.println(httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			assertEquals("expected HTTP 400", response.getStatusLine().getStatusCode(), 400);
		} finally {
			httpclient.close();
		}
	}
	
	@Test
	@UseDataProvider("inputs")
	public void testService(String params, String expected) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(GAME_SERVICE_URL+params);
			System.out.println(httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			assertEquals("expected HTTP 200", response.getStatusLine().getStatusCode(), 200);
			try {
				String responseBody = EntityUtils.toString(response.getEntity());
				assertTrue(responseBody.contains(expected));
			} finally {
				response.close();
			}
			} finally {
				httpclient.close();
			}
	}
	
	//not sure why RestAssured gives SSL handshake exception. I've tried everything... :(
	//this would have been so much nicer...
	//@Test
	public void serviceSingleMove() throws Exception {
		RestAssured.given().
		when().
		get(GAME_SERVICE_URL+"?moves=[0]").
		then().
		statusCode(200).
		body("$", hasItems(0,1));
	}
}