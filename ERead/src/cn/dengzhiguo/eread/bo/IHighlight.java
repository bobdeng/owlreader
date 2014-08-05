package cn.dengzhiguo.eread.bo;

import java.util.List;

import cn.dengzhiguo.eread.db.Highlight;
import cn.dengzhiguo.eread.widget.HighlightData;

public interface IHighlight {

	void save(HighlightData parcelableExtra, int i);

	List<Highlight> find(int bookid, int from, int to);

	void delete(HighlightData parcelableExtra);

}
