package com.token.android;

import com.token.android.SettingActivity.Env;
import com.token.android.utils.PreferencesUtils;
import com.token.android.utils.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectEnvActivity extends Activity implements OnClickListener {

	private Button mDevBtn;
	private Button mTestBtn;
	private Button mProduceBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_env);
		setupViews();
	}

	private void setupViews() {
		mDevBtn = (Button) findViewById(R.id.dev_btn);
		mTestBtn = (Button) findViewById(R.id.test_btn);
		mProduceBtn = (Button) findViewById(R.id.produce_btn);
		mDevBtn.setOnClickListener(this);
		mTestBtn.setOnClickListener(this);
		mProduceBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.dev_btn:
			String dev = PreferencesUtils.getString(this, "dev1");
			intent.putExtra("env", Env.Dev);
			if (StringUtils.isBlank(dev)) {
				intent.setClass(this, SettingActivity.class);
			} else {
				intent.setClass(this, SecActivity.class);
			}

			break;
		case R.id.test_btn:
			String test = PreferencesUtils.getString(this, "test1");
			intent.putExtra("env", Env.Test);
			if (StringUtils.isBlank(test)) {
				intent.setClass(this, SettingActivity.class);
			} else {
				intent.setClass(this, SecActivity.class);
			}
			break;
		case R.id.produce_btn:
			String pr = PreferencesUtils.getString(this, "pr1");
			intent.putExtra("env", Env.Produce);
			if (StringUtils.isBlank(pr)) {
				intent.setClass(this, SettingActivity.class);
			} else {
				intent.setClass(this, SecActivity.class);
			}
			break;
		}
		startActivity(intent);
	}
}
