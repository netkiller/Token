package com.token.android.lockpattern;


public class SetLockPatternModel {

	private String mLockPattern = null;

	public SetLockPatternModel() {
	}

	public String getLockPattern() {
		return mLockPattern;
	}

	public void setLockPattern(String lockPattern) {
		mLockPattern = lockPattern;
	}

	public boolean isTooShort(String lockPattern) {
		if(lockPattern == null) {
			return true;
		}
		if(lockPattern.length() < LockPatternUtils.MIN_LENGTH_OF_CELLS * 2) {
			return true;
		}
			
		return false;
	}
}