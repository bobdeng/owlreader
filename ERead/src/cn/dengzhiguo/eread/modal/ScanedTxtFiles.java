package cn.dengzhiguo.eread.modal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanedTxtFiles {

	private static ScanedTxtFiles instance;

	private List<File> files=new ArrayList<File>();
	private ScanedTxtFiles(){
		
	}
	public static ScanedTxtFiles getInstance() {
		if(instance==null)
			instance=new ScanedTxtFiles();
		return instance;
	}
	public void addFile(File file){
		files.add(file);
	}
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	
}
