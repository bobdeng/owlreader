package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.EAction;
import mobi.zhangying.cyclops.Invoke;

@EAction(action=SystemAction.class)
public interface ISystem {
	@Invoke
	public void initBooks();
}
