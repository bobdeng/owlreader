package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.EAction;
import mobi.zhangying.cyclops.Invoke;
import cn.dengzhiguo.eread.db.Book;

@EAction(action=BookAction.class)
public interface IBookAction {

	@Invoke
	public void listBook();
	@Invoke
	public void deleteBook(Book book);
	@Invoke
	public void saveBook(Book book);	
}
