package org.route.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class OrderException extends HttpClientErrorException {
    public OrderException(HttpStatus httpStatus,String message) {
        super(httpStatus,message);
    }
}
