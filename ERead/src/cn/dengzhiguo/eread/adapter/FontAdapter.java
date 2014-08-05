package cn.dengzhiguo.eread.adapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.dengzhiguo.eread.bo.FontImpl;
import cn.dengzhiguo.eread.bo.IFont;
import cn.dengzhiguo.eread.view.FontView;
import cn.dengzhiguo.eread.view.FontView_;
@EBean
public class FontAdapter extends BaseAdapter {

	@RootContext
	Context context;
	
	@Bean(FontImpl.class)
	IFont fontBo;
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fontBo.getAllFont().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return fontBo.getAllFont().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View converterView, ViewGroup arg2) {
		FontView fontView=(FontView)converterView;
		if(fontView==null){
			fontView=FontView_.build(context);
		}
		fontView.bind(fontBo.getAllFont().get(position));
		return fontView;
	}

}
