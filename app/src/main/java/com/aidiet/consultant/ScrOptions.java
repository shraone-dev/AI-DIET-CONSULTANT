package com.aidiet.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.aidiet.consultant.pref.UserPref;

public class ScrOptions extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_bmi, ll_bmr, ll_bfp, ll_logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_options);

        ll_bmi = findViewById(R.id.ll_bmi);
        ll_bmr = findViewById(R.id.ll_bmr);
        ll_bfp = findViewById(R.id.ll_bfp);
        ll_logout = findViewById(R.id.ll_logout);
        ll_bfp.setOnClickListener(this);
        ll_bmi.setOnClickListener(this);
        ll_bmr.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bmi:
                BMIApp.option = 1;

                break;

            case R.id.ll_bfp:
                BMIApp.option = 2;
                break;

            case R.id.ll_bmr:
                BMIApp.option = 3;
                break;

            case R.id.ll_logout:
                UserPref.getInstance().setIsLoggedIn(false);
                startActivity(new Intent(this, ScrLogin.class));

                return;
        }
        startActivity(new Intent(this, ScrMain.class));
    }

    public void aboutUsInfo(View v) {
        startActivity(new Intent(this, AboutUsActivity.class));
    }
}
