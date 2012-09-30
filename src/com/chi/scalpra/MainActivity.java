package com.chi.scalpra;

import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.chi.scalpra.service.HttpRequestTriggerService;

public class MainActivity extends Activity {
	
	private String url;
	
	private Integer triggerTime;
	
	private Set<String> keywordsToSearch = null;
	
	private HttpRequestTriggerService triggerService = new HttpRequestTriggerService();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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

	public Set<String> getKeywordsToSearch() {
		return keywordsToSearch;
	}

	public void setKeywordsToSearch(Set<String> keywordsToSearch) {
		this.keywordsToSearch = keywordsToSearch;
	}
    
    
}
