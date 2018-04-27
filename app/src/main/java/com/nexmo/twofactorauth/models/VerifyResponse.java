package com.nexmo.twofactorauth.models;


import com.squareup.moshi.Json;

public class VerifyResponse {

    @Json(name = "error_text")
    private String errorText;
    @Json(name = "request_id")
    private String requestId;
    private int status;


    public String getErrorText() {
        return errorText;
    }

    public String getRequestId() {
        return requestId;
    }

    public int getStatus() {
        return status;
    }
}
