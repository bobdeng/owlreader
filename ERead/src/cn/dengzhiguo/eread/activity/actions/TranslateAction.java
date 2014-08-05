package cn.dengzhiguo.eread.activity.actions;

import mobi.zhangying.cyclops.ActionMethod;
import mobi.zhangying.cyclops.ResponseMethod;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import cn.dengzhiguo.eread.bo.INewword;
import cn.dengzhiguo.eread.bo.NewwordImpl;
import cn.dengzhiguo.eread.db.Newword;
import cn.dengzhiguo.eread.net.DictNetHandler;
import cn.dengzhiguo.eread.net.IDictNetHandler;
@EBean
public class TranslateAction implements ITranslate{

	@Bean(DictNetHandler.class)
	IDictNetHandler dictHandler;
	@Bean(NewwordImpl.class)
	INewword newwordBo;
	@RootContext
	Context context;
	
	@ActionMethod
	public void translate(String word) {
		Newword newword=newwordBo.findNewword(word);
		if(newword==null){
			newword=new Newword(dictHandler.translate(word));
			newword.setWord(word);
			newwordBo.addNewword(newword);
		}else{
			newword.setTimes(newword.getTimes()+1);
			newwordBo.saveNewword(newword);
		}
		translateResult(newword);
	}
	@ResponseMethod
	public void translateResult(Newword newword){}
	@ActionMethod
	public void playvoice(String url){
		MediaPlayer player=MediaPlayer.create(context, Uri.parse(url));
		try {
			player.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} 
		
	}
	
}
