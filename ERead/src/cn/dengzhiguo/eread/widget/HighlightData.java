package cn.dengzhiguo.eread.widget;

import mobi.zhangying.cyclops.Entity;
import cn.dengzhiguo.eread.db.Highlight;
import android.os.Parcel;
import android.os.Parcelable;
@Entity
public class HighlightData implements Parcelable{

	private int id;
	private int fromChar;
	private int endChar;
	public int getFromChar() {
		return fromChar;
	}
	public void setFromChar(int fromChar) {
		this.fromChar = fromChar;
	}
	public int getEndChar() {
		return endChar;
	}
	public void setEndChar(int endChar) {
		this.endChar = endChar;
	}
	public HighlightData(){
		
	}
	public HighlightData(int fromChar, int endChar) {
		super();
		this.fromChar = fromChar;
		this.endChar = endChar;
		this.id=-1;
	}
	@Override
	public String toString() {
		return this.fromChar+"-"+this.endChar;
	}
	public boolean superposition(HighlightData other){
		return (this.fromChar>=other.fromChar && this.fromChar<=other.endChar)
				||(this.endChar>=other.fromChar && this.endChar<=other.endChar);
	}
	public void combin(HighlightData other){
		this.fromChar=this.fromChar<=other.fromChar?this.fromChar:other.fromChar;
		this.endChar=this.endChar>=other.endChar?this.endChar:other.endChar;
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
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeInt(id);
		parcel.writeInt(fromChar);
		parcel.writeInt(endChar);
		
	}
	public static final Parcelable.Creator<HighlightData> CREATOR = new Parcelable.Creator<HighlightData>() {
		public HighlightData createFromParcel(Parcel in) {
			return new HighlightData(in);
		}

		public HighlightData[] newArray(int size) {
			return new HighlightData[size];
		}
	};
	private HighlightData(Parcel in){
		this.id=in.readInt();
		this.fromChar=in.readInt();
		this.endChar=in.readInt();
	}
	public HighlightData(int id, int fromChar, int endChar) {
		super();
		this.id = id;
		this.fromChar = fromChar;
		this.endChar = endChar;
	}
	
	
}
