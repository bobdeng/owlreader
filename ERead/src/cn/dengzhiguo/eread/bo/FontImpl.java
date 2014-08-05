package cn.dengzhiguo.eread.bo;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.graphics.Typeface;
@EBean
public class FontImpl implements IFont {

	@RootContext
	Context context;
	
	private static List<Typeface> fonts=new ArrayList<Typeface>();
	
	@AfterInject
	public void initFonts(){
		if(fonts.size()==0){
			fonts.add(Typeface.DEFAULT);
			fonts.add(Typeface.SANS_SERIF);
			fonts.add(Typeface.SERIF);
			fonts.add(Typeface.MONOSPACE);
			fonts.add(Typeface.createFromAsset(context.getAssets(), "times.ttf"));
			fonts.add(Typeface.createFromAsset(context.getAssets(), "Arial.ttf"));
			fonts.add(Typeface.createFromAsset(context.getAssets(), "Courier New.ttf"));
			fonts.add(Typeface.createFromAsset(context.getAssets(), "Courier New Bold.ttf"));
		}
	}
	
	@Override
	public Typeface getFont(int type) {
		if(type<0 || type>fonts.size()-1) return fonts.get(0);
		return fonts.get(type);
	}

	@Override
	public List<Typeface> getAllFont() {
		// TODO Auto-generated method stub
		return fonts;
	}

}
