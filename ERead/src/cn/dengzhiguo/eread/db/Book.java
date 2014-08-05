package cn.dengzhiguo.eread.db;

import mobi.zhangying.cyclops.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_bookstore")
@Entity
public class Book implements Parcelable {
	@DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
	private int id;
	@DatabaseField
	private String file;
	@DatabaseField
	private String name;
	@DatabaseField
	private int lastread;
	@DatabaseField
	private int lastreadpage;
	@DatabaseField
	private int page;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getLastread() {
		return lastread;
	}

	public void setLastread(int lastread) {
		this.lastread = lastread;
	}

	public int getLastreadpage() {
		return lastreadpage;
	}

	public void setLastreadpage(int lastreadpage) {
		this.lastreadpage = lastreadpage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(this.id);
		out.writeInt(this.lastread);
		out.writeInt(this.lastreadpage);
		out.writeInt(this.page);
		out.writeString(this.name);
		out.writeString(this.file);

	}

	public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
		public Book createFromParcel(Parcel in) {
			return new Book(in);
		}

		public Book[] newArray(int size) {
			return new Book[size];
		}
	};
	private Book(Parcel in){
		this.id=in.readInt();
		this.lastread=in.readInt();
		this.lastreadpage=in.readInt();
		this.page=in.readInt();
		this.name=in.readString();
		this.file=in.readString();
	}
	public Book(){
		
	}

}
