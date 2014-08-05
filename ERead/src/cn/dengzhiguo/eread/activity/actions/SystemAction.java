package cn.dengzhiguo.eread.activity.actions;

import java.io.File;
import java.io.IOException;

import mobi.zhangying.cyclops.ActionCallback;
import mobi.zhangying.cyclops.ActionMethod;
import mobi.zhangying.cyclops.ResponseMethod;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import cn.dengzhiguo.eread.bo.BookImpl;
import cn.dengzhiguo.eread.bo.IBook;
import cn.dengzhiguo.eread.modal.ScanedTxtFiles;

@EBean
public abstract class SystemAction {
	@RootContext
	Context context;
	@Bean(BookImpl.class)
	IBook book;
	@ActionMethod
	public void initBooks() {
		try {
			scanPath(Environment.getExternalStorageDirectory(), 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(book.initBooks()){
				findNewBook();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void scanPath(File path, int level) throws IOException {
		File[] files = path.listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".txt")) {
				ScanedTxtFiles.getInstance().addFile(file);
			}
		}
	}

	@ResponseMethod
	public abstract void findNewBook();
}
