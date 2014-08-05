package cn.dengzhiguo.eread.bo;

import java.sql.SQLException;
import java.util.List;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import cn.dengzhiguo.eread.db.DataHelper;
import cn.dengzhiguo.eread.db.Newword;

import com.j256.ormlite.dao.RuntimeExceptionDao;
@EBean
public class NewwordImpl implements INewword {
	@RootContext
	Context context;
	@Bean(FileUtil.class)
	IFile fileUtil;
	@OrmLiteDao(helper = DataHelper.class, model = Newword.class)
	RuntimeExceptionDao<Newword, String> newwordDao;
	@Override
	public Newword findNewword(String word) {
		return newwordDao.queryForId(word);
	}

	@Override
	public void addNewword(Newword newword) {
		newwordDao.createOrUpdate(newword);
	}

	@Override
	public List<Newword> getAll() {
		try {
			return newwordDao.queryBuilder().orderBy("times", false).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(Newword word) {
		newwordDao.delete(word);
		
	}

	@Override
	public void saveNewword(Newword newword) {
		newwordDao.createOrUpdate(newword);
		
	}

}
