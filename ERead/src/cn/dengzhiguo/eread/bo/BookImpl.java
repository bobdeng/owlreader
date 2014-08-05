package cn.dengzhiguo.eread.bo;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.os.Environment;
import cn.dengzhiguo.eread.db.Book;
import cn.dengzhiguo.eread.db.DataHelper;

import com.j256.ormlite.dao.RuntimeExceptionDao;

@EBean
public class BookImpl implements IBook {
	@RootContext
	Context context;
	@Bean(FileUtil.class)
	IFile fileUtil;
	@OrmLiteDao(helper = DataHelper.class, model = Book.class)
	RuntimeExceptionDao<Book, Integer> bookDao;

	@Override
	public boolean initBooks() throws Exception {
		String[] books = context.getAssets().list("books");
		boolean changed = false;
		for (String book : books) {
			if (copyAsset(book)) {
				changed = true;
			}
		}
		if (scanNewbooks())
			changed = true;
		return changed;
	}

	private boolean scanNewbooks() {
		File path = new File(Environment.getExternalStorageDirectory(), "eread");
		boolean changed = false;
		if (path.exists()) {
			File[] books = path.listFiles(new FileFilter() {

				@Override
				public boolean accept(File file) {
					return file.getName().endsWith(".txt");
				}

			});

			for (File book : books) {
				if (addBook(book,
						book.getName()
								.substring(0, book.getName().length() - 4))) {
					changed = true;
				}
			}
		}
		return changed;
	}

	private boolean copyAsset(String filename) throws Exception {
		if (!fileUtil.isFileExist(filename)) {
			InputStream input = context.getAssets().open("books/" + filename);
			byte[] data = new byte[input.available()];
			input.read(data);
			File file = fileUtil.writeFile(data, filename);
			return this.addBook(file, filename.substring(0,filename.length()-4));
		}
		return false;
	}

	@Override
	public List<Book> getAll() {
		return bookDao.queryForAll();
	}

	@Override
	public boolean addBook(File file, String name) {
		if (bookDao.queryForEq("name", name.replaceAll("'", "''")).size() == 0) {
			Book book = new Book();
			try {
				book.setFile(file.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			book.setLastread(0);
			book.setLastreadpage(0);
			book.setName(name);
			book.setPage(0);
			bookDao.create(book);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void saveBook(Book book) {
		bookDao.createOrUpdate(book);
	}

	@Override
	public void deleteBook(Book book) {
		bookDao.delete(book);
	}

}
