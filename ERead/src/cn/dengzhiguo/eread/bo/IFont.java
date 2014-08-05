package cn.dengzhiguo.eread.bo;

import java.util.List;

import android.graphics.Typeface;

public interface IFont {

	public Typeface getFont(int type);
	public List<Typeface> getAllFont();
}
