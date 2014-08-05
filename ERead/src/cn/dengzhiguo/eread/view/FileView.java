package cn.dengzhiguo.eread.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.modal.FileSelect;
@EViewGroup(R.layout.view_file)
public class FileView extends LinearLayout {
	@ViewById(R.id.txtFileName)
	TextView txtFileName;
	@ViewById(R.id.chkSelected)
	CheckBox chkSelected;

	public FileView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public void bind(FileSelect file){
		txtFileName.setText(file.getFile().getName());
		chkSelected.setSelected(file.isChecked());
	}

}
