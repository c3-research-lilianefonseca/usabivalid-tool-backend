package com.unicap.tcc.usability.api.models.enums;

public enum EReviewState {

	AVAILABLE("Available"),
	REVIEW_REQUESTED("Review Requested"),
	REVIEWING("Reviewing"),
	Refused("Refused"),
	Canceled("Canceled"),
	Expired("Expired"),
	COMPLETED("Completed"),
	Invalid("Invalid");
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	EReviewState (String description)
	{
		this.description = description;
	}
	
	public boolean isReportable(){
		return this == REVIEWING || this == EReviewState.COMPLETED;
	}
	
	public boolean isExpirable(){
		return this == REVIEWING || this == EReviewState.REVIEW_REQUESTED;
	}
}
