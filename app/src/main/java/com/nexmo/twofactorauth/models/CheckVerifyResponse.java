package com.nexmo.twofactorauth.models;


import com.squareup.moshi.Json;

public class CheckVerifyResponse extends VerifyResponse {

    //The Nexmo Verify API expects fields to be snake_cased let's convert them to camelCase when the JSON is parsed
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
