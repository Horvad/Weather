package org.example.sensor.exeptions;

import feign.FeignException;

public class FeignExceptionBadRequest extends FeignException {

    public FeignExceptionBadRequest(int status, String message) {
        super(status, message);
    }
}
