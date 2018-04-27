package com.nexmo.twofactorauth.utils;


import com.nexmo.twofactorauth.models.CancelVerifyResponse;
import com.nexmo.twofactorauth.models.CheckVerifyResponse;
import com.nexmo.twofactorauth.models.PhoneNumber;
import com.nexmo.twofactorauth.models.RequestId;
import com.nexmo.twofactorauth.models.VerifyRequest;
import com.nexmo.twofactorauth.models.VerifyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VerifyService {

    @POST("request")
    Call<VerifyResponse> request(@Body PhoneNumber phoneNumber);

    @POST("check")
    Call<CheckVerifyResponse> check(@Body VerifyRequest verifyRequest);

    @POST("cancel")
    Call<CancelVerifyResponse> cancel(@Body RequestId requestId);
}
