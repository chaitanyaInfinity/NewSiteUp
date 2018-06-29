package com.example.infinitylabs.dynamictrackerapp.view.task;


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
import com.example.infinitylabs.dynamictrackerapp.request_response.Data;
import com.example.infinitylabs.dynamictrackerapp.request_response.MainResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.TaskRequest;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.LoginResponseUtility;
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
public class TaskListFragment extends BaseFragment {

    @BindView(R.id.rv_assigned_sites)
    RecyclerView rvAssignedSites;
    Unbinder unbinder;
    private static final String LOG_TAG = "SectionListFragment";
    @BindView(R.id.srl_site_list)
    SwipeRefreshLayout srlSiteList;


    private List<Data> taskModelList = new ArrayList<>();
    private TaskListAdapter taskListAdapter;

    public static TaskListFragment newInstance() {

        Bundle args = new Bundle();

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public TaskListFragment() {
        // Required empty public constructor
    }


    @Override
    public View getFragmentLayout() {
        View view = inflater.inflate(R.layout.fragment_task_list, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initializeViews() {
        showLoadingView(R.id.please_wait);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        rvAssignedSites.setLayoutManager(mLayoutManager);
        rvAssignedSites.setItemAnimator(new DefaultItemAnimator());
        Logger.logError(LOG_TAG, "initializeViews");
    }

    @Override
    public void initializePresenter() {

        Logger.logError(LOG_TAG, "initializePresenter");


        final TaskRequest taskRequest = new TaskRequest();
        taskRequest.setMobileNo(LoginResponseUtility.getInstance().getMobileNumber());
        taskRequest.setAccessToken(LoginResponseUtility.getInstance().getAccessToken());

        callAPI(taskRequest, new APIClientUtils(context));

        srlSiteList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callAPI(taskRequest, new APIClientUtils(context));
            }
        });

//        Data data =new Data("","","Dummy Site A","26/12/2019","Sangli","","","","Pune","","Smita Gursali","9879878732");
//        taskModelList.add(data);
//
//        data =new Data("","","Dummy Site B","26/12/2019","Satara","","","","Pune","","Smita Gursali","9879878732");
//        taskModelList.add(data);
//
//        data =new Data("","","Dummy Site C","26/12/2019","Kolhapur","","","","Pune","","Smita Gursali","9879878732");
//        taskModelList.add(data);
//
//
//        data =new Data("","","Dummy Site D","26/12/2019","Pune","","","","","","Smita Gursali","9879878732");
//        taskModelList.add(data);

        taskListAdapter = new TaskListAdapter(taskModelList, context, TaskListFragment.this);
        rvAssignedSites.setAdapter(taskListAdapter);

    }


    public void callAPI(TaskRequest taskRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getDummySitesList(),
                    new APIClientCallback<MainResponse>() {

                        @Override
                        public void onSuccess(MainResponse mainResponse) {


                            hideLoadingView();
                            srlSiteList.setRefreshing(false);

                            taskModelList = mainResponse.getData();

                            if (taskModelList != null) {
                                taskListAdapter = new TaskListAdapter(taskModelList, context, TaskListFragment.this);
                                rvAssignedSites.setAdapter(taskListAdapter);
                            } else {
                            }

                        }

                        @Override
                        public void onFailure(Exception e) {
                            hideLoadingView();
                            srlSiteList.setRefreshing(false);
                            Logger.logError(LOG_TAG, "Exception " + e);
                        }

                        @NonNull
                        @Override
                        public Class<MainResponse> getClassOfType() {
                            return MainResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(taskRequest)));
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
