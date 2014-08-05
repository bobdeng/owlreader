package cn.dengzhiguo.eread.net;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class MyJacksonConverter extends MappingJackson2HttpMessageConverter {

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		// TODO Auto-generated method stub
		return true;
	}


}
