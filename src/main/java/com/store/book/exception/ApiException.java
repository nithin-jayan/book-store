package com.store.book.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException {
	private Error error;

	public ApiException(String code, String message) {
		super(message);
		this.error = new Error(code, message);
	}
}
