package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.ActionMethod;
import mobi.zhangying.cyclops.ResponseMethod;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import cn.dengzhiguo.eread.modal.ConfigInfo;
import cn.dengzhiguo.sp.Config_;

@EBean
public class ConfigAction implements IConfig {
	@Pref
	Config_ config;
	
	@ActionMethod
	public void readConfig() {
		ConfigInfo conf=new ConfigInfo();
		conf.setFontSize(config.fontSize().get());
		conf.setFontType(config.fontType().get());
		conf.setTheme(config.theme().get());
		readConfigResult(conf);
	}
	@ResponseMethod
	public void readConfigResult(ConfigInfo config){}
	@ActionMethod
	public void saveConfig(int fontSize,int fontType,int theme) {
		if(fontSize>0)
			config.fontSize().put(fontSize);
		if(fontType>=0)
			config.fontType().put(fontType);
		if(theme>=0)
			config.theme().put(theme);

	}

}
