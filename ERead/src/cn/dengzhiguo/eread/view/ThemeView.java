package cn.dengzhiguo.eread.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.modal.Theme;
@EViewGroup(R.layout.view_them)
public class ThemeView extends LinearLayout {

	@ViewById(R.id.txtTheme)
	TextView txtTheme;

	public ThemeView(Context context) {
		super(context);
	}
	
	public void bind(Theme theme){
		txtTheme.setTextColor(theme.getFontColor());
		txtTheme.setBackgroundColor(theme.getBackColor());
	}

}
