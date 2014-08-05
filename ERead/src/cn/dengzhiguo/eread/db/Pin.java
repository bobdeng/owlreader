package cn.dengzhiguo.eread.db;

import mobi.zhangying.cyclops.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_pin")
@Entity
public class Pin implements Parcelable{
	@DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
	private int id;
	@DatabaseField
	private int bookid;
	@DatabaseField
	private int pos;
	@DatabaseField
	private String text;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
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
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(bookid);
		dest.writeInt(pos);
		dest.writeString(text);
		
	}
	public static final Parcelable.Creator<Pin> CREATOR = new Parcelable.Creator<Pin>() {
		public Pin createFromParcel(Parcel in) {
			return new Pin(in);
		}

		public Pin[] newArray(int size) {
			return new Pin[size];
		}
	};
	private Pin(Parcel in){
		this.id=in.readInt();
		this.bookid=in.readInt();
		this.pos=in.readInt();
		this.text=in.readString();
	}
	public Pin(){
		
	}
}
