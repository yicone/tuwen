package com.lutours.tuwen.view;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

import java.util.LinkedList;

/**
 * Created by xdzheng on 14-1-29.
 */
public class MyFragmentTabHost extends FragmentTabHost {
	private LinkedList<Integer> mTabIndexStack = new LinkedList<Integer>();

	public MyFragmentTabHost(Context context) {
		super(context);
	}

	public MyFragmentTabHost(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	@Override
	public void setCurrentTab(int index) {
        int location = mTabIndexStack.indexOf(index);
		if (location >= 0) {
			mTabIndexStack.remove(location);
		}
		mTabIndexStack.add(index);
		super.setCurrentTab(index);
	}

	public boolean onBackPressed() {
		if (mTabIndexStack.size() > 0) {
			int location = mTabIndexStack.size() - 1;
			mTabIndexStack.remove(location);
			if (mTabIndexStack.size() > 0) {
				int index = mTabIndexStack.get(mTabIndexStack.size() - 1);
				super.setCurrentTab(index);
				return true;
			}
		}
		return false;
	}
}
