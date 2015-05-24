package hue.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomLightState {

	@JsonProperty("r")
	private Integer r;

	@JsonProperty("g")
	private Integer g;

	@JsonProperty("b")
	private Integer b;

	@JsonProperty("brightness")
	private Integer brightness;

	@JsonProperty("is-on")
	private Boolean isOn;
	
	public Boolean getIsOn() {
		return isOn;
	}

	public void setIsOn(Boolean on) {
		this.isOn = on;
	}

	public Integer getBrightness() {
		return brightness;
	}

	public void setBrightness(Integer brightness) {
		this.brightness = brightness;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public Integer getG() {
		return g;
	}

	public void setG(Integer g) {
		this.g = g;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}
}
