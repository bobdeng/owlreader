package cn.dengzhiguo.eread;

import mobi.zhangying.cyclops.MVCController;

import org.androidannotations.annotations.EApplication;

import android.app.Application;
@EApplication
public class MainApp extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MVCController.Bind(this);
	}

	
}
