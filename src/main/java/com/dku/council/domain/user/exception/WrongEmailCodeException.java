package com.dku.council.domain.user.exception;

import com.dku.council.global.error.exception.LocalizedMessageException;
import org.springframework.http.HttpStatus;

public class WrongEmailCodeException extends LocalizedMessageException {
    public WrongEmailCodeException() {
        super(HttpStatus.BAD_REQUEST, "invalid.email-code");
    }
}
