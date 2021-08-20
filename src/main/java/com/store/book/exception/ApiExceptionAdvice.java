package com.store.book.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public class ApiExceptionAdvice {

	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(ApiException.class)
	public Error apiException(ApiException e) {
		return e.getError();
	}

}
