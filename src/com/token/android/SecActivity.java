package com.token.android;

import java.util.Timer;
import java.util.TimerTask;

import com.token.android.SettingActivity.Env;
import com.token.android.utils.DateUtil;
import com.token.android.utils.MD5Util;
import com.token.android.utils.PreferencesUtils;
import com.token.android.widget.view.NumberProgressBar;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecActivity extends Activity {

	private TextView mSecText;

	private int counter = 0;
	private Timer timer;
	private NumberProgressBar bnp;
	private int mTimePoot = 3;// 时间差秒
	private int mMaxProgress;

	private Env env;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sec);
		setupViews();
		env = (Env) getIntent().getSerializableExtra("env");
		updateSec();

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						bnp.incrementProgressBy(1);
						counter++;
						if (counter == mMaxProgress) {
							bnp.setProgress(0);
							updateSec();
						}
					}
				});
			}
		}, 1000, 100);

		mSecText.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				copy(mSecText.getText().toString());

				return false;
			}
		});
	}

	private void copy(String content) {
		ClipboardManager cmb = (ClipboardManager) this
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setPrimaryClip(ClipData.newPlainText(null, content));
		Toast.makeText(SecActivity.this,
				getResources().getString(R.string.already_copy),
				Toast.LENGTH_SHORT).show();
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
			Intent intent = new Intent(SecActivity.this, SettingActivity.class);
			intent.putExtra("env", env);
			startActivity(intent);
			return true;
		} else if (id == R.id.copy) {
			copy(mSecText.getText().toString());
			return true;
		} else if (id == R.id.setting_time) {
			showSettingDialog();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void showSettingDialog() {
		final Dialog dialog = new Dialog(this, R.style.CommonDialog);
		dialog.setContentView(R.layout.setting_time_dialog);
		final EditText etAppTime = (EditText) dialog.findViewById(R.id.et_time);

		String re_time = "";
		if (env == Env.Dev) {
			re_time = String.valueOf(PreferencesUtils.getInt(SecActivity.this,
					"dev_time", 1));
		} else if (env == Env.Test) {
			re_time = String.valueOf(PreferencesUtils.getInt(SecActivity.this,
					"test_time", 1));
		} else if (env == Env.Produce) {
			re_time = String.valueOf(PreferencesUtils.getInt(SecActivity.this,
					"produce_time", 1));
		}
		etAppTime.setText(re_time);

		OnClickListener ocl = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.btn_dialog_ok) {

					if (env == Env.Dev) {
						PreferencesUtils
								.putInt(SecActivity.this, "dev_time", Integer
										.parseInt(etAppTime.getText()
												.toString()));
					} else if (env == Env.Test) {
						PreferencesUtils
								.putInt(SecActivity.this, "test_time", Integer
										.parseInt(etAppTime.getText()
												.toString()));
					} else if (env == Env.Produce) {
						PreferencesUtils.putInt(SecActivity.this,
								"produce_time", Integer.parseInt(etAppTime
										.getText().toString()));
					}

					updateSec();
				}
				dialog.dismiss();
			}
		};
		dialog.findViewById(R.id.btn_dialog_ok).setOnClickListener(ocl);
		dialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(ocl);
		dialog.setCancelable(false);
		dialog.show();
	}

	private void updateSec() {

		String param1 = null;
		String param2 = null;
		String param3 = null;
		String param4 = null;

		if (env == Env.Dev) {
			param1 = PreferencesUtils.getString(this, "dev1");
			param2 = PreferencesUtils.getString(this, "dev2");
			param3 = PreferencesUtils.getString(this, "dev3");
			param4 = PreferencesUtils.getString(this, "dev4");
		} else if (env == Env.Test) {
			param1 = PreferencesUtils.getString(this, "test1");
			param2 = PreferencesUtils.getString(this, "test2");
			param3 = PreferencesUtils.getString(this, "test3");
			param4 = PreferencesUtils.getString(this, "test4");
		} else if (env == Env.Produce) {
			param1 = PreferencesUtils.getString(this, "pr1");
			param2 = PreferencesUtils.getString(this, "pr2");
			param3 = PreferencesUtils.getString(this, "pr3");
			param4 = PreferencesUtils.getString(this, "pr4");
		}

		int re_time = 1;
		if (env == Env.Dev) {
			re_time = PreferencesUtils.getInt(SecActivity.this, "dev_time", 1);
		} else if (env == Env.Test) {
			re_time = PreferencesUtils.getInt(SecActivity.this, "test_time", 1);
		} else if (env == Env.Produce) {
			re_time = PreferencesUtils.getInt(SecActivity.this, "produce_time",
					1);
		}

		String md5 = MD5Util.MD5(
				DateUtil.getDate(this, param1, param2, param3, param4,re_time) + "\n")
				.substring(1, 9)
				+ "\n";
		String base64code = new String(Base64.encode(md5.getBytes(), 0))
				.replaceAll("=", "");
		mSecText.setText(base64code.substring(0, base64code.length() > 32 ? 32
				: base64code.length()));

		int refreshTime = 0;

		if (env == Env.Dev) {
			refreshTime = PreferencesUtils.getInt(this, "dev_time", 1);
		} else if (env == Env.Test) {
			refreshTime = PreferencesUtils.getInt(this, "test_time", 1);
		} else if (env == Env.Produce) {
			refreshTime = PreferencesUtils.getInt(this, "produce_time", 1);
		}

		mMaxProgress = (refreshTime * 60 + mTimePoot) * 10;
		bnp.setMax(mMaxProgress);

		// counter = DateUtil.getSS() * 10;
		// counter=(DateUtil.getMM()*60+DateUtil.getSS())*10%mMaxTime;
		counter = (DateUtil.getMM() * 60 + DateUtil.getSS())
				% (refreshTime * 60) * 10;
		bnp.setProgress(counter);

	}

	private void setupViews() {
		mSecText = (TextView) findViewById(R.id.sec_text);
		bnp = (NumberProgressBar) findViewById(R.id.numberbar1);
	}

	@Override
	protected void onStart() {
		super.onStart();
		updateSec();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}
}
