package org.route.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class RiderException extends HttpClientErrorException {
    public RiderException(HttpStatus httpStatus, String message) {
        super(httpStatus,message);
    }
}
