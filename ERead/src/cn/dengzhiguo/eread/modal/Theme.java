package cn.dengzhiguo.eread.modal;

public class Theme {

	private int backColor;
	private int fontColor;
	public int getBackColor() {
		return backColor;
	}
	public void setBackColor(int backColor) {
		this.backColor = backColor;
	}
	public int getFontColor() {
		return fontColor;
	}
	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}
	public Theme(int backColor, int fontColor) {
		super();
		this.backColor = backColor;
		this.fontColor = fontColor;
	}
	public Theme(){
		
	}
}
