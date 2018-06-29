package com.example.infinitylabs.dynamictrackerapp.base_view.activities;


import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.infinitylabs.dynamictrackerapp.R;


public class BaseToolbarActivity extends AppCompatActivity {


    ImageButton ibLogout;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base_frame_container);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.frame_main_container);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        ibLogout = (ImageButton) findViewById(R.id.ib_logout);
        setupToolbar();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base_frame_container);
//        ButterKnife.bind(this);
//        Fragment fragment = new LoginFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main_container,
//                fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
//
////        tvSiteId.setText("Site ID : " + SelectedSiteUtility.getInstance().getSiteName());
//
//
//        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
//                .content(R.string.logout_dialog)
//                .contentColor(ContextCompat.getColor(BaseToolbarActivity.this, R.color.colorBlack))
//                .positiveText(R.string.yes)
//                .positiveColor(ContextCompat.getColor(BaseToolbarActivity.this, R.color.colorPrimary))
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
////                        LoginActivity.launch(BaseToolbarActivity.this);
//                    }
//                })
//                .negativeText(R.string.no)
//                .negativeColor(ContextCompat.getColor(BaseToolbarActivity.this, R.color.colorPrimary))
//                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                    }
//                });
//
//
//        final MaterialDialog dialog = builder.build();
//
//        ibLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show();
//            }
//        });
//
//    }

    private void setupToolbar() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .content(R.string.logout_dialog)
                .contentColor(ContextCompat.getColor(BaseToolbarActivity.this, R.color.colorBlack))
                .positiveText(R.string.yes)
                .positiveColor(ContextCompat.getColor(BaseToolbarActivity.this, R.color.colorPrimary))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        LoginActivity.launch(BaseToolbarActivity.this);
                    }
                })
                .negativeText(R.string.no)
                .negativeColor(ContextCompat.getColor(BaseToolbarActivity.this, R.color.colorPrimary))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });


        final MaterialDialog dialog = builder.build();

        ibLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
}
