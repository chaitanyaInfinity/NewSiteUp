package com.example.infinitylabs.dynamictrackerapp.view.create_password;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.view.otp_screen.OtpActivity;
import com.example.infinitylabs.dynamictrackerapp.view.otp_screen.OtpFragment;

import butterknife.ButterKnife;

public class CreatePasswordActivity extends AppCompatActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, CreatePasswordActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        ButterKnife.bind(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_sub_container, CreatePasswordFragment.newInstance());
        fragmentTransaction.commitAllowingStateLoss();
    }
}
