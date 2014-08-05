package cn.dengzhiguo.eread.net;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import cn.dengzhiguo.eread.modal.TranslateInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@EBean
public class DictNetHandler implements IDictNetHandler {

	@RestService
	DictClient dictClient;
	@Override
	public TranslateInfo translate(String word) {
		
		return dictClient.getTranslate(word);
	}

}
