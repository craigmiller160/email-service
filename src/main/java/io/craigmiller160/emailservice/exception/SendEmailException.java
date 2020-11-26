package io.craigmiller160.emailservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error sending email")
public class SendEmailException extends Exception {

    public SendEmailException(final String message, final Throwable ex) {
        super (message, ex);
    }

}
