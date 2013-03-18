package com.mercadolibre.endOfMonthControl.exceptions;

public class MissingColumnException extends Exception {

	private static final long serialVersionUID = -4638198640440087698L;
	
	public MissingColumnException(String column) {
		super(column);
	}
}
