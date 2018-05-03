package com.nexmo.twofactorauth.models;

import com.squareup.moshi.Json;

public class VerifyRequest {
    private String code;
    @Json(name = "request_id")
    private String requestId;

    public VerifyRequest(String code, String requestId) {
        this.code = code;
        this.requestId = requestId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
