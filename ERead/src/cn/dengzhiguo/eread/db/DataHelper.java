package cn.dengzhiguo.eread.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

public class DataHelper extends OrmLiteSqliteOpenHelper {

	public DataHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, "eread.db", factory, 5);
		// TODO Auto-generated constructor stub
	}
	public DataHelper(Context context) {
		super(context, "eread.db", null, 5);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(connectionSource, Book.class);
			TableUtils.createTable(connectionSource, Newword.class);
			TableUtils.createTable(connectionSource, Highlight.class);
			TableUtils.createTable(connectionSource, Pin.class);
		} catch (Exception e) {
			Log.e(DataHelper.class.getName(), "创建数据库失败", e);
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldversion,
			int newversion) {
		if(oldversion==1){
			try {
				TableUtils.dropTable(connectionSource, Newword.class, true);
				TableUtils.createTable(connectionSource, Newword.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(oldversion<=3){
			try {
				TableUtils.dropTable(connectionSource, Book.class, true);
				TableUtils.createTable(connectionSource, Book.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(oldversion==2){
			try {
				connectionSource.getReadWriteConnection().executeStatement("alter table t_newword add column times", DatabaseConnection.DEFAULT_RESULT_FLAGS);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(oldversion<=4){
			try {
				TableUtils.createTable(connectionSource, Highlight.class);
				TableUtils.createTable(connectionSource, Pin.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
		
	}

	

}
