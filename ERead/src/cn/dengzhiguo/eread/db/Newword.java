package cn.dengzhiguo.eread.db;

import java.io.Serializable;

import cn.dengzhiguo.eread.modal.Part;
import cn.dengzhiguo.eread.modal.Symbol;
import cn.dengzhiguo.eread.modal.TranslateInfo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_newword")
public class Newword implements Serializable {
	@DatabaseField(id = true)
	private String word;
	@DatabaseField
	private String en;
	@DatabaseField
	private String am;
	@DatabaseField
	private String voiceEn;
	@DatabaseField
	private String voiceAm;
	@DatabaseField
	private String voiceTts;
	@DatabaseField
	private String parts;
	@DatabaseField
	private int times;
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}

	public String getVoiceEn() {
		return voiceEn;
	}

	public void setVoiceEn(String voiceEn) {
		this.voiceEn = voiceEn;
	}

	public String getVoiceAm() {
		return voiceAm;
	}

	public void setVoiceAm(String voiceAm) {
		this.voiceAm = voiceAm;
	}

	public String getVoiceTts() {
		return voiceTts;
	}

	public void setVoiceTts(String voiceTts) {
		this.voiceTts = voiceTts;
	}

	public String getParts() {
		return parts;
	}

	public void setParts(String parts) {
		this.parts = parts;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public Newword() {

	}

	public Newword(TranslateInfo ti) {
		if (ti != null) {
			StringBuffer sbParts = new StringBuffer();
			for (Symbol symbol : ti.getSymbols()) {
				if (symbol.getPh_en() != null && this.en == null) {
					this.en = symbol.getPh_en();
					this.voiceEn = symbol.getPh_en_mp3();
				}
				if (symbol.getPh_am() != null && this.am == null) {
					this.am = symbol.getPh_am();
					this.voiceAm = symbol.getPh_am_mp3();
				}
				if (this.voiceTts == null && symbol.getPh_tts_mp3() != null) {
					this.voiceTts = symbol.getPh_tts_mp3();
				}
				if (symbol.getParts() != null) {
					for (Part part : symbol.getParts()) {
						sbParts.append(part.getPart());
						for (String mean : part.getMeans()) {
							sbParts.append(mean);
							sbParts.append(";");
						}
						sbParts.append("\n");
					}
				}
			}
			this.parts = sbParts.toString();
		}
	}
}
