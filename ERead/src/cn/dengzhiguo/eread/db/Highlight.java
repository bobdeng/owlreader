package cn.dengzhiguo.eread.db;

import mobi.zhangying.cyclops.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "t_highlight")
@Entity
public class Highlight implements Parcelable{

	@DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
	private int id;
	@DatabaseField
	private int from;
	@DatabaseField
	private int to;
	@DatabaseField
	private int bookid;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		parcel.writeInt(id);
		parcel.writeInt(from);
		parcel.writeInt(to);
		parcel.writeInt(bookid);
		
	}
	public static final Parcelable.Creator<Highlight> CREATOR = new Parcelable.Creator<Highlight>() {
		public Highlight createFromParcel(Parcel in) {
			return new Highlight(in);
		}

		public Highlight[] newArray(int size) {
			return new Highlight[size];
		}
	};
	private Highlight(Parcel in){
		this.id=in.readInt();
		this.from=in.readInt();
		this.to=in.readInt();
		this.bookid=in.readInt();
	}
	public Highlight(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	
}
