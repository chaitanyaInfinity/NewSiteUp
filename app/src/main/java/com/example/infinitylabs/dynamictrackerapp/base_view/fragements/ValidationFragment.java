package com.example.infinitylabs.dynamictrackerapp.base_view.fragements;

/**
 * Created by nileshjarad on 08/02/17.
 */

public abstract class ValidationFragment extends BaseFragment {
    public abstract void setDataIfPresent();

    public abstract boolean isScreenValidData();

    public abstract void setDataToUtility();

    @Override
    public void onResume() {
        super.onResume();
        setDataIfPresent();
    }

    @Override
    public void onPause() {
        super.onPause();
        setDataToUtility();
    }
}
