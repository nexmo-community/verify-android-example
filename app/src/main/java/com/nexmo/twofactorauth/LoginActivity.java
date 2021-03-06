package com.nexmo.twofactorauth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nexmo.twofactorauth.models.CancelVerifyResponse;
import com.nexmo.twofactorauth.models.PhoneNumber;
import com.nexmo.twofactorauth.models.RequestId;
import com.nexmo.twofactorauth.models.VerifyResponse;
import com.nexmo.twofactorauth.utils.VerifyUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String TWO_FACTOR_AUTH = "TWO_FACTOR_AUTH";
    private EditText phoneTxt;
    private SharedPreferences sharedPref;
    private TextView errorTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneTxt = findViewById(R.id.phone);
        errorTxt = findViewById(R.id.error_txt);
        Button signInBtn = findViewById(R.id.sign_in_btn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start2FA(phoneTxt.getText().toString());
            }
        });
        Button cancelBtn = findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRequest(phoneTxt.getText().toString());
            }
        });

        sharedPref = getSharedPreferences(TWO_FACTOR_AUTH, Context.MODE_PRIVATE);
        errorTxt.setText(null);
    }

    private void start2FA(final String phone) {
        //clear out the previous error (if any) that was shown.
        errorTxt.setText(null);
        //start the verification request. Wrap `String phone` in a `PhoneNumber` class so it is correctly serialized into JSON
        Call<VerifyResponse> request = VerifyUtil.getInstance().getVerifyService().request(new PhoneNumber(phone));
        request.enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                if (response.isSuccessful()) {
                    //parse the response
                    VerifyResponse requestVerifyResponse = response.body();
                    storeResponse(phone, requestVerifyResponse);
                    startActivity(new Intent(LoginActivity.this, PhoneNumberConfirmActivity.class));
                } else {
                    //if the HTTP response is 4XX, Retrofit doesn't pass the response to the `response.body();`
                    //So we need to convert the `response.errorBody()` to a `VerifyResponse`
                    Converter<ResponseBody, VerifyResponse> errorConverter = VerifyUtil.getInstance().getRetrofit().responseBodyConverter(VerifyResponse.class, new Annotation[0]);
                    try {
                        VerifyResponse verifyResponse = errorConverter.convert(response.errorBody());
                        Toast.makeText(LoginActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
                        errorTxt.setText(verifyResponse.getErrorText());
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: ", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    //You may want to cancel the previous request without launching the PhoneNumberConfirmActivity
    private void cancelRequest(final String phone) {
        //clear out the previous error (if any) that was shown.
        errorTxt.setText(null);
        //The requestId needs to be sent in the request to cancel a verification. Wrap `String phone` in a `PhoneNumber` class so it is correctly serialized into JSON
        String requestId = sharedPref.getString(phone, null);
        //Cancel the verification request
        VerifyUtil.getInstance().getVerifyService().cancel(new RequestId(requestId)).enqueue(new Callback<CancelVerifyResponse>() {
            @Override
            public void onResponse(Call<CancelVerifyResponse> call, Response<CancelVerifyResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Cancelled!", Toast.LENGTH_LONG).show();
                } else {
                    //if the HTTP response is 4XX, Retrofit doesn't pass the response to the `response.body();`
                    //So we need to convert the `response.errorBody()` to a `CancelVerifyResponse`
                    Converter<ResponseBody, CancelVerifyResponse> errorConverter = VerifyUtil.getInstance().getRetrofit().responseBodyConverter(CancelVerifyResponse.class, new Annotation[0]);
                    try {
                        CancelVerifyResponse cancelVerifyResponse = errorConverter.convert(response.errorBody());
                        errorTxt.setText(cancelVerifyResponse.getErrorText());
                        Toast.makeText(LoginActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: ", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<CancelVerifyResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void storeResponse(String phone, VerifyResponse response) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PHONE_NUMBER, phone);
        //Don't store empty responseIds since we only keep a reference to one requestId at a time
        if (!TextUtils.isEmpty(response.getRequestId())) {
            editor.putString(phone, response.getRequestId());
        }
        editor.apply();
    }
}
