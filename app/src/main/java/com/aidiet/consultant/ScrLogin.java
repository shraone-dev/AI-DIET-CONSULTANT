package com.aidiet.consultant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aidiet.consultant.db.User;
import com.aidiet.consultant.db.UserDB;
import com.aidiet.consultant.pref.UserPref;

public class ScrLogin extends AppCompatActivity implements View.OnClickListener {

    private EditText et_email, et_pass;
    private Button bt_ok, bt_cancel;
    private TextView tv_signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_login);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        bt_ok = findViewById(R.id.bt_ok);
        bt_cancel = findViewById(R.id.bt_cancel);
        tv_signup = findViewById(R.id.tv_signup);
        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                String email = et_email.getText().toString();
                String pass = et_pass.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
                    User user = UserDB.getInstance().getAllDetailsOfUser(email, pass);
                    if (user != null) {
                        UserPref.getInstance().setIsLoggedIn(true);
                        clearEditText(et_email, et_pass);
                        finish();
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Please enter proper details", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.bt_cancel:
                finish();
                break;

            case R.id.tv_signup:
                startActivity(new Intent(this, ScrSignup.class));
                break;
        }

    }

    private void clearEditText(EditText email, EditText pass) {
        email.setText("");
        pass.setText("");
        startActivity(new Intent(this, ScrOptions.class));
    }
}
