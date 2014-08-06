package cn.dengzhiguo.eread.view;

import mobi.zhangying.cyclops.MVCAction;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.dengzhiguo.eread.R;
import cn.dengzhiguo.eread.activity.actions.ITranslate;
import cn.dengzhiguo.eread.db.Newword;
@EViewGroup(R.layout.view_word)
public class WordView extends LinearLayout {
	@ViewById(R.id.txtParts)
	TextView txtParts;
	@ViewById(R.id.imgVoiceEn)
	ImageView imgVoiceEn;
	@ViewById(R.id.txtWord)
	TextView txtWord;
	@ViewById(R.id.imgVoiceAm)
	ImageView imgVoiceAm;
	@ViewById(R.id.txtPhen)
	TextView txtPhen;
	@ViewById(R.id.txtPham)
	TextView txtPham;
	@MVCAction
	ITranslate translate;
	Newword mNewword;
	public WordView(Context context) {
		super(context);
	}
	public void bind(Newword word){
		this.mNewword=word;
		txtParts.setText(word.getParts());
		txtWord.setText(word.getWord());
		txtPhen.setText(String.format("[英：%s]",word.getEn()));
		txtPham.setText(String.format("[美：%s]",word.getAm()));
		
	}
	@Click(R.id.imgVoiceEn)
	public void clickVoiceEn(){
		String voice=(mNewword.getVoiceEn()==null || "".equals(mNewword.getVoiceEn()))?
				mNewword.getVoiceTts():mNewword.getVoiceEn();
		this.playvoice(voice);
	}
	private void playvoice(String url){
		if(url!=null)
		{
			//System.out.println("start:"+url);
			translate.playvoice(url);
		}
		else
			Toast.makeText(this.getContext(), "没有语音", Toast.LENGTH_SHORT).show();

	}
	@Click(R.id.imgVoiceAm)
	public void clickVoiceAm(){
		String voice=(mNewword.getVoiceAm()==null || "".equals(mNewword.getVoiceAm()))?
				mNewword.getVoiceTts():mNewword.getVoiceAm();
		this.playvoice(voice);
	}
	public Newword getmNewword() {
		return mNewword;
	}

}
