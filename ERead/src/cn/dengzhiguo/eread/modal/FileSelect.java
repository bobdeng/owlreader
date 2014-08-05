package cn.dengzhiguo.eread.modal;

import java.io.File;

public class FileSelect {

	private File file;
	private boolean checked;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public FileSelect(File file, boolean checked) {
		super();
		this.file = file;
		this.checked = checked;
	}
	
}
