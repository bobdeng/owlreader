package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.EAction;
import mobi.zhangying.cyclops.Invoke;
import cn.dengzhiguo.eread.widget.HighlightData;
@EAction(action=HighlightAction.class)
public interface IHighlightAction {
	@Invoke
	public void listHighlight(int bookid,int from, int to);
	@Invoke
	public void saveHighlight(int bookid,HighlightData data);
	@Invoke
	public void deleteHighlight(HighlightData data);
}
