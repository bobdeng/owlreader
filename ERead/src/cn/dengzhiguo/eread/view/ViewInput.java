package cn.dengzhiguo.eread.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.dengzhiguo.eread.R;
@EViewGroup(R.layout.view_input)
public class ViewInput extends LinearLayout {
	@ViewById(R.id.edtInput)
	public EditText edtInput;
	
	public ViewInput(Context context) {
		super(context);
		
	}

}
