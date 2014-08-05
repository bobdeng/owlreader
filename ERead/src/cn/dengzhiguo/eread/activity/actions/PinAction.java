package cn.dengzhiguo.eread.activity.actions;

import java.util.ArrayList;

import mobi.zhangying.cyclops.ActionMethod;
import mobi.zhangying.cyclops.ResponseMethod;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import cn.dengzhiguo.eread.bo.IPin;
import cn.dengzhiguo.eread.bo.PinImpl;
import cn.dengzhiguo.eread.db.Pin;
import cn.dengzhiguo.eread.widget.PinData;
@EBean
public class PinAction {

	@Bean(PinImpl.class)
	IPin pinBo;
	@ActionMethod
	public void listPin(int bookid,int from,int to) {
		ArrayList<Pin> list=new ArrayList<Pin>();
		list.addAll(pinBo.find(bookid,from,to));
		listPinResult(list);
	}
	@ResponseMethod
	public void listPinResult(ArrayList<Pin> rlt){}
	@ActionMethod
	public void savePin(PinData pin,int bookid) {
		pinBo.save(pin,bookid);
	}
	@ActionMethod
	public void deletePin(PinData data) {
		pinBo.delete(data);
	}
}
