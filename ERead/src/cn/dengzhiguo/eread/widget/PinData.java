package cn.dengzhiguo.eread.widget;

import mobi.zhangying.cyclops.Entity;
import android.os.Parcel;
import android.os.Parcelable;
@Entity
public class PinData implements Parcelable{

	private int id;
	private int pos;
	private String text;
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(pos);
		dest.writeString(text);
		
	}
	public static final Parcelable.Creator<PinData> CREATOR = new Parcelable.Creator<PinData>() {
		public PinData createFromParcel(Parcel in) {
			return new PinData(in);
		}

		public PinData[] newArray(int size) {
			return new PinData[size];
		}
	};
	
	private PinData(Parcel in){
		this.id=in.readInt();
		this.pos=in.readInt();
		this.text=in.readString();
	}
	public PinData(){
		this.id=-1;
	}
	public PinData(int id, int pos, String text) {
		super();
		this.id = id;
		this.pos = pos;
		this.text = text;
	}
	
	
}
