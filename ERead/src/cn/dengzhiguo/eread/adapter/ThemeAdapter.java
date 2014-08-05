package cn.dengzhiguo.eread.adapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.dengzhiguo.eread.bo.ITheme;
import cn.dengzhiguo.eread.bo.ThemeImpl;
import cn.dengzhiguo.eread.view.ThemeView;
import cn.dengzhiguo.eread.view.ThemeView_;
@EBean
public class ThemeAdapter extends BaseAdapter {

	@RootContext
	Context context;
	
	@Bean(ThemeImpl.class)
	ITheme themeBo;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return themeBo.getAllTheme().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return themeBo.getAllTheme().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View converterView, ViewGroup arg2) {
		ThemeView view=(ThemeView)converterView;
		if(view==null){
			view=ThemeView_.build(context);
		}
		view.bind(themeBo.getAllTheme().get(position));
		return view;
	}

}
