package com.aidiet.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aidiet.consultant.db.User;
import com.aidiet.consultant.db.UserDB;

public class ScrSignup extends AppCompatActivity implements View.OnClickListener {

    private EditText et_email, et_pass, et_name;
    private Button bt_ok, bt_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_signup);

        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        bt_ok = findViewById(R.id.bt_ok);
        bt_cancel = findViewById(R.id.bt_cancel);
        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                String email = et_email.getText().toString();
                String pass = et_pass.getText().toString();
                String name = et_name.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(name)) {
                    User user = new User(name, pass, email);
                    UserDB.getInstance().add(user);
                    Toast.makeText(this, "Signup success", Toast.LENGTH_LONG).show();
                    clearEditText(et_email, et_email, et_name);
                } else {
                    Toast.makeText(this, "Please enter proper details", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.bt_cancel:
                finish();
                break;
        }

    }

    private void clearEditText(EditText email, EditText pass, EditText name) {
        email.setText("");
        pass.setText("");
        name.setText("");
        startActivity(new Intent(this, ScrLogin.class));
    }

}
