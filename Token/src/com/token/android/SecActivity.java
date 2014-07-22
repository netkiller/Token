package com.token.android;

import java.util.Timer;
import java.util.TimerTask;

import com.token.android.utils.DateUtil;
import com.token.android.utils.MD5Util;
import com.token.android.widget.view.NumberProgressBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SecActivity extends Activity {

	private TextView mSecText;

	private int counter = 0;
	private Timer timer;
	private NumberProgressBar bnp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sec);
		setupViews();
		updateSec();

		// counter = 0;
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						bnp.incrementProgressBy(1);
						counter++;
						if (counter == 630) {
							bnp.setProgress(0);
							// counter = 0;
							updateSec();
						}
					}
				});
			}
		}, 1000, 100);

	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		updateSec();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.re_setting) {
			Intent intent=new Intent(SecActivity.this,SettingActivity.class);
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void updateSec() {
		String md5 = MD5Util.MD5(DateUtil.getDate(this) + "\n").substring(1, 9)
				+ "\n";
		String base64code = new String(Base64.encode(md5.getBytes(), 0))
				.replaceAll("=", "");
		mSecText.setText(base64code.substring(0, base64code.length() > 32 ? 32
				: base64code.length()));

		counter = DateUtil.getSS() * 10;
		bnp.setProgress(counter);
	}

	private void setupViews() {
		mSecText = (TextView) findViewById(R.id.sec_text);
		bnp = (NumberProgressBar) findViewById(R.id.numberbar1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}
}
