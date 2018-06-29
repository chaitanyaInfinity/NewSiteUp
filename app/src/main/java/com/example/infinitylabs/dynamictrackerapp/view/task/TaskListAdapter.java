package com.example.infinitylabs.dynamictrackerapp.view.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.request_response.Data;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTaskUtility;
import com.example.infinitylabs.dynamictrackerapp.view.tracking.TrackingActivity;

import java.util.List;

/**
 * Created by infinitylabs on 5/7/17.
 */


public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {

    private static final String LOG_TAG = TaskListAdapter.class.getSimpleName();

    private List<Data> taskDataList;
    Context mContext;
    TaskListFragment taskListFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public String LOG_TAG = TaskListAdapter.class.getSimpleName();
        public TextView siteId, location, date, siteName;

        public MyViewHolder(View view) {
            super(view);

            siteName = (TextView)view.findViewById(R.id.tv_site_name);
            siteId = (TextView) view.findViewById(R.id.tv_site_id);
            location = (TextView) view.findViewById(R.id.tv_site_location);
            date = (TextView) view.findViewById(R.id.tv_site_date);
        }
    }

    public TaskListAdapter(List<Data> taskModelList, Context context, TaskListFragment taskListFragment) {
        this.taskDataList = taskModelList;
        this.mContext = context;
        this.taskListFragment = taskListFragment;
    }

    @Override
    public TaskListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_site_row_list, parent, false);

        return new TaskListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TaskListAdapter.MyViewHolder holder, int position) {
        Data assignedSitesModel = taskDataList.get(position);
        holder.location.setText(assignedSitesModel.getCity());
        holder.date.setText(assignedSitesModel.getDate());
        holder.siteName.setText(assignedSitesModel.getSiteName());
        holder.siteId.setText(assignedSitesModel.getSiteId());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskListFragment.showLoadingView(R.id.please_wait);
                SelectedTaskUtility.getInstance()
                        .setSelectedTaskData(taskDataList.get(holder.getAdapterPosition()));

//                if (SelectedTaskUtility.getInstance().getSiteCompletionStatus().equalsIgnoreCase("accepted")) {
//                    SectionListActivity.launch(mContext);
//                } else if(SelectedTaskUtility.getInstance().getSiteCompletionStatus().equalsIgnoreCase("completed")){
//                    taskListFragment.hideLoadingView();
//                    MainUtility.showMessage(holder.date,"Site Already Completed");
//                }else  {
//                    SiteApprovalActivity.launch(mContext);
//                }
                taskListFragment.hideLoadingView();
                TrackingActivity.launch(mContext);
            }
        });
    }


    @Override
    public int getItemCount() {
        return taskDataList.size();
    }
}


