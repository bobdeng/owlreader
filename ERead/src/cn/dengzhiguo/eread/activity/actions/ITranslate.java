package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.EAction;
import mobi.zhangying.cyclops.Invoke;

@EAction(action=TranslateAction.class)
public interface ITranslate {
	@Invoke
	public void translate(String word);
	@Invoke
	public void playvoice(String url);
}
