package cn.dengzhiguo.eread.modal;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Part implements Serializable{
	private String part;
	private List<String> means;
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public List<String> getMeans() {
		return means;
	}
	public void setMeans(List<String> means) {
		this.means = means;
	}
	
	
}
