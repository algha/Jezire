package com.jezire.http;

import java.io.IOException;

public class Base64DataException extends IOException {
	private static final long serialVersionUID = 1L;

	public Base64DataException(String detailMessage) {
		super(detailMessage);
	}
}