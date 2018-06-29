package com.example.infinitylabs.dynamictrackerapp.view.otp_screen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.example.infinitylabs.dynamictrackerapp.ExitActivity;
import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.view.login.LoginActivity;
import com.example.infinitylabs.dynamictrackerapp.view.tracking.TrackingFragment;

import butterknife.ButterKnife;


public class OtpActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_READ_SMS = 1992;
    private static final int REQUEST_CODE_RECEIVE_SMS = 1993;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, OtpActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        ButterKnife.bind(this);

        requestPermissionForApp();

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
//
//// permission is already granted
//// here you can directly access contacts
//
//        } else {
//
////persmission is not granted yet
////Asking for permission
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE);
//
//        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_sub_container, OtpFragment.newInstance());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ExitActivity.exitApplication(this);
    }


    private void requestPermissionForApp() {
        if (ContextCompat.checkSelfPermission(OtpActivity.this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(OtpActivity.this,
                    new String[]{Manifest.permission.READ_SMS},
                    REQUEST_CODE_READ_SMS);
        } else if (ContextCompat.checkSelfPermission(OtpActivity.this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(OtpActivity.this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    REQUEST_CODE_RECEIVE_SMS);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_RECEIVE_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    requestPermissionForApp();

                } else {
                    finish();
                }
                return;
            }

            case REQUEST_CODE_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    requestPermissionForApp();

                } else {
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
