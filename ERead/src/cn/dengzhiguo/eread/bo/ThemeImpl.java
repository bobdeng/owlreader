package cn.dengzhiguo.eread.bo;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;

import cn.dengzhiguo.eread.modal.Theme;
@EBean
public class ThemeImpl implements ITheme {

	public static List<Theme> listThemes=new ArrayList<Theme>();
	static{
		listThemes.add(new Theme(0xffecd9ac,0xff3d3d3d));
		listThemes.add(new Theme(0xff262626,0xffccffff));
		listThemes.add(new Theme(0xff100a0b,0xff726866));
		listThemes.add(new Theme(0xff2f2f2f,0xffffffcc));
		listThemes.add(new Theme(0xff330000,0xffffffff));
		listThemes.add(new Theme(0xff330000,0xff0ac200));
		listThemes.add(new Theme(0xff303000,0xffcccaa1));
		listThemes.add(new Theme(0xff4b2c23,0xffccffff));
		listThemes.add(new Theme(0xff4a4a4a,0xffccffff));
		listThemes.add(new Theme(0xff360e38,0xffccffff));
		listThemes.add(new Theme(0xff330033,0xffffffff));
		listThemes.add(new Theme(0xff522c54,0xffececa0));
		listThemes.add(new Theme(0xff224800,0xffccffff));
		listThemes.add(new Theme(0xff205218,0xff9cc9a3));
		listThemes.add(new Theme(0xff97cf94,0xff000000));
		listThemes.add(new Theme(0xffaf6f39,0xffccffff));
		listThemes.add(new Theme(0xffc9930a,0xffccffff));
		listThemes.add(new Theme(0xffda88c3,0xffccffff));
		listThemes.add(new Theme(0xff758f8f,0xffccffff));
		listThemes.add(new Theme(0xfff8e8e5,0xff3d3d3d));
		listThemes.add(new Theme(0xff806a3c,0xffa8b2b2));
		listThemes.add(new Theme(0xffb6a009,0xff000000));
		listThemes.add(new Theme(0xffd1d1d1,0xff3a3a3a));
		listThemes.add(new Theme(0xffcee7cb,0xff3333cc));
		listThemes.add(new Theme(0xffe6e6e6,0xff000000));
		listThemes.add(new Theme(0xffd69569,0xffdfdfdf));
		listThemes.add(new Theme(0xffc2f6ec,0xff3d3d3d));
		listThemes.add(new Theme(0xffd1d1d1,0xff050505));
		listThemes.add(new Theme(0xffb6ccc4,0xff6c6c36));
		listThemes.add(new Theme(0xff94ceec,0xff043264));
		listThemes.add(new Theme(0xff3477b6,0xfff5f5f5));
		listThemes.add(new Theme(0xffc4c4c4,0xff3a3a3a));
		listThemes.add(new Theme(0xffecce92,0xff945a1c));
		listThemes.add(new Theme(0xffedcc91,0xff925616));
		listThemes.add(new Theme(0xffdeceac,0xff64420c));
		listThemes.add(new Theme(0xffd3c8a3,0xff715017));
	}
	@Override
	public List<Theme> getAllTheme() {
		return listThemes;
	}

}
