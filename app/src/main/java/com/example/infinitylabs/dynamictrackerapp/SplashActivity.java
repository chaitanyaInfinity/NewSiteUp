package com.example.infinitylabs.dynamictrackerapp;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import com.example.infinitylabs.dynamictrackerapp.view.login.LoginActivity;
import com.example.infinitylabs.dynamictrackerapp.view.otp_screen.OtpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.tv_logo)
    TextView tvLogo;
    private SharedPreferences otpPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Audiowide.ttf");
        tvLogo.setTypeface(typeFace);

        otpPreferences = getSharedPreferences("otpPrefs", MODE_PRIVATE);
        Boolean numberVerified = otpPreferences.getBoolean("numberVerified", false);

        if (numberVerified) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoginActivity.launch(SplashActivity.this);
                    finish();
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    OtpActivity.launch(SplashActivity.this);
                    finish();
                }
            }, 2000);
        }
    }
}
