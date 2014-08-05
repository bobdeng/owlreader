package cn.dengzhiguo.eread.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.dengzhiguo.eread.db.Newword;
import cn.dengzhiguo.eread.view.WordView;
import cn.dengzhiguo.eread.view.WordView_;
@EBean
public class WordsListAdapter extends BaseAdapter {

	private List<Newword> words=new ArrayList<Newword>();
	@RootContext
	Context context;
	@Override
	public int getCount() {
		return words.size();
	}

	@Override
	public Object getItem(int arg0) {
		return words.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		WordView view=(WordView)convertView;
		if(view==null){
			view=WordView_.build(context);
		}
		view.bind(words.get(position));
		return view;
	}
	public void removeItem(Newword word){
		words.remove(word);
		this.notifyDataSetChanged();
	}
	public void setWords(List<Newword> words) {
		this.words=words;
		this.notifyDataSetChanged();
	}

}
