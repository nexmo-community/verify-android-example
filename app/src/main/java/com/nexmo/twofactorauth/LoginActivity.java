package com.nexmo.twofactorauth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private EditText phoneTxt;
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
    }

    private void start2FA(final String phone) {


    }
}
