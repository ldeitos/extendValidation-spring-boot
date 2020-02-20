package com.github.ldeitos.test.base.stubs;

@TestConstraint(value = "valueParametrized")
public class Bean {
	private String stringField;

	private boolean booleanField;

	private boolean otherBooleanField;

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String field) {
		stringField = field;
	}

	public boolean isBooleanField() {
		return booleanField;
	}

	public void setBooleanField(boolean booleanField) {
		this.booleanField = booleanField;
	}

	public boolean isOtherBooleanField() {
		return otherBooleanField;
	}

	public void setOtherBooleanField(boolean otherBooleanField) {
		this.otherBooleanField = otherBooleanField;
	}
}
