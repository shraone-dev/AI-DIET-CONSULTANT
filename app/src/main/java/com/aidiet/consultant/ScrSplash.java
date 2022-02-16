package com.aidiet.consultant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aidiet.consultant.pref.UserPref;

import java.util.Timer;
import java.util.TimerTask;

public class ScrSplash extends AppCompatActivity {

    private Timer _splashTimer;
    private SplashTimerTask _splashTimerTask;

    private final long SPLASH_TIMEOUT = (long) (1.5 * 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_splash);
        _scheduleTimer();
    }

    private void _scheduleTimer() {
        _splashTimer = new Timer();
        _splashTimerTask = new SplashTimerTask();
        _splashTimer.schedule(_splashTimerTask, SPLASH_TIMEOUT);
    }

    private class SplashTimerTask extends TimerTask {
        @Override
        public void run() {
            Intent intent = null;
            if (UserPref.getInstance().isLoggedIn()) {
                intent = new Intent(ScrSplash.this, ScrOptions.class);
            } else {
                intent = new Intent(ScrSplash.this, ScrLogin.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            ScrSplash.this.finish();
        }
    }
}
