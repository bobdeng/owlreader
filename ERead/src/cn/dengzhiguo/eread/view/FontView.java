package cn.dengzhiguo.eread.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dengzhiguo.eread.R;
@EViewGroup(R.layout.view_font)
public class FontView extends LinearLayout {

	@ViewById(R.id.txtFont)
	TextView txtFont;

	public FontView(Context context) {
		super(context);
	}
	
	public void bind(Typeface font){
		txtFont.setTypeface(font);
	}

}
