package cn.dengzhiguo.eread.activity.actions;

import java.util.ArrayList;

import mobi.zhangying.cyclops.ActionMethod;
import mobi.zhangying.cyclops.ResponseMethod;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import cn.dengzhiguo.eread.bo.INewword;
import cn.dengzhiguo.eread.bo.NewwordImpl;
import cn.dengzhiguo.eread.db.Newword;

@EBean
public class NewwordAction {

	@Bean(NewwordImpl.class)
	INewword newwordBo;
	@ActionMethod
	public void listNewwords() {
		ArrayList<Newword> list=new ArrayList<Newword>();
		list.addAll(newwordBo.getAll());
		listNewwordsResult(list);
	}
	@ResponseMethod
	public void listNewwordsResult(ArrayList<Newword> list){}
	
	@ActionMethod
	public void deleteNewword(Newword word) {
		newwordBo.delete(word);
	}
}
