package com.example.infinitylabs.dynamictrackerapp.base_view.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infinitylabs.dynamictrackerapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public abstract class BaseDrawerActivity extends AppCompatActivity {


    @BindView(R.id.img_hamburger)
    ImageView imgHamburger;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.flContentRoot)
    FrameLayout flContentRoot;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    private TextView tvUserName;
    private TextView version;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        bindViews();
        setUpDrawer();
    }


    private void setUpDrawer() {

        imgHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);

            }
        });

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.END);

                if (item.getItemId() == R.id.nav_menu_apply) {
                    launchOTPScreen();
                } else if (item.getItemId() == R.id.nav_menu_dashboard) {
                    launchDashboardScreen();

                } else if (item.getItemId() == R.id.nav_menu_sync_img) {
                    launchSyncImageScreen();

                } else if (item.getItemId() == R.id.nav_menu_asset_master) {
                    launchAssetMasterScreen();
                } else if (item.getItemId() == R.id.nav_menu_change_password) {
                    launchChangePasswordScreen();
                } else if (item.getItemId() == R.id.nav_menu_emi_calculator) {
                    launchEMICalculatorScreen();
                } else if (item.getItemId() == R.id.nav_menu_version_properties) {
                    launchVersionPropertiesScreen();
                } else if (item.getItemId() == R.id.nav_menu_logout) {
                    showDialog(BaseDrawerActivity.this,
                            "Logout", "Would you like to cancel current application?\nYour current application progress would be lost.");
                }
                return false;
            }
        });

        if (navigationView != null) {
            tvUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_user_name);
            version = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_current_apk_version);
        }
    }


    public void launchAssetMasterScreen() {
//        Intent intentAsset = new Intent(BaseDrawerActivity.this, AssetMasterActivity.class);
//        startActivity(intentAsset);
//        finish();
    }

    public void launchChangePasswordScreen() {
//        Intent intentPassword = new Intent(BaseDrawerActivity.this, ChangePasswordActivity.class);
//        startActivity(intentPassword);
//        finish();
    }

    public void launchEMICalculatorScreen() {
//        Intent intentEMI = new Intent(BaseDrawerActivity.this, EMICalculationActivity.class);
//        startActivity(intentEMI);
//        finish();
    }

    public void launchVersionPropertiesScreen() {
//        Intent intentVersion = new Intent(BaseDrawerActivity.this, VersionScreen.class);
//        startActivity(intentVersion);
//        finish();
    }

    public void launchDashboardScreen() {
//        Intent intentDash = new Intent(BaseDrawerActivity.this, DashBoardActivity.class);
//        startActivity(intentDash);
//        finish();
    }

    public void launchSyncImageScreen() {
//        Intent intentSync = new Intent(BaseDrawerActivity.this, SyncImageActivity.class);
//        startActivity(intentSync);
//        finish();
    }

    public void launchOTPScreen() {
//        OTPActivity.launchScreen(BaseDrawerActivity.this, true);
//        finish();
    }


    protected void setupToolbar() {
        setSupportActionBar(toolbar);

        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            });
        }
    }

    @Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }


    protected void bindViews() {
        ButterKnife.bind(this);
        setupToolbar();
    }


    protected void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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


//    protected void disableNavigationDrawer() {
//        if (imgHamburger != null) {
//            imgHamburger.setVisibility(View.INVISIBLE);
//        }
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//    }
}
