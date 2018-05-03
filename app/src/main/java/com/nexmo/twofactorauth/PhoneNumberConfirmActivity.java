package com.nexmo.twofactorauth;

import android.content.Context;
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
import com.nexmo.twofactorauth.models.CheckVerifyResponse;
import com.nexmo.twofactorauth.models.RequestId;
import com.nexmo.twofactorauth.models.VerifyRequest;
import com.nexmo.twofactorauth.utils.VerifyUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class PhoneNumberConfirmActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String TWO_FACTOR_AUTH = "TWO_FACTOR_AUTH";
    private EditText codeTxt;
    private String requestId;
    private TextView verifyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_confirm);
        SharedPreferences sharedPref = getSharedPreferences(TWO_FACTOR_AUTH, Context.MODE_PRIVATE);

        String phoneNumber = sharedPref.getString(PHONE_NUMBER, null);
        Log.d(TAG, "phone: " + phoneNumber);
        requestId = sharedPref.getString(phoneNumber, null);
        Log.d(TAG, "request id: " + requestId);

        codeTxt = findViewById(R.id.code_txt);
        TextView phoneTxt = findViewById(R.id.phone_txt);
        verifyTxt = findViewById(R.id.verify_txt);
        Button confirmBtn = findViewById(R.id.confirm_btn);
        Button cancelBtn = findViewById(R.id.cancel_btn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    confirmCode(codeTxt.getText().toString());
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRequest();
            }
        });

        phoneTxt.setText(phoneNumber);
        verifyTxt.setText(null);
    }


    private void confirmCode(String code) {
        verifyTxt.setText(null);
        VerifyUtil.getInstance().getVerifyService().check(new VerifyRequest(code, requestId)).enqueue(new Callback<CheckVerifyResponse>() {
            @Override
            public void onResponse(Call<CheckVerifyResponse> call, Response<CheckVerifyResponse> response) {
                if (response.isSuccessful()) {
                    verifyTxt.setText("Verified!");
                    Toast.makeText(PhoneNumberConfirmActivity.this, "Verified!", Toast.LENGTH_LONG).show();
                } else {
                    Converter<ResponseBody, CheckVerifyResponse> errorConverter = VerifyUtil.getInstance().getRetrofit().responseBodyConverter(CheckVerifyResponse.class, new Annotation[0]);
                    try {
                        CheckVerifyResponse checkVerifyResponse = errorConverter.convert(response.errorBody());
                        verifyTxt.setText(checkVerifyResponse.getErrorText());
                        Toast.makeText(PhoneNumberConfirmActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckVerifyResponse> call, Throwable t) {
              Toast.makeText(PhoneNumberConfirmActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
              Log.e(TAG, "onFailure: ", t);
            }
      });
    }

    private void cancelRequest() {
        verifyTxt.setText(null);
        VerifyUtil.getInstance().getVerifyService().cancel(new RequestId(requestId)).enqueue(new Callback<CancelVerifyResponse>() {
            @Override
            public void onResponse(Call<CancelVerifyResponse> call, Response<CancelVerifyResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PhoneNumberConfirmActivity.this, "Cancelled!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Converter<ResponseBody, CancelVerifyResponse> errorConverter = VerifyUtil.getInstance().getRetrofit().responseBodyConverter(CancelVerifyResponse.class, new Annotation[0]);
                    try {
                        CancelVerifyResponse cancelVerifyResponse = errorConverter.convert(response.errorBody());
                        verifyTxt.setText(cancelVerifyResponse.getErrorText());
                        Toast.makeText(PhoneNumberConfirmActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CancelVerifyResponse> call, Throwable t) {
                Toast.makeText(PhoneNumberConfirmActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
