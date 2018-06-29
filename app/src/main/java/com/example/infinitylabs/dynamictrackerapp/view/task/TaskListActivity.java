package com.example.infinitylabs.dynamictrackerapp.view.task;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.view.login.LoginActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TaskListActivity extends AppCompatActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, TaskListActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_sub_container, TaskListFragment.newInstance());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        showDialog(this,
                "Logout", "Would you like to cancel current application?\nYour current application progress would be lost.");


    }

    protected void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity,R.style.MyDialogTheme);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginActivity.launch(TaskListActivity.this);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
