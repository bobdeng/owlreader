package cn.dengzhiguo.eread.net;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;

import cn.dengzhiguo.eread.modal.TranslateInfo;

@Rest(rootUrl="http://dict-co.iciba.com/api/",converters = {MyJacksonConverter.class })
public interface DictClient {

	@Get("dictionary.php?w={word}&key=B551388316D652BEA79B45FDEA46F1C0&type=json")
	TranslateInfo getTranslate(String word);

}
