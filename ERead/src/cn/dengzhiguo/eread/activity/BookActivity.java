package cn.dengzhiguo.eread.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mobi.zhangying.cyclops.MVCAction;
import mobi.zhangying.cyclops.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.activity.actions.IBookAction;
import cn.dengzhiguo.eread.activity.actions.IConfig;
import cn.dengzhiguo.eread.activity.actions.IHighlightAction;
import cn.dengzhiguo.eread.activity.actions.IPin;
import cn.dengzhiguo.eread.activity.actions.ITranslate;
import cn.dengzhiguo.eread.bo.FileUtil;
import cn.dengzhiguo.eread.bo.FontImpl;
import cn.dengzhiguo.eread.bo.IFile;
import cn.dengzhiguo.eread.bo.IFont;
import cn.dengzhiguo.eread.bo.ITheme;
import cn.dengzhiguo.eread.bo.ThemeImpl;
import cn.dengzhiguo.eread.db.Book;
import cn.dengzhiguo.eread.db.Highlight;
import cn.dengzhiguo.eread.db.Newword;
import cn.dengzhiguo.eread.db.Pin;
import cn.dengzhiguo.eread.modal.ConfigInfo;
import cn.dengzhiguo.eread.view.ViewInput;
import cn.dengzhiguo.eread.view.ViewInput_;
import cn.dengzhiguo.eread.widget.EBook;
import cn.dengzhiguo.eread.widget.HighlightData;
import cn.dengzhiguo.eread.widget.OnBookSetOver;
import cn.dengzhiguo.eread.widget.OnObjectEvent;
import cn.dengzhiguo.eread.widget.OnPage;
import cn.dengzhiguo.eread.widget.OnPaintUpdate;
import cn.dengzhiguo.eread.widget.OnTextSelectListener;
import cn.dengzhiguo.eread.widget.OnZoom;
import cn.dengzhiguo.eread.widget.PinData;

import com.umeng.analytics.MobclickAgent;

@EActivity(R.layout.activity_book)
public class BookActivity extends Activity {

	@ViewById(R.id.ebook)
	EBook ebook;
	@ViewById(R.id.txtWord)
	TextView txtWord;
	@ViewById(R.id.txtPhen)
	TextView txtPhen;
	@ViewById(R.id.txtPham)
	TextView txtPham;
	@ViewById(R.id.txtParts)
	TextView txtParts;
	@ViewById(R.id.imgVoiceEn)
	ImageView imgPlayen;
	@ViewById(R.id.imgVoiceAm)
	ImageView imgPlayam;
	@ViewById(R.id.btnHighlight)
	ImageView btnHighlight;
	@ViewById(R.id.btnPin)
	ImageView btnPin;
	@ViewById(R.id.layoutTranslateMask)
	FrameLayout layoutTransMask;
	@ViewById(R.id.layoutTranslate)
	LinearLayout layoutTrans;
	@ViewById(R.id.pgbWaiting)
	ProgressBar pgbWaiting;
	@ViewById(R.id.skbPage)
	SeekBar skbPage;

