package com.example.infinitylabs.dynamictrackerapp.view.tracking;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.base_view.fragements.BaseFragment;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.TrackingData;
import com.example.infinitylabs.dynamictrackerapp.request_response.TrackingRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.TrackingResponse;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTaskUtility;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackingFragment extends BaseFragment {

    private static final String LOG_TAG = TrackingFragment.class.getSimpleName();


    @BindView(R.id.rv_tracking_list)
    RecyclerView rvTrackingList;
    Unbinder unbinder;
    @BindView(R.id.srl_tracking_list)
    SwipeRefreshLayout srlTrackingList;


    private List<TrackingData> trackingList = new ArrayList<>();
    private TrackingAdapter trackingAdapter;

    public TrackingFragment() {
        // Required empty public constructor
    }

    public static TrackingFragment newInstance() {

        Bundle args = new Bundle();

        TrackingFragment fragment = new TrackingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View getFragmentLayout() {
        View view = inflater.inflate(R.layout.fragment_tracking, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void initializeViews() {

//        List<String> taskList = new ArrayList<>();
//        taskList.add("Create Site");
//        taskList.add("Approve Site Information from Vendor");
//        taskList.add("Approve Site Information from Cisco");
//        taskList.add("Approve Site Information from Customer");
//        taskList.add("Schedule Site Survey");
//        taskList.add("Approve Site Survey from Customer");
//        taskList.add("Assign Site Survey tasks filed engineer");
//        taskList.add("Update Tasks");
//        taskList.add("Task Approval from Vendor");
//        taskList.add("Task Approval from Cisco");
//        taskList.add("Task Approval from Customer");
//        taskList.add("Site BOQ Preparation");
//        taskList.add("Site BOQ Approval from Vendor");
//        taskList.add("Site BOQ Approval from Cisco");
//        taskList.add("Site BOQ Approval from Customer");
//        taskList.add("BOQ Dispatch instruction sent to WH");
//        taskList.add("BOQ Dispatch confirmed");
//        taskList.add("Material Delivered at Site");
//        taskList.add("Open case Inspection");
//        taskList.add("Site Power confirmed");
//        taskList.add("Rack Space confirmed");
//        taskList.add("Media-1 confirmed");
//        taskList.add("Media-2 confirmed");
//        taskList.add("NDD/NIP Confirmed");
//        taskList.add("Schedule Installation");
//        taskList.add("Intimation sent to all PMs");
//        taskList.add("Assign Installation Task to Field Engineer");
//        taskList.add("Field Engineer Update Task");
//        taskList.add("Task Approval from Vendor");
//        taskList.add("Task Approval from Cisco");
//        taskList.add("Task Approval from Customer");
//        taskList.add("HOTO");
//
//
//        vsvStepView
//                .setStepsViewIndicatorComplectingPosition(15)
//                .reverseDraw(false)
//                .setStepViewTexts(taskList)
//                .setLinePaddingProportion(0.85f)
//                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.dark_green))
//                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.dark_gray))
//                .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray))
//                .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.dark_gray))// text未完成线的颜色
//                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.completed_green))//设置StepsViewIndicator CompleteIcon
//                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.pending_gray))//设置StepsViewIndicator DefaultIcon
//                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon
//
//
//        vsvStepView.ondrawIndicator
        showLoadingView(R.id.please_wait);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        rvTrackingList.setLayoutManager(mLayoutManager);
        rvTrackingList.setItemAnimator(new DefaultItemAnimator());


    }

    @Override
    public void initializePresenter() {


        final TrackingRequest trackingRequest = new TrackingRequest();
        trackingRequest.setSiteId(SelectedTaskUtility.getInstance().getSiteID());
        callAPI(trackingRequest, new APIClientUtils(context));

        trackingAdapter = new TrackingAdapter(trackingList, context, TrackingFragment.this);
        rvTrackingList.setAdapter(trackingAdapter);


        srlTrackingList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI(trackingRequest, new APIClientUtils(context));
            }
        });


    }


    public void callAPI(TrackingRequest trackingRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getTracking(),
                    new APIClientCallback<TrackingResponse>() {

                        @Override
                        public void onSuccess(TrackingResponse mainResponse) {


                            hideLoadingView();
                            srlTrackingList.setRefreshing(false);

                            trackingList = mainResponse.getData();

                            if (trackingList != null) {
                                trackingAdapter = new TrackingAdapter(trackingList, context, TrackingFragment.this);
                                rvTrackingList.setAdapter(trackingAdapter);
                            } else {
                            }

                        }

                        @Override
                        public void onFailure(Exception e) {
                            hideLoadingView();
                            srlTrackingList.setRefreshing(false);
                            Logger.logError(LOG_TAG, "Exception " + e);
                        }

                        @NonNull
                        @Override
                        public Class<TrackingResponse> getClassOfType() {
                            return TrackingResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(trackingRequest)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }


}
