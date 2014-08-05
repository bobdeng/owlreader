package cn.dengzhiguo.eread.bo;

import java.sql.SQLException;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;

import cn.dengzhiguo.eread.db.DataHelper;
import cn.dengzhiguo.eread.db.Highlight;
import cn.dengzhiguo.eread.widget.HighlightData;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
@EBean
public class HighlightImpl implements IHighlight {
	@OrmLiteDao(helper = DataHelper.class, model = Highlight.class)
	RuntimeExceptionDao<Highlight, Integer> dao;
	
	@Override
	public void save(HighlightData data,int bookid) {
		Highlight obj=null;
		if(data.getId()<0){
			obj=new Highlight();
			obj.setBookid(bookid);
		}else{
			obj=dao.queryForId(data.getId());
		}
		if(obj!=null){
			obj.setFrom(data.getFromChar());
			obj.setTo(data.getEndChar());
			dao.createOrUpdate(obj);
		}
		
		
	}

	@Override
	public List<Highlight> find(int bookid, int from, int to) {
		QueryBuilder<Highlight,Integer> builder=dao.queryBuilder();
		Where where=builder.where();
		try {
			where.and(where.eq("bookid", bookid),where.or(where.between("from", from, to),where.between("to", from, to)));
			return builder.query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(HighlightData hd) {
		dao.deleteById(hd.getId());
	}

}
