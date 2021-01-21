package com.handyman.app.utility;

public enum DefaultAnswerEnum {

	MESSAGE_INSERT("successful insert"), MESSAGE_ERROR("error process failed"),
	MESSAGE_DUPLICATE("You cannot add this service report, it is already registered"),
	MESSAGE_ERROR_DATE("the end date cannot be less than the initial or equal");

	private String value;

	private DefaultAnswerEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
