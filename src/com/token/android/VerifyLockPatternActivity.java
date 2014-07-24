package com.token.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.token.android.lockpattern.LockPatternUtils;
import com.token.android.lockpattern.VerifyLockPatternView;
import com.token.android.lockpattern.LockPatternView.Cell;
import com.token.android.lockpattern.LockPatternView.OnPatternListener;

public class VerifyLockPatternActivity extends Activity implements OnPatternListener {

	private final static String TAG = "VerifyLockPatternActivity";
	
	public static final int VERIFY_STATUS_READY = 1001;
	public static final int VERIFY_STATUS_ERROR = 1002;

	private VerifyLockPatternView mView = null;

	private int mCurrentStatus = VERIFY_STATUS_READY;
	
	private int mLeftTimes = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		mView = new VerifyLockPatternView(this);
		//
		reset();
	}

	private void reset() {
		mLeftTimes = 10;
		mCurrentStatus = VERIFY_STATUS_READY;
		mView.updateView(mCurrentStatus, mLeftTimes);
	}
	
	@Override
	public void onPatternDetected(List<Cell> pattern) {
		Log.d(TAG, "onPatternDetected:" + pattern);
		String data = LockPatternUtils.convertToSequence(pattern);
		String savedData = LockPatternUtils.loadFromPreferences(this);
		if(savedData != null && savedData.equals(data)) {
			//解锁成功
			Intent intent = new Intent(VerifyLockPatternActivity.this,
					SecActivity.class);
			startActivity(intent);
			finish();
		} else {//解锁失败
			mCurrentStatus = VERIFY_STATUS_ERROR;
			mLeftTimes--;
			if(mLeftTimes <= 0) {
//				closeActivity(false);
			} else {
				mView.updateView(mCurrentStatus, mLeftTimes);
			}
		}
	}
	
	@Override
	public void onPatternStart() {
		Log.d(TAG, "onPatternStart");
	}
	
	@Override
	public void onPatternCellAdded(List<Cell> pattern) {
		Log.d(TAG, "onPatternCellAdded:" + pattern);
	}

	@Override
	public void onPatternCleared() {
		Log.d(TAG, "onPatternCleared");
	}
}
