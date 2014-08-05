package cn.dengzhiguo.eread.activity.actions;

import cn.dengzhiguo.eread.db.Newword;
import mobi.zhangying.cyclops.EAction;
import mobi.zhangying.cyclops.Invoke;

@EAction(action=NewwordAction.class)
public interface NewwordInterface {
	@Invoke
	public void listNewwords();
	@Invoke
	public void deleteNewword(Newword word);
}
