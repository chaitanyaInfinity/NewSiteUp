package com.example.infinitylabs.dynamictrackerapp.view.preview_screen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.request_response.PreviewData;

import java.util.List;

/**
 * Created by infinitylabs on 19/1/18.
 */

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.MyViewHolder> {

    private static final String LOG_TAG = PreviewAdapter.class.getSimpleName();

    private List<PreviewData> previewDataList;
    Context mContext;
    PreviewFragment previewFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public String LOG_TAG = PreviewAdapter.class.getSimpleName();
        public TextView sectionName, taskName, valueText;
        public ImageView valuePhoto;

        public MyViewHolder(View view) {
            super(view);
            sectionName = (TextView) view.findViewById(R.id.tv_section_name);
            taskName = (TextView) view.findViewById(R.id.tv_task_name);
            valueText = (TextView) view.findViewById(R.id.tv_text_output);
            valuePhoto = (ImageView) view.findViewById(R.id.iv_photo_output);
        }
    }

    public PreviewAdapter(List<PreviewData> previewDataList, Context context, PreviewFragment previewFragment) {
        this.previewDataList = previewDataList;
        this.mContext = context;
        this.previewFragment = previewFragment;
    }

    @Override
    public PreviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.preview_row_list, parent, false);

        return new PreviewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PreviewAdapter.MyViewHolder holder, int position) {
        final PreviewData previewData = previewDataList.get(position);

        if (previewData.getSection().equalsIgnoreCase("")) {
            holder.sectionName.setVisibility(View.GONE);
        } else {
            holder.sectionName.setVisibility(View.VISIBLE);
            holder.sectionName.setText(previewData  .getSection());
        }
        holder.taskName.setText(previewData.getTask());

        if (previewData.getAnswerType().equalsIgnoreCase("Photo") || previewData.getAnswerType().equalsIgnoreCase("Signature")) {
            holder.valueText.setVisibility(View.GONE);
            holder.valuePhoto.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(previewData.getValue()) // image url
                    .placeholder(R.drawable.loading) // any placeholder to load at start
                    .error(R.drawable.error)  // any image in case of error
                        .override(200, 200)
                    .centerCrop()
                    .into(holder.valuePhoto);  // imageview object

        } else {
            holder.valueText.setVisibility(View.VISIBLE);
            holder.valuePhoto.setVisibility(View.GONE);
            holder.valueText.setText(previewData.getValue());
        }

    }

    @Override
    public int getItemCount() {
        return previewDataList.size();
    }

}

