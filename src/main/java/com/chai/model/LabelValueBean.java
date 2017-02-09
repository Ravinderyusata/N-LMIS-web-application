package com.chai.model;

public class LabelValueBean {
	private String value;
	private String label;
	private String extra1;
	private String extra2;
	private String extra3;
	private String extra4;

	public LabelValueBean() {
	}

	public LabelValueBean(String value, String label) {
		super();
		this.value = value;
		this.label = label;
	}

	public LabelValueBean(String value, String label, String extra1, String extra2) {
		super();
		this.value = value;
		this.label = label;
		this.extra1 = extra1;
		this.extra2 = extra2;
	}

	public LabelValueBean(String value, String label, String extra1) {
		super();
		this.value = value;
		this.label = label;
		this.extra1 = extra1;
	}

	public LabelValueBean(String value, String label, String extra1, String extra2, String extra3) {
		super();
		this.value = value;
		this.label = label;
		this.extra1 = extra1;
		this.extra2 = extra2;
		this.extra3 = extra3;
	}

	public LabelValueBean(String value, String label, String extra1, String extra2, String extra3, String extra4) {
		super();
		this.value = value;
		this.label = label;
		this.extra1 = extra1;
		this.extra2 = extra2;
		this.extra3 = extra3;
		this.extra4 = extra4;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public String getExtra3() {
		return extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}

	public String getExtra4() {
		return extra4;
	}

	public void setExtra4(String extra4) {
		this.extra4 = extra4;
	}
}
