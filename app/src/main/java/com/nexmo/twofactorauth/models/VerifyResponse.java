package com.nexmo.twofactorauth.models;


import com.squareup.moshi.Json;

public class VerifyResponse {

    //The Nexmo Verify API expects fields to be snake_cased let's convert them to camelCase when the JSON is parsed
    @Json(name = "error_text")
    //The Nexmo Verify API expects fields to be snake_cased let's convert them to camelCase when the JSON is parsed
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
