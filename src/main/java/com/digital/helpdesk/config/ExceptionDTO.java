package com.digital.helpdesk.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDTO {
    public HttpStatus statusCode;
    public String message;
}
