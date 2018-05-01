package com.nexmo.twofactorauth;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
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

        codeTxt = findViewById(R.id.code_txt);
        TextView phoneTxt = findViewById(R.id.phone_txt);
        verifyTxt = findViewById(R.id.verify_txt);
        Button confirmBtn = findViewById(R.id.confirm_btn);
        Button cancelBtn = findViewById(R.id.cancel_btn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmCode(Integer.parseInt(codeTxt.getText().toString()));
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRequest();
            }
        });
    }


    private void confirmCode(int code) {

    }

    private void cancelRequest() {

    }
}
