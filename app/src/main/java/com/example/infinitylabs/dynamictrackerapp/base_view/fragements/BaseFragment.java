package com.example.infinitylabs.dynamictrackerapp.base_view.fragements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.base_view.BaseScreenView;
import com.example.infinitylabs.dynamictrackerapp.base_view.BaseView;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.MainUtility;


public abstract class BaseFragment extends Fragment implements BaseScreenView, BaseView {

    private static final String LOG_TAG = "BaseFragment";
    protected static final int REQUEST_CODE_CAMERA = 1301;

    protected Context context;
    private LinearLayout llProgressMain;
    private FrameLayout flFragmentLayout;
    protected LayoutInflater inflater;
    private TextView tvLoadingMessage;
    protected Bitmap bitmap;
    private int typeOfImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDataForScreen();
        this.inflater = inflater;
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        llProgressMain = (LinearLayout) view.findViewById(R.id.ll_progress_main);
        flFragmentLayout = (FrameLayout) view.findViewById(R.id.fl_fragment_layout);
        tvLoadingMessage = (TextView) view.findViewById(R.id.tv_loading_message);
        flFragmentLayout.addView(getFragmentLayout());
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.logError(LOG_TAG, "onAttach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.logError(LOG_TAG, "onActivityCreated");
        context = getActivity();
        initializeViews();
        initializePresenter();
    }

    @Override
    public void getDataForScreen() {
        // empty implementation
    }


    protected LinearLayout getLlProgressMain() {
        return llProgressMain;
    }

    @Override
    public void showLoadingView(int message) {
        llProgressMain.setVisibility(View.VISIBLE);
        tvLoadingMessage.setText(MainUtility.getStringFromXml(context, message));
    }

    @Override
    public void hideLoadingView() {
        llProgressMain.setVisibility(View.GONE);
    }

    public abstract View getFragmentLayout();


    protected void replaceFragment(int frameId, Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(frameId, fragment);
        ft.commitAllowingStateLoss();
    }

    public void takePhoto(int typeOfImage) {
        this.typeOfImage = typeOfImage;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity()
                .startActivityFromFragment(this, cameraIntent, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                Logger.logError(LOG_TAG, "onActivityResult() : TAKE_PICTURE");
                if (resultCode == Activity.RESULT_OK) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    refreshViewOnSuccessIfAny(requestCode, typeOfImage);
                }
                break;

            default:
                Logger.logError(LOG_TAG, "onActivityResult() : TAKE_PICTURE");
                if (resultCode == Activity.RESULT_OK) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    refreshViewOnSuccessIfAnyForOthers(requestCode, typeOfImage,data,resultCode);
                }
        }
    }


    protected void refreshViewOnSuccessIfAny(int requestCode, int typeOfImage) {

    }

    protected void refreshViewOnSuccessIfAnyForOthers(int requestCode, int typeOfImage, Intent data, int resultCode) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.logError(LOG_TAG, "onDestroyView");
    }
}
