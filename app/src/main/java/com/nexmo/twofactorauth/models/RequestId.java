package com.nexmo.twofactorauth.models;


import com.squareup.moshi.Json;

public class RequestId {

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
