package com.example.infinitylabs.dynamictrackerapp.view.site_approval;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.view.section.SectionListActivity;
import com.example.infinitylabs.dynamictrackerapp.view.section.SectionListFragment;
import com.example.infinitylabs.dynamictrackerapp.view.task.TaskListActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SiteApprovalActivity extends AppCompatActivity {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SiteApprovalActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_approval);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.frame_sub_container, SiteApprovalFragment.newInstance());
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TaskListActivity.launch(this);
    }
}
