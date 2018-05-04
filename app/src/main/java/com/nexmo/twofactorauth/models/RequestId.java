package com.nexmo.twofactorauth.models;


import com.squareup.moshi.Json;

public class RequestId {

    //The Nexmo Verify API expects fields to be snake_cased let's convert them to camelCase when the JSON is parsed
    @Json(name = "request_id")
    private String requestId;

    public RequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
