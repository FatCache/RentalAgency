package com.rentalagency.me.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Generic Exception used to flag bad request to the page.
 * Can be used to either throw custom error pgae [404, 500, ... ] or 
 * throw default error pages
 * @author abdusamed
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST,reason="Error Occured with your request")
public class ResourceNotFoundException extends RuntimeException {


}
