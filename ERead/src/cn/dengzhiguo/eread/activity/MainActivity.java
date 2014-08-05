package cn.dengzhiguo.eread.activity;

import java.util.ArrayList;

import mobi.zhangying.cyclops.MVCAction;
import mobi.zhangying.cyclops.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.activity.actions.IBookAction;
import cn.dengzhiguo.eread.activity.actions.ISystem;
import cn.dengzhiguo.eread.adapter.BookListAdapter;
import cn.dengzhiguo.eread.db.Book;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@ViewById(R.id.listBook)
	ListView listBook;
	@Bean
	BookListAdapter bookListAdpater;
	@MVCAction
	IBookAction bookActions;
	@MVCAction
	ISystem systemAction;
	
	Book selected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		systemAction.initBooks();
	}

	@AfterViews
	public void init() {
		listBook.setAdapter(bookListAdpater);
		listBook
				.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

					@Override
					public void onCreateContextMenu(ContextMenu menu,
							final View v, ContextMenuInfo menuInfo) {
						menu.add("删除").setOnMenuItemClickListener(
								new MenuItem.OnMenuItemClickListener() {

									@Override
									public boolean onMenuItemClick(MenuItem item) {
										deleteSelected();
										return true;
									}
								});

					}
				});
	}

	@ItemClick(R.id.listBook)
	public void openBook(Object obj,View view) {
		Book book = (Book) obj;
		Intent intent = new Intent(this, BookActivity_.class);
		intent.putExtra("book", book);
		this.startActivity(intent);
	}
	@Click(R.id.btnShare)
	public void share() {
		send("推荐个英语阅读器，可以在阅读时点击显示翻译并加入生词本，安卓下载地址： http://www.dwz.cn/oPMe8");
	}
	@ItemLongClick(R.id.listBook)
	public void seleteItem(Book book){
		this.selected=book;
		listBook.showContextMenu();
	}
	@Click(R.id.btnSupport)
	public void doSupport() {
	}
	@Click(R.id.btnNewword)
	public void openNewwords() {
		NewwordActivity_.intent(this).start();
	}
	@Click(R.id.btnMore)
	public void btnMoreClick() {
		showSetupMenu();
	}

	private void send(String content) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		startActivity(Intent.createChooser(intent, "分享到"));
	}

	@Response
	public void listBookResult(ArrayList<Book> result) {
		bookListAdpater.setBooks(result);
	}

	@Response
	public void bookUpdate() {
		bookActions.listBook();
	}
	private void deleteSelected(){
		bookActions.deleteBook(selected);
	}
	private void showSetupMenu(){
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setTitle("更多");
		builder.setItems(new String[]{"设置","帮助"}, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int item) {
				switch(item){
				case 0:
					SetupActivity_.intent(MainActivity.this).start();
					break;
				case 1:
					ScanFileActivity_.intent(MainActivity.this).start();
					break;
				default:
					break;
				}
				
			}
		});
		builder.show();
	}
	@Override
	protected void onResume() {

		super.onResume();
		MobclickAgent.onResume(this);
		bookActions.listBook();
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);

	}
	@Response
	public void findNewBook(){
		System.out.println("new book");
		bookActions.listBook();
	}
}
