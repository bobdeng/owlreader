package cn.dengzhiguo.eread.bo;

import java.io.File;
import java.util.List;

import cn.dengzhiguo.eread.db.Book;

public interface IBook {

	public boolean initBooks() throws Exception;
	public List<Book> getAll();
	public boolean addBook(File file,String name);
	public void saveBook(Book book);
	public void deleteBook(Book book);
}
