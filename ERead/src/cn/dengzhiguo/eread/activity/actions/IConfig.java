package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.EAction;
import mobi.zhangying.cyclops.Invoke;

@EAction(action=ConfigAction.class)
public interface IConfig {
	@Invoke
	public void readConfig();
	@Invoke
	public void saveConfig(int fontSize,int fontType,int theme);
}
