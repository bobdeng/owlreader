package cn.dengzhiguo.eread.activity.actions;

import java.io.File;
import java.util.ArrayList;

import mobi.zhangying.cyclops.ActionMethod;
import mobi.zhangying.cyclops.ResponseMethod;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import cn.dengzhiguo.eread.bo.BookImpl;
import cn.dengzhiguo.eread.bo.IBook;
import cn.dengzhiguo.eread.db.Book;

@EBean
public class BookAction implements IBookAction{

	@Bean(BookImpl.class)
	IBook bookBo;
	
	@Override
	@ActionMethod
	public void listBook() {
		ArrayList<Book> result=new ArrayList<Book>();
		result.addAll(bookBo.getAll());
		listBookResult(result);
	}
	@ResponseMethod
	public void listBookResult(ArrayList<Book> result){
		
	}
	@ResponseMethod
	public void bookUpdate(){}
	@Override
	@ActionMethod
	public void deleteBook(Book book) {
		if(book!=null){
			bookBo.deleteBook(book);
			File file=new File(book.getFile());
			if(file.exists()){
				file.delete();
			}
			bookUpdate();
		}
	}

	@Override
	@ActionMethod
	public void saveBook(Book book) {
		bookBo.saveBook(book);
		bookUpdate();
	}

	
}
