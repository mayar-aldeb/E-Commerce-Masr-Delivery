package org.route.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class ReportException extends HttpClientErrorException {
    public ReportException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
