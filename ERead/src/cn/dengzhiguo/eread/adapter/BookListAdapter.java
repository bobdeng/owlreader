package cn.dengzhiguo.eread.adapter;

import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.dengzhiguo.eread.db.Book;
import cn.dengzhiguo.eread.view.BookView;
import cn.dengzhiguo.eread.view.BookView_;
@EBean
public class BookListAdapter extends BaseAdapter {

	private List<Book> books;
	@RootContext
	Context context;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return books!=null?books.size():0;
	}

	@Override
	public Object getItem(int p) {
		// TODO Auto-generated method stub
		return books.get(p);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		BookView view=(BookView)convertView;
		if(view==null){
			view=BookView_.build(context);
		}
		view.bind(books.get(position));
		return view;
	}

	

	public void setBooks(List<Book> books) {
		this.books = books;
		this.notifyDataSetChanged();
	}

	
	

}
