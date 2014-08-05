package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.EAction;
import mobi.zhangying.cyclops.Invoke;
import cn.dengzhiguo.eread.widget.PinData;
@EAction(action=PinAction.class)
public interface IPin {
	@Invoke
	public void listPin(int bookid,int from,int to);
	@Invoke
	public void savePin(PinData pin,int bookid);
	@Invoke
	public void deletePin(PinData data);
}
