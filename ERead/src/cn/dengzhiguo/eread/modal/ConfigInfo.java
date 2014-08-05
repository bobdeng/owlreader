package cn.dengzhiguo.eread.modal;

import java.io.Serializable;

public class ConfigInfo implements Serializable{

	private int fontSize;
	private int fontType;
	private int theme;
	
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public int getFontType() {
		return fontType;
	}
	public void setFontType(int fontType) {
		this.fontType = fontType;
	}
	public int getTheme() {
		return theme;
	}
	public void setTheme(int theme) {
		this.theme = theme;
	}
	
	
}
