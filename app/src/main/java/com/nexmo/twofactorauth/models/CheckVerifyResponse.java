package com.nexmo.twofactorauth.models;


import com.squareup.moshi.Json;

public class CheckVerifyResponse extends VerifyResponse {

    @Json(name = "event_id")
    private String eventId;
    private float price;
    private String currency;

    public String getEventId() {
        return eventId;
    }

    public float getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
