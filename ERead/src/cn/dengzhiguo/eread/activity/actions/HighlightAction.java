package cn.dengzhiguo.eread.activity.actions;

import java.util.ArrayList;
import java.util.List;

import mobi.zhangying.cyclops.ActionMethod;
import mobi.zhangying.cyclops.ResponseMethod;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import cn.dengzhiguo.eread.bo.HighlightImpl;
import cn.dengzhiguo.eread.bo.IHighlight;
import cn.dengzhiguo.eread.db.Highlight;
import cn.dengzhiguo.eread.widget.HighlightData;
@EBean
public class HighlightAction implements IHighlightAction {

	@Bean(HighlightImpl.class)
	IHighlight highlightBo;
	
	@ActionMethod
	public void listHighlight(int bookid,int from, int to) {
		ArrayList<Highlight> result=new ArrayList<Highlight>();
		result.addAll(highlightBo.find(bookid,from,to));
		listHighlightResult(result);
	}
	@ResponseMethod
	public void listHighlightResult(ArrayList<Highlight> rlt){}
	@ActionMethod
	public void saveHighlight(int bookid,HighlightData data) {
		highlightBo.save(data,bookid);
	}
	@ActionMethod
	public void deleteHighlight(HighlightData data) {
		highlightBo.delete(data);
	}
	

}
