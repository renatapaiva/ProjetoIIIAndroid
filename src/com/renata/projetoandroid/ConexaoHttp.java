package com.renata.projetoandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.provider.SyncStateContract.Constants;
import android.util.Log;


//Requisições http
public class ConexaoHttp {

	public static final int HTTP_TIMEOUT = 30 * 1000;
	private static HttpClient httpClient;

	public static String getRequest(String url) {
		String retorno = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			HttpResponse responseGet = client.execute(get);
			HttpEntity resEntityGet = responseGet.getEntity();
			if (resEntityGet != null) {
				retorno = EntityUtils.toString(resEntityGet);
			}
		} catch (ClientProtocolException e) {
			Log.d(Constants._ID, e.getMessage());
		} catch (IOException e) {
			Log.d(Constants._ID, e.getMessage());
		} catch (Exception e) {
			Log.d(Constants._ID, e.getMessage());
		}
		return retorno;
	}

	public static String postRequest(String url, JSONObject json) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			ResponseHandler<String> resonseHandler = new BasicResponseHandler();
			HttpPost postMethod = new HttpPost(
					"http://bolaoshow.herokuapp.com/service/usuario");
			postMethod.setHeader("Content-Type", "application/json");
			postMethod.setEntity(new ByteArrayEntity(json.toString().getBytes(
					"UTF8")));
			String response = httpClient.execute(postMethod, resonseHandler);
			return response;
		} catch (ClientProtocolException e) {
			Log.d(Constants._ID, e.getMessage());
		} catch (IOException e) {
			Log.d(Constants._ID, e.getMessage());
		} catch (Exception e) {
			Log.d(Constants._ID, e.getMessage());
		}
		return null;
	}

}
