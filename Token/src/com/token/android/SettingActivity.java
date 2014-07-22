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
		mDetermine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!StringUtils.isBlank(mET1.getText().toString())
						&& !StringUtils.isBlank(mET2.getText().toString())
						&& !StringUtils.isBlank(mET3.getText().toString())
						&& !StringUtils.isBlank(mET4.getText().toString())) {

					PreferencesUtils.putString(SettingActivity.this, "a", mET1
							.getText().toString());
					PreferencesUtils.putString(SettingActivity.this, "b", mET2
							.getText().toString());
					PreferencesUtils.putString(SettingActivity.this, "c", mET3
							.getText().toString());
					PreferencesUtils.putString(SettingActivity.this, "d", mET4
							.getText().toString());

					Intent intent = new Intent(SettingActivity.this,
							SecActivity.class);
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
