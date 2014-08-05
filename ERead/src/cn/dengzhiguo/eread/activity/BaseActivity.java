package cn.dengzhiguo.eread.activity;

import mobi.zhangying.cyclops.MVCAdapter;
import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	MVCAdapter mvc;
	boolean mvcRegistered=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mvc=new MVCAdapter(this);
		mvc.register(this);
		mvcRegistered=true;

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mvc.unregister();
		mvcRegistered=false;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!mvcRegistered)
			mvc.register(this);
	}
	
}
