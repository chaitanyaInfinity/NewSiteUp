package com.example.infinitylabs.dynamictrackerapp.view.preview_screen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.base_view.fragements.BaseFragment;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.BOQFormData;
import com.example.infinitylabs.dynamictrackerapp.request_response.GetBOQIRMRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.GetBOQIRMResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.PreviewData;
import com.example.infinitylabs.dynamictrackerapp.request_response.PreviewRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.PreviewResponse;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTaskUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTrackingDataUtility;
import com.example.infinitylabs.dynamictrackerapp.view.BOQ_form.BOQFormAdapter;
import com.example.infinitylabs.dynamictrackerapp.view.BOQ_form.BOQFragment;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewFragment extends BaseFragment {

private static final String LOG_TAG = PreviewFragment.class.getSimpleName();


    @BindView(R.id.rv)
    RecyclerView rv;
//    @BindView(R.id.btn_back)
//    Button btnBack;
    Unbinder unbinder;

    public PreviewFragment() {
        // Required empty public constructor
    }

    public static PreviewFragment newInstance() {

        Bundle args = new Bundle();

        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private List<PreviewData> previewDataList = new ArrayList<>();
    private PreviewAdapter previewAdapter;


    @Override
    public View getFragmentLayout() {
        View view = inflater.inflate(R.layout.fragment_preview, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initializeViews() {

        showLoadingView(R.id.please_wait);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void initializePresenter() {

        PreviewRequest previewRequest = new PreviewRequest();
        previewRequest.setSiteId(SelectedTaskUtility.getInstance().getSiteID());
        previewRequest.setFormId(SelectedTrackingDataUtility.getInstance().getFormId());
        callAPI(previewRequest, new APIClientUtils(context));

    }


    private void callAPI(PreviewRequest previewRequest, APIClientUtils apiClientUtils) {


        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getTaskPreview(),
                    new APIClientCallback<PreviewResponse>() {

                        @Override
                        public void onSuccess(PreviewResponse previewResponse) {


                            hideLoadingView();

                            previewDataList = previewResponse.getData();


                            if (previewDataList != null) {


                                previewAdapter = new PreviewAdapter(previewDataList, context, PreviewFragment.this);
                                rv.setAdapter(previewAdapter);
                            } else {
                            }

                        }

                        @Override
                        public void onFailure(Exception e) {
                            hideLoadingView();
                            Logger.logError(LOG_TAG, "Exception " + e);
                        }

                        @NonNull
                        @Override
                        public Class<PreviewResponse> getClassOfType() {
                            return PreviewResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(previewRequest)));
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @OnClick(R.id.btn_back)
//    public void onViewClicked() {
//
//    }
}
