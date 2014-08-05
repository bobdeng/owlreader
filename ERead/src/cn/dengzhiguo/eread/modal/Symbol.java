package cn.dengzhiguo.eread.modal;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Symbol implements Serializable{

	private String ph_en;
	private String ph_am;
	private String ph_en_mp3;
	private String ph_am_mp3;
	private String ph_tts_mp3;
	private List<Part> parts;
	
	public String getPh_en() {
		return ph_en;
	}
	public void setPh_en(String ph_en) {
		this.ph_en = ph_en;
	}
	public String getPh_am() {
		return ph_am;
	}
	public void setPh_am(String ph_am) {
		this.ph_am = ph_am;
	}
	public String getPh_en_mp3() {
		return ph_en_mp3;
	}
	public void setPh_en_mp3(String ph_en_mp3) {
		this.ph_en_mp3 = ph_en_mp3;
	}
	public String getPh_am_mp3() {
		return ph_am_mp3;
	}
	public void setPh_am_mp3(String ph_am_mp3) {
		this.ph_am_mp3 = ph_am_mp3;
	}
	public List<Part> getParts() {
		return parts;
	}
	public void setParts(List<Part> parts) {
		this.parts = parts;
	}
	public String getPh_tts_mp3() {
		return ph_tts_mp3;
	}
	public void setPh_tts_mp3(String ph_tts_mp3) {
		this.ph_tts_mp3 = ph_tts_mp3;
	}

}
