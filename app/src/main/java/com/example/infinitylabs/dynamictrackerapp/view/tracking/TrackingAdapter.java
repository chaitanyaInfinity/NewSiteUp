package com.example.infinitylabs.dynamictrackerapp.view.tracking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.request_response.TrackingData;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTrackingDataUtility;
import com.example.infinitylabs.dynamictrackerapp.view.BOQ_form.BOQFormActivity;
import com.example.infinitylabs.dynamictrackerapp.view.nip_upload_form.NipUploadActivity;
import com.example.infinitylabs.dynamictrackerapp.view.section.SectionListActivity;

import java.util.List;

/**
 * Created by infinitylabs on 7/11/17.
 */


public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.MyViewHolder> {

    private static final String LOG_TAG = TrackingAdapter.class.getSimpleName();

    private List<TrackingData> trackDataList;
    Context mContext;

    TrackingFragment trackingFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public String LOG_TAG = TrackingAdapter.class.getSimpleName();

        TextView stepName;
        TextView stepDetails;
        ImageView stepImage;
        View stepLineTop;
        View getStepLineBottom;
        ImageView imgArrowEnter;

        public MyViewHolder(View view) {
            super(view);
            stepImage = (ImageView) view.findViewById(R.id.img_check_stop);
            stepName = (TextView) view.findViewById(R.id.tv_step_name);
            stepDetails = (TextView) view.findViewById(R.id.tv_step_details);
            stepLineTop = view.findViewById(R.id.view_line_top);
            getStepLineBottom = view.findViewById(R.id.view_line_bottom);
            imgArrowEnter = (ImageView) view.findViewById(R.id.img_arrow_enter);
        }
    }

    public TrackingAdapter(List<TrackingData> taskModelList, Context context, TrackingFragment trackingFragment) {
        this.trackDataList = taskModelList;
        this.mContext = context;
        this.trackingFragment = trackingFragment;
    }

    @Override
    public TrackingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_view_row_list, parent, false);

        return new TrackingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TrackingAdapter.MyViewHolder holder, int position) {
        final TrackingData data = trackDataList.get(position);

        Logger.logError(LOG_TAG, "Tracking Data " + data + " size " + trackDataList.size());

//        if (position == 0) {
//            holder.stepLineTop.setVisibility(View.INVISIBLE);
//        }
//
//        if (position == trackDataList.size() - 1) {
//            holder.getStepLineBottom.setVisibility(View.INVISIBLE);
//        }

        holder.stepName.setText(data.getName());

        if (!data.getDetails().equalsIgnoreCase("")) {
            holder.stepDetails.setVisibility(View.VISIBLE);
            holder.stepDetails.setText(data.getDetails());
        } else {
            holder.stepDetails.setVisibility(View.GONE);
        }


        Logger.logError(LOG_TAG, "status " + data.getStatus());

        if (data.getStatus() != null) {

            if (data.getStatus().equalsIgnoreCase("completed") || data.getStatus().equalsIgnoreCase("approved")) {

                holder.stepImage.setBackgroundResource(R.drawable.completed_green);


            } else if (data.getStatus().equalsIgnoreCase("pending") || data.getStatus().equalsIgnoreCase("open")) {

                holder.stepImage.setBackgroundResource(R.drawable.attention);

            } else {

                holder.stepImage.setBackgroundResource(R.drawable.pending_gray);

            }

        } else {
            holder.stepImage.setBackgroundResource(R.drawable.pending_gray);
        }


        if (data.getAccess()) {

            holder.imgArrowEnter.setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SelectedTrackingDataUtility.getInstance().setSelectedTrackingData(data);

                    if (data.getFormId().equalsIgnoreCase("irm_boq")) {
                        BOQFormActivity.launch(mContext);
                    } else if (data.getFormId().equalsIgnoreCase("nip_upload")) {
                        NipUploadActivity.launch(mContext);
                    } else {
                        SectionListActivity.launch(mContext);
                    }

                }
            });


        } else {
            holder.itemView.setClickable(false);
            holder.imgArrowEnter.setVisibility(View.INVISIBLE);
        }


    }


    @Override
    public int getItemCount() {
        return trackDataList.size();
    }
}



