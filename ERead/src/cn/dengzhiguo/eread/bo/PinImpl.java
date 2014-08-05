package cn.dengzhiguo.eread.bo;

import java.sql.SQLException;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;

import cn.dengzhiguo.eread.db.DataHelper;
import cn.dengzhiguo.eread.db.Pin;
import cn.dengzhiguo.eread.widget.PinData;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

@EBean
public class PinImpl implements IPin{
	@OrmLiteDao(helper = DataHelper.class, model = Pin.class)
	RuntimeExceptionDao<Pin, Integer> dao;

	@Override
	public List<Pin> find(int bookid, int from, int to) {
		QueryBuilder<Pin,Integer> builder=dao.queryBuilder();
		Where where=builder.where();
		try {
			where.and(where.eq("bookid", bookid),where.between("pos", from, to));
			return builder.query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(PinData data, int bookid) {
		Pin obj=null;
		if(data.getId()<0){
			obj=new Pin();
			obj.setBookid(bookid);
		}else{
			obj=dao.queryForId(data.getId());
		}
		if(obj!=null){
			obj.setPos(data.getPos());
			obj.setText(data.getText());
			dao.createOrUpdate(obj);
		}
		
	}

	@Override
	public void delete(PinData data) {
		dao.deleteById(data.getId());	
	}

}