	@Bean(FileUtil.class)
	IFile fileUtil;
	@Bean(FontImpl.class)
	IFont fontBo;
	@Bean(ThemeImpl.class)
	ITheme themeBo;
	@MVCAction
	IBookAction bookBo;
	@MVCAction
	IConfig configBo;
	@MVCAction
	IHighlightAction highlightBo;
	@MVCAction
	IPin pinBo;
	@MVCAction
	ITranslate transBo;

	
	Book book = null;
	private long lastTurnPage=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		book = (Book) this.getIntent().getParcelableExtra("book");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	@SuppressLint("DefaultLocale")
	@AfterViews
	public void afterView() {
		configBo.readConfig();
		skbPage.setEnabled(false);
		ebook.setLastRead(book.getLastread());
		ebook.setPinBitmap(BitmapFactory.decodeResource(this.getResources(),
				R.drawable.pin));
		ebook.setOnPaintUpdate(new OnPaintUpdate() {

			@Override
			public void update(HighlightData data) {
				highlightBo.saveHighlight(book.getId(), data);
			}
		});
		ebook.setTextSelectListener(new OnTextSelectListener() {

			@Override
			public void onSelected(String txt, boolean up, boolean left, int x,
					int y, int lineHeight) {
				transBo.translate(txt.toLowerCase());
				showTranslate(txt.toLowerCase(), up, left, x, y, lineHeight);
			}
		});
		ebook.setOnBookSetOver(new OnBookSetOver() {

			@Override
			public void over(int page, int curPage) {
				book.setLastreadpage(curPage);
				book.setPage(page);
				bookBo.saveBook(book);
				skbPage.setMax(page);
				skbPage.setProgress(curPage);

			}
		});
		ebook.setOnZoom(new OnZoom() {
			@Override
			public void onZoom(int fontSize) {
				configBo.saveConfig(fontSize, -1, -1);
			}
		});
		ebook.setOnPage(new OnPage() {

			@Override
			public void pageChange(int curPage, int page, int lastRead,
					int pageEnd) {
				book.setLastreadpage(curPage + 1);
				book.setPage(page);
				book.setLastread(lastRead);
				bookBo.saveBook(book);
				skbPage.setProgress(curPage);
				skbPage.setMax(page);
				highlightBo.listHighlight(book.getId(), lastRead-1, pageEnd);
				pinBo.listPin(book.getId(), lastRead - 1, pageEnd);
				if(System.currentTimeMillis()-lastTurnPage<1000)
				{
					skbPage.setEnabled(true);
				}else
				{
					skbPage.setEnabled(false);
				}
				lastTurnPage=System.currentTimeMillis();
			}
		});
		skbPage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				ebook.setScrolling(false);

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				ebook.setScrolling(true);

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int progress,
					boolean arg2) {
				ebook.pageto(progress);

			}
		});
		setObjectEvent();
	}

	private Object selectedObject = null;

	private void setObjectEvent() {
		ebook.setOnObjectEvent(new OnObjectEvent() {

			@Override
			public void onPinLongPress(PinData pin) {
				selectedObject = pin;
				showSetupMenu();
			}

			@Override
			public void onPinClick(PinData pin) {
				showInputPin(pin);
			}

			@Override
			public void onHighlightLongPress(HighlightData data) {
				selectedObject = data;
				showSetupMenu();
			}

			@Override
			public void onPinAdd(PinData pin) {
				showInputPin(pin);
				btnPinClick();
			}

		});
	}

	private void showInputPin(final PinData pin) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("备注");
		final ViewInput view = ViewInput_.build(this);
		builder.setView(view);
		if (pin.getText() != null) {
			view.edtInput.setText(pin.getText());
		}
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				savePin(pin, view.edtInput.getEditableText().toString());
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();
	}

	protected void savePin(PinData pin, String string) {
		pin.setText(string);
		pinBo.savePin(pin, book.getId());
	}

	private void showSetupMenu() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("操作");
		builder.setItems(new String[] { "删除" },
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item) {
						switch (item) {
						case 0:
							deleteObject();
							break;
						default:
							break;
						}

					}
				});
		builder.show();
	}

	private void deleteObject() {
		if (selectedObject != null) {
			if (selectedObject instanceof PinData) {
				ebook.deletePin((PinData) selectedObject);
				pinBo.deletePin( (PinData) selectedObject);
			}
			if (selectedObject instanceof HighlightData) {
				ebook.deleteHighlight((HighlightData) selectedObject);
				highlightBo.deleteHighlight((HighlightData) selectedObject);
			}
		}
	}

	private void showTranslate(String word, boolean up, boolean left, int x,
			int y, int lineHeight) {
		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layoutTrans
				.getLayoutParams();
		if (up) {
			lp.bottomMargin = 0;
			lp.topMargin = y;
			lp.gravity = Gravity.TOP;
		} else {
			lp.topMargin = 0;
			lp.bottomMargin = ebook.getHeight() - y + lineHeight;
			lp.gravity = Gravity.BOTTOM;
		}

		layoutTransMask.setVisibility(View.VISIBLE);
		txtWord.setText(word);
		txtPhen.setText("");
		txtPham.setText("");
		txtParts.setText("");
		pgbWaiting.setVisibility(View.VISIBLE);

	}

	@Response
	public void translateResult(Newword newword) {
		pgbWaiting.setVisibility(View.GONE);
		if (newword != null) {
			if (newword.getEn() != null) {
				txtPhen.setText(String.format("英：[%s]", newword.getEn()));
			}
			if (newword.getAm() != null) {
				txtPham.setText(String.format("美：[%s]", newword.getAm()));
			}
			mNewword = newword;
			txtParts.setText(newword.getParts());

		} else {
			Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
		}

	}

	private Newword mNewword;

	@Click(R.id.layoutEn)
	public void clickVoiceEn() {
		if (mNewword == null)
			return;
		String voice = (mNewword.getVoiceEn() == null || "".equals(mNewword
				.getVoiceEn())) ? mNewword.getVoiceTts() : mNewword
				.getVoiceEn();
		this.playvoice(voice);
	}
	
	private void playvoice(String url) {
		if (url != null)
			transBo.playvoice(url);
		else
			Toast.makeText(this, "没有语音", Toast.LENGTH_SHORT).show();

	}

	@Click(R.id.layoutAm)
	public void clickVoiceAm() {
		if (mNewword == null)
			return;
		String voice = (mNewword.getVoiceAm() == null || "".equals(mNewword
				.getVoiceAm())) ? mNewword.getVoiceTts() : mNewword
				.getVoiceAm();
		this.playvoice(voice);
	}

	@Click(R.id.layoutTitle)
	public void layoutTitleClick() {
	}

	@Click(R.id.txtParts)
	public void partsClick() {
	}

	@Click(R.id.btnHighlight)
	public void btnHighlightClick() {
		ebook.setPainting(!ebook.isPainting());
		btnHighlight.setColorFilter(ebook.isPainting() ? 0x0 : 0xa0000000);
	}

	@Click(R.id.btnPin)
	public void btnPinClick() {
		ebook.setPining(!ebook.isPining());
		btnPin.setColorFilter(ebook.isPining() ? 0x0 : 0xa0000000);
	}

	@Click(R.id.layoutTranslateMask)
	public void bookClick() {
		layoutTransMask.setVisibility(View.GONE);
	}

	@Response
	public void  readConfigResult(ConfigInfo config) {
		try {
			ebook.setFontSize(config.getFontSize());
			ebook.setFontType(fontBo.getFont(config.getFontType()));
			ebook.setBackcolor(themeBo.getAllTheme().get(config.getTheme())
					.getBackColor());
			ebook.setFontcolor(themeBo.getAllTheme().get(config.getTheme())
					.getFontColor());
			ebook.setTxtFile(new File(book.getFile()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Response
	public void listHighlightResult(ArrayList<Highlight> rlt) {
		List<HighlightData> listResult = new ArrayList<HighlightData>();
		for (Highlight hl : rlt) {
			listResult.add(new HighlightData(hl.getId(), hl.getFrom(), hl
					.getTo()));
		}
		ebook.setHighLights(listResult);
	}

	@Response
	public void listPinResult(ArrayList<Pin> rlt) {
		List<PinData> listResult = new ArrayList<PinData>();
		for (Pin p : rlt) {
			listResult.add(new PinData(p.getId(), p.getPos(), p.getText()));
		}
		ebook.setListPin(listResult);
	}

	@Override
	protected void onResume() {

		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		AudioManager audioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int curVolumn = audioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		int maxVolumn = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {

			audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, curVolumn
					- maxVolumn / 10, AudioManager.FLAG_PLAY_SOUND);

			return false;
		}
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

			audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, curVolumn
					+ maxVolumn / 10, AudioManager.FLAG_PLAY_SOUND);

			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
