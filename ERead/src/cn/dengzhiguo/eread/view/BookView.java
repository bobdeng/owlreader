package cn.dengzhiguo.eread.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.db.Book;
@EViewGroup(R.layout.view_book)
public class BookView extends LinearLayout {

	public BookView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@ViewById(R.id.txtPage)
	TextView txtPage;
	@ViewById(R.id.txtTitle)
	TextView txtTitle;
	public void bind(Book book){
		txtTitle.setText(book.getName());
		txtPage.setText(book.getLastreadpage()+"/"+book.getPage());
	}

}
