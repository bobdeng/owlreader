package cn.dengzhiguo.eread.bo;

import java.util.List;

import cn.dengzhiguo.eread.db.Pin;
import cn.dengzhiguo.eread.widget.PinData;

public interface IPin {

	List<Pin> find(int bookid, int from, int to);

	void save(PinData pinData, int intExtra);

	void delete(PinData pinData);

}
