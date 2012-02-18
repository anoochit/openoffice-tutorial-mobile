package net.redlinesoft.app.oootutorial;

import net.redlinesoft.app.oootutorial.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	ProgressBar mProgress;
	int mProgressStatus = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		// play with progress bar
		mProgress = (ProgressBar) findViewById(R.id.progressBar);

		final WebView webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setSupportZoom(true);
		// webView.getSettings().setBuiltInZoomControls(true);

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// Handle the error
				webView.loadUrl("file:///android_asset/www/notconnect.html");
			}			
		});

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("vnd.youtube:")) {
				    String youtubeUrl = "http://www.youtube.com/watch?v=" + url.replace("vnd.youtube:", "");
				    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)));
				}
				return true;
			}

		});

		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Toast.makeText(getApplicationContext(),
				// "Loading " + progress + "% ...", Toast.LENGTH_SHORT)
				// .show();
				//mProgress.animate();
				mProgress.setProgress(progress);

			}
		});

		// check screen dimension
		//Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		//int width = display.getWidth();
		//int height = display.getHeight();

		//Toast.makeText(getApplicationContext(), width + "x" + height, Toast.LENGTH_LONG).show();

		if (this.chkStatus()) {
			webView.loadUrl("file:///android_asset/www/index.html");
		} else {
			webView.loadUrl("file:///android_asset/www/notconnect.html");
		}
	}
	
	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// We do nothing here. We're only handling this to keep orientation
		// or keyboard hiding from causing the WebView activity to restart.
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		WebView webView = (WebView) findViewById(R.id.webView);
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean chkStatus() {
		final ConnectivityManager connMgr = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (wifi.isAvailable()) {
			// Toast.makeText(this, "Wifi" , Toast.LENGTH_LONG).show();
			return true;
		} else if (mobile.isAvailable()) {
			// Toast.makeText(this, "Mobile 3G " , Toast.LENGTH_LONG).show();
			return true;
		} else {
			Toast.makeText(this, "No Network ", Toast.LENGTH_LONG).show();
			return false;
		}
	}

}