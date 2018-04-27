package com.nexmo.twofactorauth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nexmo.twofactorauth.models.PhoneNumber;
import com.nexmo.twofactorauth.models.VerifyResponse;
import com.nexmo.twofactorauth.utils.VerifyUtil;

import retrofit2.Call;
import retrofit2.Callback;
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

        sharedPref = getSharedPreferences(TWO_FACTOR_AUTH, Context.MODE_PRIVATE);
        errorTxt.setText(null);
    }

    private void start2FA(final String phone) {
        errorTxt.setText(null);
        Call<VerifyResponse> request = VerifyUtil.getInstance().getVerifyService().request(new PhoneNumber(phone));
        request.enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                VerifyResponse requestVerifyResponse = response.body();
                if (response.code() > 200) {
                    Toast.makeText(LoginActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
                    errorTxt.setText(requestVerifyResponse.getErrorText());
                } else {
                    storeResponse(phone, requestVerifyResponse);
                    startActivity(new Intent(LoginActivity.this, PhoneNumberConfirmActivity.class));
                }
            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error Will Robinson!", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    private void storeResponse(String phone, VerifyResponse response) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PHONE_NUMBER, phone);
        editor.putString(phone, response.getRequestId());
        editor.apply();
    }
}
