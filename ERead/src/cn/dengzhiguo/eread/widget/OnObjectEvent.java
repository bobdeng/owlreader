package cn.dengzhiguo.eread.widget;

public interface OnObjectEvent {

	public void onHighlightLongPress(HighlightData data);
	public void onPinLongPress(PinData pin);
	public void onPinClick(PinData pin);
	public void onPinAdd(PinData pin);
}
