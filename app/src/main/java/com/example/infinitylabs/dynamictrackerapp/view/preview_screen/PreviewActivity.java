package com.example.infinitylabs.dynamictrackerapp.view.preview_screen;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.view.login.LoginActivity;
import com.example.infinitylabs.dynamictrackerapp.view.login.LoginFragment;

public class PreviewActivity extends AppCompatActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, PreviewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_sub_container, PreviewFragment.newInstance());
        fragmentTransaction.commitAllowingStateLoss();
    }
}
