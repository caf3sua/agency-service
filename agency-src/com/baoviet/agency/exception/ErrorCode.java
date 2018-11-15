package com.baoviet.agency.exception;

public enum ErrorCode {
	INVALID(2001, "invalid"),
	NULL_OR_EMPTY(2002, "null or empty"),
	UNKNOW_ERROR(2000, "unknow error"),
	OUT_OF_RANGER(2003, "out of ranger"),
	FORMAT_DATE_INVALID(2004, "format date invalid"),
	AGENCY_NOT_FOUND(2005, "agency not found"),
	GET_GYCBH_NUMBER_ERROR(2005, "get gycbh number error"),
	LINE_ID_NOT_FOUND(2006, "lindId not found"),
	GYCBH_EXIST(2007, "gycbhNumber is exist"),
	PRINTED_PAPER_NOT_FOUND(2008, "printed paper not found"),
	CREATE_YCBH_ANCHI_ERROR(2009, "create ycbh an chi error"),
	CREATE_YCBH_ANCHI_UNKNOW_ERROR(2010, "create ycbh an chi error");
	
	
	private ErrorCode(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }
	
	private final int statusCode;
	private final String description;
	
	public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }

}