package ru.mcb.common;

public class CrypterException extends Exception {

	private static final long serialVersionUID = 13L;

	public CrypterException(String s) {
		super(s);
	}

	public CrypterException(Exception e) {
		super(e);
	}
}
