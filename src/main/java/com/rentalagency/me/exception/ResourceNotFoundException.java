package com.rentalagency.me.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BANDWIDTH_LIMIT_EXCEEDED,reason="Error Occured with your request")
public class ResourceNotFoundException extends RuntimeException {


}
