package com.example.infinitylabs.dynamictrackerapp.view.section;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infinitylabs.dynamictrackerapp.IssueActivity;
import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.base_view.fragements.BaseFragment;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.MainResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.SectionData;
import com.example.infinitylabs.dynamictrackerapp.request_response.SectionResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.ServeySectionRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.SiteStatusRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.SubmitServeRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.SubmitServeyResponse;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTaskUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTrackingDataUtility;
import com.example.infinitylabs.dynamictrackerapp.view.preview_screen.PreviewActivity;
import com.example.infinitylabs.dynamictrackerapp.view.task.TaskListActivity;
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
public class SectionListFragment extends BaseFragment {


    @BindView(R.id.tv_section_header)
    TextView tvSectionHeader;
    @BindView(R.id.ib_issue)
    ImageButton ibIssue;
    @BindView(R.id.btn_submit_sections)
    Button btnSubmitSections;
    @BindView(R.id.srl_section_list)
    SwipeRefreshLayout srlSectionList;
    @BindView(R.id.ib_preview)
    ImageButton ibPreview;

    public static SectionListFragment newInstance() {

        Bundle args = new Bundle();

        SectionListFragment fragment = new SectionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String LOG_TAG = "SectionListFragment";
    @BindView(R.id.rv_assigned_sites)
    RecyclerView rvAssignedSites;
    Unbinder unbinder;

    private List<SectionData> taskModelList = new ArrayList<>();
    private SectionListAdapter taskListAdapter;

    public SectionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View getFragmentLayout() {
        View view = inflater.inflate(R.layout.fragment_section_list, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initializeViews() {
        showLoadingView(R.id.please_wait);

        if(SelectedTrackingDataUtility.getInstance().isHardwareAT()){
            ibPreview.setVisibility(View.GONE);
        }else {
            ibPreview.setVisibility(View.VISIBLE);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        rvAssignedSites.setLayoutManager(mLayoutManager);
        rvAssignedSites.setItemAnimator(new DefaultItemAnimator());
        tvSectionHeader.setText(SelectedTaskUtility.getInstance().getSiteName());
    }

    @Override
    public void initializePresenter() {

        final ServeySectionRequest sectionRequest = new ServeySectionRequest();
        sectionRequest.setSiteId(SelectedTaskUtility.getInstance().getSiteID());
        sectionRequest.setFormId(SelectedTrackingDataUtility.getInstance().getFormId());
        callAPI(sectionRequest, new APIClientUtils(context));


        srlSectionList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI(sectionRequest, new APIClientUtils(context));
            }
        });

    }


    public void callAPI(ServeySectionRequest sectionRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getSectionList(),
                    new APIClientCallback<SectionResponse>() {

                        @Override
                        public void onSuccess(SectionResponse sectionResponse) {


                            hideLoadingView();
                            if (srlSectionList != null) {
                                srlSectionList.setRefreshing(false);
                            }

                            taskModelList = sectionResponse.getData();


                            if (taskModelList != null) {

                                taskListAdapter = new SectionListAdapter(taskModelList, context, SectionListFragment.this);
                                rvAssignedSites.setAdapter(taskListAdapter);
                            } else {
                            }

                        }

                        @Override
                        public void onFailure(Exception e) {
                            hideLoadingView();
                            if (srlSectionList != null) {
                                srlSectionList.setRefreshing(false);
                            }
                            Logger.logError(LOG_TAG, "Exception " + e);
                        }

                        @NonNull
                        @Override
                        public Class<SectionResponse> getClassOfType() {
                            return SectionResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(sectionRequest)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.ib_preview, R.id.ib_issue, R.id.btn_submit_sections})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_preview:
                PreviewActivity.launch(getActivity());
                break;
            case R.id.ib_issue:
                IssueActivity.launch(getActivity());
                break;

            case R.id.btn_submit_sections:
                showLoadingView(R.id.please_wait);
                SubmitServeRequest submitServeRequest = new SubmitServeRequest();
                submitServeRequest.setSiteId(SelectedTaskUtility.getInstance().getSiteID());
                submitServeRequest.setFormId(SelectedTrackingDataUtility.getInstance().getFormId());
                callSubmitSectionAPI(submitServeRequest, new APIClientUtils(context));
                break;
        }
    }

    private void callSubmitSectionAPI(SubmitServeRequest submitServeRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getSubmitSurveyForm(),
                    new APIClientCallback<SubmitServeyResponse>() {

                        @Override
                        public void onSuccess(SubmitServeyResponse mainResponse) {
                            hideLoadingView();
                            Toast.makeText(context, mainResponse.getMsg(), Toast.LENGTH_LONG).show();

                            if (mainResponse.getStatus()) {
                                btnSubmitSections.setEnabled(false);
                                btnSubmitSections.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                            }
                        }

                        @Override
                        public void onFailure(Exception e) {
                            hideLoadingView();
                            Logger.logError(LOG_TAG, "Exception " + e);
                        }

                        @NonNull
                        @Override
                        public Class<SubmitServeyResponse> getClassOfType() {
                            return SubmitServeyResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(submitServeRequest)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

//    public void callAPIForSIteCompletion(SiteStatusRequest siteStatusRequest, APIClientUtils apiClientUtils) {
//
//        try {
//            apiClientUtils.getServiceResponseByPost(UrlConstant.getSiteApproval(),
//                    new APIClientCallback<MainResponse>() {
//
//                        @Override
//                        public void onSuccess(MainResponse mainResponse) {
//                            hideLoadingView();
//                            TaskListActivity.launch(getActivity());
//                        }
//
//                        @Override
//                        public void onFailure(Exception e) {
//                            hideLoadingView();
//                            Logger.logError(LOG_TAG, "Exception " + e);
//                        }
//
//                        @NonNull
//                        @Override
//                        public Class<MainResponse> getClassOfType() {
//                            return MainResponse.class;
//                        }
//
//                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(siteStatusRequest)));
//        } catch (JSONException | JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }

//    protected void showDialog(Activity activity, String title, CharSequence message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
//
//        if (title != null) builder.setTitle(title);
//
//        builder.setMessage(message);
//        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                showLoadingView(R.id.please_wait);
//                SiteStatusRequest siteStatusRequest1 = new SiteStatusRequest();
//                siteStatusRequest1.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
//                siteStatusRequest1.setStatus("completed");
//
//                callAPIForSIteCompletion(siteStatusRequest1, new APIClientUtils(context));
//            }
//        });
//        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
//    }

    public void showSubmitBtn() {
        btnSubmitSections.setVisibility(View.VISIBLE);
    }

    public void hideSubmitBtn() {
        btnSubmitSections.setVisibility(View.GONE);
    }


    @OnClick(R.id.rv_assigned_sites)
    public void onViewClicked() {
    }
}
