package cn.dengzhiguo.eread.widget;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class EBook extends View {

	public EBook(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private float paddingLeft = 20;
	private float paddingRight = 20;
	private float linePadding = 20;
	private float lineMargin = 1.5f;
	private boolean scrolling = false;
	private List<List<String>> pages = new ArrayList<List<String>>();
	private File txtFile = null;
	List<String> page = new ArrayList<String>();
	StringBuffer line = new StringBuffer();
	float lineHeight = 0;
	int curPage = -1;
	float mMoveX = 0;
	float mMoveY = 0;
	boolean mPointerDown = false;
	int linesPerPage = 0;
	private OnTextSelectListener textSelectListener;
	private OnBookSetOver onBookSetOver;
	private OnPage onPage;
	private int fontSize;
	private int backcolor;
	private int fontcolor;
	private Typeface font = Typeface.DEFAULT;
	private VelocityTracker mVelocityTracker;
	private OnZoom onZoom;
	private boolean painting = false;
	private boolean pining=false;
	private Bitmap pinBitmap;
	private int paintingColor = 0xff7cfa00;
	private OnPaintUpdate onPaintUpdate;
	private OnObjectEvent onObjectEvent;
	private List<PinData> listPin=new ArrayList<PinData>();
	public boolean isPainting() {
		return painting;
	}

	public void setPainting(boolean painting) {
		this.painting = painting;
		this.invalidate();
	}

	public boolean isPining() {
		return pining;
	}

	public Bitmap getPinBitmap() {
		return pinBitmap;
	}

	public void setPinBitmap(Bitmap pinBitmap) {
		this.pinBitmap = pinBitmap;
	}

	public void setPining(boolean pining) {
		this.pining = pining;
		invalidate();
	}

	public OnPaintUpdate getOnPaintUpdate() {
		return onPaintUpdate;
	}

	public void setOnPaintUpdate(OnPaintUpdate onPaintUpdate) {
		this.onPaintUpdate = onPaintUpdate;
	}

	public OnTextSelectListener getTextSelectListener() {
		return textSelectListener;
	}

	public OnZoom getOnZoom() {
		return onZoom;
	}

	public void setOnZoom(OnZoom onZoom) {
		this.onZoom = onZoom;
	}

	private int lastRead;

	public int getLastRead() {
		return lastRead;
	}

	public void setLastRead(int lastRead) {
		this.lastRead = lastRead;
	}

	public void setTextSelectListener(OnTextSelectListener textSelectListener) {
		this.textSelectListener = textSelectListener;
	}

	private void newLine(int lastBlank) {
		if (line.length() == 0)
			return;

		page.add(line.substring(0, lastBlank == 0 ? line.length() : lastBlank));
		line = line.delete(0, lastBlank == 0 ? line.length() : lastBlank);
		if (page.size() >= linesPerPage) {
			newPage();
		}
	}

	private void newPage() {
		pages.add(page);
		page = new ArrayList<String>(linesPerPage);
	}

	private void getLineHeight(Paint paint) {
		Rect bounds = new Rect();
		paint.getTextBounds("Ay", 0, 2, bounds);
		lineHeight = bounds.height() * lineMargin;
		linesPerPage = (int) (this.getHeight() / lineHeight);
	}

	long start = 0;
	float charWidth = 0;
	int minLenOnLine = 0;
	int mesureTime = 0;
	float[] charWidths = new float[127];

	private boolean isNormalChar(char c) {
		return (c > 'a' && c < 'z') || (c > 'A' && c < 'Z');
	}

	public void readBook() throws Exception {
		InputStream input = new FileInputStream(txtFile);
		pages.clear();
		start = System.currentTimeMillis();
		byte[] buffer = new byte[10240];
		int r = 0;
		Paint paint = this.getTextPaint();
		for (int i = 10; i <= 0x7a; i++) {
			charWidths[i] = paint.measureText(String.valueOf((char) i));
		}
		getLineHeight(paint);
		int lastBlank = 0;
		int lineWidth = 0;
		int lastBlankLineWidth = 0;
		float maxLineWidth = this.getWidth() - paddingLeft - paddingRight;
		while ((r = input.read(buffer)) > 0) {
			for (int i = 0; i < r; i++) {
				char c = (char) buffer[i];
				if (c > 127)
					continue;

				if (c == '\n') {
					if (i > 0) {
						if (lineWidth >= maxLineWidth) {
							newLine(lastBlank);
							lastBlank = 0;
							lineWidth = lineWidth - lastBlankLineWidth;
							lastBlankLineWidth = 0;
						} else {
							newLine(0);
							lastBlank = 0;
							lineWidth = 0;
							lastBlankLineWidth = 0;
						}
					}
				} else {
					line.append(c);
					lineWidth += getCharWidth(c);
					if (lineWidth >= maxLineWidth) {
						if (lastBlank == 0) {
							lastBlankLineWidth = lineWidth
									- (int) this.getCharWidth(line.charAt(line
											.length() - 1));
						}
						newLine(lastBlank == 0 ? line.length() - 1 : lastBlank);

						lineWidth = lineWidth - lastBlankLineWidth;
						lastBlankLineWidth = 0;
						lastBlank = 0;

					} else {
						if (c == ' ') {
							lastBlank = line.length();
							lastBlankLineWidth = lineWidth;
						}

					}
				}

			}
		}

		newLine(0);
		newPage();
		gotoLastRead();
		input.close();
		if (onBookSetOver != null)
			onBookSetOver.over(pages.size(), curPage);
		onChangePage();
	}

	private void gotoLastRead() {
		int len = 0;
		for (int i = 0; i < pages.size(); i++) {
			for (String line : pages.get(i)) {
				len += line.length();
				if (len > this.lastRead) {
					curPage = i;
					return;
				}
			}
		}
	}

	public EBook(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public EBook(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	Handler handler = new Handler();

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawBack(canvas);
		paintHighlight(canvas);
		paintPin(canvas);
		paintText(canvas);
		if (this.txtFile != null && (pages == null || pages.size() == 0)) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						readBook();
						handler.post(new Runnable() {

							@Override
							public void run() {
								invalidate();
							}
						});

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();

		}
	}
	private void paintPin(Canvas canvas){
		if (pages.size() > 0 && curPage >= 0) {
			int startWord = this.getStartWord();
			for (int i = 0; i < pages.get(curPage).size(); i++) {
				String line=pages.get(curPage).get(i);
				for(int j=0;j<line.length();j++){
					paintPin(canvas,startWord,i,line.substring(0,j));
					startWord++;
				}
			}
		}
	}
	private void paintPin(Canvas canvas,int pos,int line,String xpos){
		Paint txtPaint=this.getPinTextPaint();
		for(PinData pin:listPin){
			if(pin.getPos()==pos){
				int left= (int)this.getStringWidth(xpos);
				int top=(int)(line*lineHeight);
				canvas.drawBitmap(pinBitmap,new Rect(0,0,pinBitmap.getWidth(),pinBitmap.getHeight()),
						new Rect((int) (left + mMoveX),top,(int)(left+32*this.getContext().getResources().getDisplayMetrics().density + mMoveX)
								,(int)(top+32*this.getContext().getResources().getDisplayMetrics().density)), new Paint());
				if(pin.getText()!=null){
					canvas.drawText(pin.getText(), (int) (left + mMoveX+pinBitmap.getWidth()), top+lineHeight/2, txtPaint);
				}
			}
		}
	}
	private void paintHighlight(Canvas canvas) {
		if (pages.size() > 0 && curPage >= 0) {
			int startWord = this.getStartWord();
			// System.out.println("start:"+startWord+",page:"+curPage);
			for (int i = 0; i < pages.get(curPage).size(); i++) {
				String line = pages.get(curPage).get(i);
				paintLineHighlight(canvas, i, pages.get(curPage).get(i),
						startWord);
				startWord += line.length();
			}
		}
	}

	private void paintLineHighlight(Canvas canvas, int line, String lineString,
			int start) {

		if (mCurPainting != null) {
			paintHighLight(mCurPainting, canvas, line, lineString, start);
		}
		if (highLights != null)
			for (HighlightData data : this.highLights) {
				paintHighLight(data, canvas, line, lineString, start);
			}
	}

	private void paintHighLight(HighlightData data, Canvas canvas, int line,
			String lineString, int start) {
		// 超出范围
		if (start > data.getEndChar())
			return;
		if (start + lineString.length() < data.getFromChar())
			return;
		Paint paint = new Paint();
		paint.setColor(paintingColor);
		int beginChar = 0;
		int endChar = lineString.length();
		//计算开始位置
		if (start <= data.getFromChar()) {
			beginChar = data.getFromChar() - start;

		} 
		//计算结束位置
		if (start + lineString.length() > data.getEndChar()) {
			endChar = data.getEndChar() - start;
		} 

		float left = this.getStringWidth(lineString.substring(0, beginChar));
		float top = line * lineHeight+ linePadding;
		float height = lineHeight;
		float right = this.getStringWidth(lineString.substring(0, endChar));
		canvas.drawRect(left + mMoveX, top, right + mMoveX, top + height-linePadding, paint);
	}

	private void paintText(Canvas canvas) {
		if (pages == null || pages.size() == 0 || curPage < 0)
			return;
		paintPageText(canvas, curPage, mMoveX + paddingLeft);
		paintText(canvas, mMoveX + paddingLeft, pages.get(curPage), true);
		if (mMoveX < 0) {
			if (curPage < pages.size() - 1) {
				if (!scrolling)
					paintPageText(canvas, curPage + 1, this.getWidth() + mMoveX
							+ paddingLeft);
				paintText(canvas, this.getWidth() + mMoveX + paddingLeft,
						pages.get(curPage + 1), false);
				if (scrolling)
					paintPageText(canvas, curPage + 1, this.getWidth() + mMoveX
							+ paddingLeft);
			}

		}
		if (mMoveX > 0) {
			if (curPage >= 1) {
				if (!scrolling)
					paintPageText(canvas, curPage - 1, mMoveX - this.getWidth()
							+ paddingLeft);
				paintText(canvas, mMoveX - this.getWidth() + paddingLeft,
						pages.get(curPage - 1), false);
				if (scrolling)
					paintPageText(canvas, curPage - 1, mMoveX - this.getWidth()
							+ paddingLeft);
			}
		}
	}

	private void paintPageText(Canvas canvas, int page, float leftMove) {
		if (page >= 0 && pages != null) {
			Paint pagePaint = new Paint();
			pagePaint.setColor(fontcolor);
			pagePaint.setTextSize(10 * this.getContext().getResources()
					.getDisplayMetrics().density);
			pagePaint.setTypeface(Typeface.SANS_SERIF);
			pagePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
			Rect rectPage = new Rect();
			String pageText = (1 + page) + "/" + pages.size();
			pagePaint.getTextBounds(pageText, 0, pageText.length(), rectPage);
			canvas.drawText(pageText, (this.getWidth() - rectPage.width())
					+ leftMove - paddingLeft - 5, rectPage.height() + 5,
					pagePaint);
		}
	}

	private void drawBack(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(backcolor);
		canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
	}

	private Paint getTextPaint() {
		Paint paint = new Paint();
		int fc = fontcolor;
		paint.setColor(fc);
		paint.setTextSize(fontSize
				* this.getContext().getResources().getDisplayMetrics().density);
		paint.setTypeface(font);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		return paint;
	}
	private Paint getPinTextPaint() {
		Paint paint = new Paint();
		int fc = fontcolor;
		paint.setColor(fc);
		paint.setTextSize(fontSize
				* this.getContext().getResources().getDisplayMetrics().density/2);
		paint.setTypeface(font);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		return paint;
	}

	private void paintText(Canvas canvas, float left, List<String> line,
			boolean isCurPage) {
		float height = lineHeight;
		Paint textPaint = this.getTextPaint();
		for (int i = 0; i < line.size(); i++) {
//			if (i == selectedLine && isCurPage) {
//				Paint hilightPaint = new Paint();
//				hilightPaint.setColor(0xffFAE15A);
//				canvas.drawRect(new Rect(
//						(int) (selectedStartX + left - paddingLeft),
//						(int) (height - lineHeight + linePadding),
//						(int) (selectedEndx + left - paddingLeft),
//						(int) (height + linePadding)), hilightPaint);
//			}
			canvas.drawText(line.get(i), left, height, textPaint);

			height += lineHeight;
		}

	}

	private GestureDetector gestureDetector = new GestureDetector(
			this.getContext(), new GestureDetector.OnGestureListener() {

				@Override
				public boolean onSingleTapUp(MotionEvent event) {
					click(event);
					return false;
				}

				@Override
				public void onShowPress(MotionEvent arg0) {
				}

				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2,
						float x, float y) {
					if (e1 == null || e2 == null)
						return false;
					if (painting) {
						paintHighlight(e1, e2, x, y);
						return false;
					}
					if (Math.abs(x) > Math.abs(y)) {
						if (!mPointerDown) {
							mMoveX = (int) (e2.getAxisValue(MotionEvent.AXIS_X) - e1
									.getAxisValue(MotionEvent.AXIS_X));
						}
						EBook.this.invalidate();
					} else {
						// zoom
						if (mPointerDown) {
							if (e2.getPointerCount() == 2) {
								float nowLength = Math.abs(e2.getY(0)
										- e2.getY(1));
								if (mBeforeLenth > nowLength) {
									// zoom in
									fontSize = lastFontSize
											- (int) ((mBeforeLenth - nowLength) * 20 / getHeight());
								} else {
									// zoom out
									fontSize = lastFontSize
											+ (int) ((nowLength - mBeforeLenth) * 20 / getHeight());
								}
								getLineHeight(getTextPaint());
								invalidate();
							}
						}
					}
					return false;
				}

				@Override
				public void onLongPress(MotionEvent event) {
					longPress(event);
				}

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2, float x,
						float y) {
					return false;
				}

				@Override
				public boolean onDown(MotionEvent arg0) {
					return false;
				}
			});
	private int lastFontSize = 0;
	private float mBeforeLenth = 0;
	private List<HighlightData> highLights = new ArrayList<HighlightData>();
	private HighlightData mCurPainting = null;

	private void longPress(MotionEvent event){
		int words=getWord(event.getX(),event.getY());
		for(HighlightData hd:highLights){
			if(words>=hd.getFromChar()&& words<=hd.getEndChar()){
				if(onObjectEvent!=null){
					onObjectEvent.onHighlightLongPress(hd);
				}
				return;
			}
		}
		for(PinData pin:listPin){
			if(pin.getPos()>=words-3 && pin.getPos()<=words+3){
				if(onObjectEvent!=null){
					onObjectEvent.onPinLongPress(pin);
				}
				return;
			}
		}
	}
	private void paintHighlight(MotionEvent e1, MotionEvent e2, float x, float y) {
		int start = getWord(e1.getX(), e1.getY());
		int end = getWord(e2.getX(), e1.getY());
		int from = start < end ? start : end;
		int to = start > end ? start : end;
		if (mCurPainting == null) {
			mCurPainting = new HighlightData(from, to);
		} else {
			mCurPainting.setFromChar(from);
			mCurPainting.setEndChar(to);
		}
		invalidate();
	}

	private int getWord(float x, float y) {
		int startWord = this.getStartWord();
		int height = 0;
		for (String str : pages.get(curPage)) {
			height += lineHeight;
			if (height > y) {
				float width = paddingLeft;
				for (int i = 0; i < str.length(); i++) {
					char c = str.charAt(i);
					width += this.getCharWidth(c);
					if (width > x) {
						return startWord + i + 1;
					}
				}
				return startWord + str.length();
			}
			startWord += str.length();
		}
		return 0;
	}

	private int getStartWord() {
		int startWord = 0;
		for (int i = 0; i < this.curPage; i++) {
			for (String str : pages.get(i)) {
				startWord += str.length();
			}
		}
		return startWord;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_UP:
			if (!mPointerDown || mMoveY == 0) {
				doActionUp(event);
			}
			if (mPointerDown) {
				if (fontSize != lastFontSize) {
					if (this.onZoom != null)
						this.onZoom.onZoom(fontSize);
					try {
						this.readBook();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (mCurPainting != null) {
				addCurPainting();
				mCurPainting = null;
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mPointerDown = true;
			lastFontSize = fontSize;
			mBeforeLenth = Math.abs(event.getY(0) - event.getY(1));
			break;
		case MotionEvent.ACTION_DOWN:
			mMoveY = 0;
			mMoveX = 0;
			mPointerDown = false;
			break;
		}
		return true;
	}

	private void addCurPainting() {
		for (HighlightData data : this.highLights) {
			if (data.superposition(mCurPainting)) {

				data.combin(mCurPainting);
				if (onPaintUpdate != null) {
					onPaintUpdate.update(data);
				}
				// System.out.println("combin");
				return;
			}
		}
		this.highLights.add(mCurPainting);
		onPaintUpdate.update(mCurPainting);
	}

	private void chagePage() {
		this.highLights.clear();
		this.mCurPainting = null;
	}

	private void doActionUp(MotionEvent event) {
		if (mMoveX != 0) {
			mVelocityTracker.computeCurrentVelocity(1000);
			int duration = 500;
			if (Math.abs(mVelocityTracker.getXVelocity()) > 0) {
				duration = (int) ((this.getWidth() - Math.abs(mMoveX)) * 1000 / Math
						.abs(mVelocityTracker.getXVelocity()));
				if (duration > 500)
					duration = 500;
			}
			if (mMoveX < 0) {
				if (curPage == pages.size() - 1
						|| (Math.abs(mMoveX) < getWidth() / 4 && Math
								.abs(mVelocityTracker.getXVelocity()) < 1000)) {
					animationMove(mMoveX, 0, 500);
				} else {
					animationMove(mMoveX, 0 - this.getWidth(), duration);
				}
			} else {
				if (curPage == 0
						|| (Math.abs(mMoveX) < getWidth() / 4 && Math
								.abs(mVelocityTracker.getXVelocity()) < 1000)) {
					animationMove(mMoveX, 0, 500);
				} else {
					animationMove(mMoveX, this.getWidth(), duration);
				}
			}
		}
	}

	private int selectedLine = 0;
	private float selectedStartX = 0;
	private float selectedEndx = 0;

	private void clickPin(MotionEvent event){
		int clickWord=this.getWord(event.getX(), event.getY());
		PinData pin=new PinData();
		pin.setPos(clickWord);
		listPin.add(pin);
		//System.out.println("click new pin:"+pin.getPos());
		if(onObjectEvent!=null){
			onObjectEvent.onPinAdd(pin);
		}
		this.invalidate();
	}
	private void clickPin(PinData pin){
		//System.out.println("click pin:"+pin.getPos());
		if(onObjectEvent!=null){
			onObjectEvent.onPinClick(pin);
		}
	}
	private void click(MotionEvent event) {
		if(pining){
			clickPin(event);
			return;
		}
		int clickWord=this.getWord(event.getX(), event.getY());
//		for(int i=0;i<listPin.size();i++){
//			PinData pin=listPin.get(i);
//			if(pin.getPos()>=clickWord-3 && pin.getPos()<=clickWord+3){
//				clickPin(pin);
//				return;
//			}
//		}
		int line = -1;
		int height = 0;
		for (int i = 0; i < pages.get(curPage).size(); i++) {
			height += lineHeight;
			if (height > event.getY()) {
				line = i;
				break;
			}
		}
		if(line<0) return;
		String lineWords = pages.get(curPage).get(line);
		int stringEnd = lineWords.length();
		int stringStart = 0;
		float width = paddingLeft;
		selectedStartX = width;
		for (int i = 0; i < lineWords.length(); i++) {
			char c = lineWords.charAt(i);
			width += getCharWidth(c);
			if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
				if (width >= event.getX()) {
					stringEnd = i;
					selectedEndx = width;
					break;
				} else {
					stringStart = i + 1;
					selectedStartX = width;
				}
			}
		}

		if (width > event.getX()) {
			selectedLine = line;
			this.invalidate();
			if (textSelectListener != null) {
				textSelectListener.onSelected(
						lineWords.substring(stringStart, stringEnd),
						event.getY() < this.getHeight() / 2,
						event.getX() < this.getWidth() / 2, (int) event.getX(),
						(int) (height + linePadding),
						(int) (lineHeight + linePadding));
			}
		}
	}

	private float getCharWidth(char c) {
		if (c <= 127) {
			return charWidths[(int) c];
		} else
			return 0;
	}

	private float getStringWidth(String str) {
		// Log.d("UI", "get str width:"+str);
		float width = 0;
		for (int i = 0; i < str.length(); i++) {
			width += this.getCharWidth(str.charAt(i));
		}
		return width;
	}

	private void onChangePage() {
		int lastRead = 0;
		int pageEnd = 0;
		for (int i = 0; i < curPage; i++) {
			for (String line : pages.get(i)) {
				lastRead += line.length();
			}
		}
		for (String line : pages.get(curPage)) {
			pageEnd += line.length();
		}
		pageEnd += lastRead;
		lastRead++;
		if (onPage != null) {
			onPage.pageChange(curPage, pages.size(), lastRead, pageEnd);
		}

	}

	private void animationMove(final float from, final float to, int duration) {
		ValueAnimator animation = ValueAnimator.ofFloat(0f, 1f);
		animation.setInterpolator(new AccelerateDecelerateInterpolator());
		final float moveStart = from;
		animation.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (mMoveX < 0 && Math.abs(moveStart) > getWidth() / 4) {
					// next
					if (curPage < (pages.size() - 1)) {
						curPage++;
						selectedLine = -1;
						chagePage();
					}
				}
				if (mMoveX > 0 && moveStart > getWidth() / 4) {

					if (curPage > 0) {
						curPage--;
						selectedLine = -1;
						chagePage();
					}
				}
				onChangePage();
				mMoveX = 0;

			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

		});
		animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator va) {

				if (moveStart > 0) {
					mMoveX = moveStart + ((Float) va.getAnimatedValue())
							* (to - from);
				} else {
					mMoveX = moveStart + ((Float) va.getAnimatedValue())
							* (to - from);
				}
				invalidate();
			}
		});
		animation.setDuration(duration);
		animation.start();
	}

	public File getTxtFile() {
		return txtFile;
	}

	public void setTxtFile(File txtFile) {
		this.txtFile = txtFile;
	}

	public OnBookSetOver getOnBookSetOver() {
		return onBookSetOver;
	}

	public void setOnBookSetOver(OnBookSetOver onBookSetOver) {
		this.onBookSetOver = onBookSetOver;
	}

	public OnPage getOnPage() {
		return onPage;
	}

	public void setOnPage(OnPage onPage) {
		this.onPage = onPage;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getBackcolor() {
		return backcolor;
	}

	public void setBackcolor(int backcolor) {
		this.backcolor = backcolor;
	}

	public void pageto(int progress) {
		if (pages != null && progress < pages.size()) {
			curPage = progress;
			invalidate();
		}

	}

	public void setScrolling(boolean b) {
		this.scrolling = b;
		invalidate();
	}

	public void setFontType(Typeface font) {
		this.font = font;

	}

	public void setFontcolor(int fontColor) {
		fontcolor = fontColor;
	}

	public List<HighlightData> getHighLights() {
		return highLights;
	}

	public void setHighLights(List<HighlightData> highLights) {
		this.highLights = highLights;
		this.invalidate();
	}
	public void deleteHighlight(HighlightData hd){
		this.highLights.remove(hd);
		this.invalidate();
	}
	public void deletePin(PinData pin){
		this.listPin.remove(pin);
		this.invalidate();
	}

	public OnObjectEvent getOnObjectEvent() {
		return onObjectEvent;
	}

	public void setOnObjectEvent(OnObjectEvent onObjectEvent) {
		this.onObjectEvent = onObjectEvent;
	}

	public void setListPin(List<PinData> listResult) {
		this.listPin=listResult;
		this.invalidate();
		
	}

}
