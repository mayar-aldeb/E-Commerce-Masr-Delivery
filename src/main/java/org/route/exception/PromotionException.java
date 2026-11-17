package org.route.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class PromotionException extends HttpClientErrorException {
    
    public PromotionException(HttpStatus httpStatus,String message) {
        super(httpStatus,message);
    }
}
