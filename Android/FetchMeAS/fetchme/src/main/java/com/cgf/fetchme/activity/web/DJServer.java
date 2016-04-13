package com.cgf.fetchme.activity.web;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

public class DJServer {
//	private static final String BASE_URL = "http://192.168.1.102:80/FetchMe/";
	private static final String BASE_URL = "http://chenguanfu.f3322.net/FetchMe/";
	private static final String TAG = "DJServer";
	private static AsyncHttpClient client = new AsyncHttpClient();
	static {
		// avoid the server is too slower then set more timeout
		client.setTimeout(200000);
	}
	private static String csrfToken;

	public static String getCsrfToken() {
		return csrfToken;
	}

	public static void setCsrfToken(String csrfToken) {
//		if(csrfToken == null)
			DJServer.csrfToken = csrfToken;
	}

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		if(params == null) {
			params = new RequestParams();
		}
		params.put("csrfmiddlewaretoken", csrfToken);
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void uploadImage(String url, RequestParams params, AsyncHttpResponseHandler responseHandler, String imageFilePath) {
		Log.d(TAG, "uploadImage----in----");
		if(params == null) {
			params = new RequestParams();
		}
		params.put("csrfmiddlewaretoken", csrfToken);
		params.put("title", "fileUpload");
		try {
			params.put("fileUpload", new File(imageFilePath));
		} catch (FileNotFoundException e) {
			Log.d(TAG, "file can not be found");
			e.printStackTrace();
		}
		client.post(getAbsoluteUrl(url), params, responseHandler);
		Log.d(TAG, "uploadImage----out----");
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
