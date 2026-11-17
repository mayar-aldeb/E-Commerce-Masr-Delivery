package org.route.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class RestaurantException extends HttpClientErrorException {
    public RestaurantException(HttpStatus httpStatus, String message) {
        super(httpStatus,message);
    }
}
