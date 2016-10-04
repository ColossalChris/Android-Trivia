package com.webnmobilesolutions.androidtrivia;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class MainActivity extends Activity {
	
	SharedPreferences prefs;
	WebView myWebView;
	Boolean b1 = false;
	Boolean b2 = false;
	Boolean b3 = false;
	Boolean b4 = false;
	Boolean b5 = false;
	Boolean b6 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prefs = getApplicationContext().getSharedPreferences("com.webnmobilesolutions.androidtrivia", Context.MODE_PRIVATE);
        String prevURL = prefs.getString("prevURL", "https://twitter.com/ohsomeinc");
        
        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(prevURL);
        
        View adminView = findViewById(R.id.adminView);
    	adminView.setBackgroundColor(Color.GRAY);
        if(prevURL.length() > 0){
        	adminView.setVisibility(View.GONE);
        }
        
        EditText urlEditText = (EditText)findViewById(R.id.urlEditText);
        urlEditText.setText(prevURL);
        urlEditText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                	prefs.edit().putString("prevURL", v.getText().toString()).apply();
                	myWebView.loadUrl(v.getText().toString());
                	
                	EditText urlEditText = (EditText)findViewById(R.id.urlEditText);
                	InputMethodManager imm = (InputMethodManager)getSystemService(
                		      Context.INPUT_METHOD_SERVICE);
                	imm.hideSoftInputFromWindow(urlEditText.getWindowToken(), 0);
                    
                    return true;
                }
                return false;
            }
        });
        
        Button doneButton = (Button)findViewById(R.id.doneButton);
        doneButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
            	View adminView = findViewById(R.id.adminView);
            	adminView.setVisibility(View.GONE);
            }
        });
        
        Button bottomRightButton = (Button)findViewById(R.id.bottomRightButton);
        bottomRightButton.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(!b1){
            		b1 = true;
            	}else if(!b2){
            		b2 = true;
            	}else if(!b3){
            		b3 = true;
            	}else if(!b4){
            		b4 = true;
            	}else if(!b5){
            		b5 = true;
            	}else if(!b6){
            		b6 = true;
            	}
            	
            	shouldShowAdminView();
            	
            	Handler handler = new Handler();
            	handler.postDelayed(runnable, 3000);
            }
        });
        
        View decorView = setSystemUiVisibilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUiVisibilityMode(); // Needed to avoid exiting immersive_sticky when keyboard is displayed
            }
        });

    }
    
    private View setSystemUiVisibilityMode() {

        View decorView = getWindow().getDecorView();
        int options;
        options =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(options);

        return decorView;
    }
    
    public Runnable runnable = new Runnable() {
    	   @Override
    	   public void run() {
    	      b1 = false;
    	      b2 = false;
    	      b3 = false;
    	      b4 = false;
    	      b5 = false;
    	      b6 = false;
    	   }
    };

    public void shouldShowAdminView(){
    	if(b1 && b2 && b3 && b4 && b5 && b6){
    		View adminView = findViewById(R.id.adminView);
        	adminView.setVisibility(View.VISIBLE);
        	b1 = false;
        	b2 = false;
        	b3 = false;
        	b4 = false;
        	b5 = false;
  	      	b6 = false;
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setSystemUiVisibilityMode();
    }
}
