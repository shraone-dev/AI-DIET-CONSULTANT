package com.aidiet.consultant;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ScrDiet extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_diet);
        ImageView ivDiet = findViewById(R.id.iv_diet);
        if (BMIApp.bmi < 16) {
            ivDiet.setBackground(getResources().getDrawable(R.drawable.very_low));
        } else if (BMIApp.bmi > 16 && BMIApp.bmi < 18.5) {
            ivDiet.setBackground(getResources().getDrawable(R.drawable.low));
        } else if (BMIApp.bmi > 18.5 && (BMIApp.bmi <= 25)) {
            ivDiet.setBackground(getResources().getDrawable(R.drawable.normal));
        } else if (BMIApp.bmi > 25) {
            ivDiet.setBackground(getResources().getDrawable(R.drawable.obesity));
        }
    }
}
