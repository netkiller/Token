package com.token.android;

import com.token.android.utils.PreferencesUtils;
import com.token.android.utils.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity {

	public enum Env {
		Dev, Test, Produce
	}

	private EditText mET1;
	private EditText mET2;
	private EditText mET3;
	private EditText mET4;
	private Button mDetermine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		setupViews();

		final Env env = (Env) getIntent().getSerializableExtra("env");

		mDetermine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!StringUtils.isBlank(mET1.getText().toString())
						&& !StringUtils.isBlank(mET2.getText().toString())
						&& !StringUtils.isBlank(mET3.getText().toString())
						&& !StringUtils.isBlank(mET4.getText().toString())) {

					String param1 = null;
					String param2 = null;
					String param3 = null;
					String param4 = null;
					if (env == Env.Dev) {
						param1 = "dev1";
						param2 = "dev2";
						param3 = "dev3";
						param4 = "dev4";
					} else if (env == Env.Test) {
						param1 = "test1";
						param2 = "test2";
						param3 = "test3";
						param4 = "test4";
					} else if (env == Env.Produce) {
						param1 = "pr1";
						param2 = "pr2";
						param3 = "pr3";
						param4 = "pr4";
					}

					PreferencesUtils.putString(SettingActivity.this, param1,
							mET1.getText().toString());
					PreferencesUtils.putString(SettingActivity.this, param2,
							mET2.getText().toString());
					PreferencesUtils.putString(SettingActivity.this, param3,
							mET3.getText().toString());
					PreferencesUtils.putString(SettingActivity.this, param4,
							mET4.getText().toString());

					Intent intent = new Intent(SettingActivity.this,
							SecActivity.class);
					intent.putExtra("env", env);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(SettingActivity.this, "输入不能为空",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private void setupViews() {
		mDetermine = (Button) findViewById(R.id.determine_setting);
		mET1 = (EditText) findViewById(R.id.et1);
		mET2 = (EditText) findViewById(R.id.et2);
		mET3 = (EditText) findViewById(R.id.et3);
		mET4 = (EditText) findViewById(R.id.et4);
	}

}
