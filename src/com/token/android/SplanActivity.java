package com.token.android;

import com.token.android.lockpattern.LockPatternUtils;
import com.token.android.utils.PreferencesUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class SplanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splan);
		begin();
	}

	/**
	 * 睡眠后进入首页
	 */
	private void begin() {
		AsyncTask<String, Integer, String> task = new AsyncTask<String, Integer, String>() {
			protected String doInBackground(String... params) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String result) {
				gotoMain();
			}
		};
		task.execute();
	}

	/**
	 * 进入主界面
	 */
	private void gotoMain() {
		Intent intent = null;
		String savedData = LockPatternUtils.loadFromPreferences(this);
		if (savedData == null) {
			intent = new Intent(this, SetLockPatternActivity.class);
			startActivity(intent);
		} else {
			intent = new Intent(this, VerifyLockPatternActivity.class);
			startActivity(intent);
		}
		finish();
	}

}
