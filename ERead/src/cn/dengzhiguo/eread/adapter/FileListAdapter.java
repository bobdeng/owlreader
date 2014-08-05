package cn.dengzhiguo.eread.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.dengzhiguo.eread.modal.FileSelect;
import cn.dengzhiguo.eread.view.FileView;
import cn.dengzhiguo.eread.view.FileView_;
@EBean
public class FileListAdapter extends BaseAdapter {

	private List<FileSelect> files=new ArrayList<FileSelect>();
	@RootContext
	Context context;
	@Override
	public int getCount() {
		return files.size();
	}

	@Override
	public Object getItem(int arg0) {
		return files.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		FileView view=(FileView)convertView;
		if(view==null){
			view=FileView_.build(context);
		}
		view.bind(files.get(position));
		return view;
	}
	public void removeItem(FileSelect file){
		files.remove(file);
		this.notifyDataSetChanged();
	}
	public void setFiles(List<File> files) {
		this.files=new ArrayList<FileSelect>();
		for(File file:files){
			this.files.add(new FileSelect(file,false));
		}
		this.notifyDataSetChanged();
	}

}
