package com.chi.scalpra.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class HttpRequestTriggerService extends IntentService {
	
	private String url;
	
	private Integer triggerTime;
	
	private Set<String> keywordsToSearch;
	
	private HttpClient httpClient = new DefaultHttpClient();
	
	private HttpGet httpGet;

	public HttpRequestTriggerService() {
		super("HttpRequestTriggerService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if(triggerTime != null && url!= null && keywordsToSearch != null && !keywordsToSearch.isEmpty()) {
		long endTime = System.currentTimeMillis() + triggerTime*1000;
	      while (System.currentTimeMillis() < endTime) {
	          synchronized (this) {
	              try {
	            	  HttpGet request = getHttpGetInstance();
	            	  HttpResponse response = httpClient.execute(request);
	            	  responseChecker(response);
	                  wait(endTime - System.currentTimeMillis());
	              } catch (Exception e) {
	              }
	          }
	      }
		}
		stopSelf();
	}
	
	private HttpGet getHttpGetInstance() {
  	  if(httpGet == null) {
		  httpGet = new HttpGet(url);
	  }
  	  return httpGet;
	}
	
	private void responseChecker(HttpResponse response) {
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			if(hasMatchInResponse(response)) {
				Toast.makeText(getBaseContext(), "Got a Match!", Toast.LENGTH_LONG);
			}
   	  	}
		
	}
	
	private boolean hasMatchInResponse(HttpResponse response) {
		String responseBody = null;
		try {
			responseBody = EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {
			//TODO log
		} catch (IOException e) {
			//TODO log
		}
		
		boolean hasMatch = false;
		Iterator<String> it = keywordsToSearch.iterator();
		while(it.hasNext() && responseBody != null) {
			if(responseBody.contains((it.next().trim()))) {
				hasMatch = true;
			}
		}
		
		return hasMatch;
	}

	public Set<String> getKeywordsToSearch() {
		return keywordsToSearch;
	}

	public void setKeywordsToSearch(Set<String> keywordsToSearch) {
		this.keywordsToSearch = keywordsToSearch;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(Integer triggerTime) {
		this.triggerTime = triggerTime;
	}

}
