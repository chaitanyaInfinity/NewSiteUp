package com.example.infinitylabs.dynamictrackerapp.view.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.request_response.SectionData;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedSectionUtility;
import com.example.infinitylabs.dynamictrackerapp.view.question.QuestionActivity;

import java.util.List;

/**
 * Created by infinitylabs on 4/7/17.
 */

public class SectionListAdapter extends RecyclerView.Adapter<SectionListAdapter.MyViewHolder> {

    private List<SectionData> sectionDataList;
    Context mContext;
    SectionListFragment sectionListFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public String LOG_TAG = SectionListAdapter.class.getSimpleName();
        public TextView  sectionName;
        public ImageView completed, pending, blank, arrow;

        public MyViewHolder(View view) {
            super(view);
            sectionName = (TextView) view.findViewById(R.id.tv_section_name);
            completed = (ImageView) view.findViewById(R.id.ib_checked);
            pending = (ImageView) view.findViewById(R.id.ib_pending);
            blank = (ImageView) view.findViewById(R.id.ib_blank);
            arrow = (ImageView) view.findViewById(R.id.img_arrow_enter);
        }
    }

    public SectionListAdapter(List<SectionData> taskModelList, Context context, SectionListFragment taskListFragment) {
        this.sectionDataList = taskModelList;
        this.mContext = context;
        this.sectionListFragment = taskListFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_section_row_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SectionData sectionData = sectionDataList.get(position);
        holder.sectionName.setText(sectionData.getSectionName());

        if (sectionData.getSectionStatus().equals("complete")) {
            holder.completed.setVisibility(View.VISIBLE);
            holder.pending.setVisibility(View.GONE);
            holder.blank.setVisibility(View.GONE);
            holder.arrow.setVisibility(View.VISIBLE);


        } else if (sectionData.getSectionStatus().equals("inprogress")) {
            holder.completed.setVisibility(View.GONE);
            holder.pending.setVisibility(View.VISIBLE);
            holder.blank.setVisibility(View.GONE);
            holder.arrow.setVisibility(View.VISIBLE);

        } else if (sectionData.getSectionStatus().equals("incomplete")) {
            holder.completed.setVisibility(View.GONE);
            holder.pending.setVisibility(View.GONE);
            holder.blank.setVisibility(View.VISIBLE);
            holder.arrow.setVisibility(View.VISIBLE);

        }

        int pendingCount = 0;

        for(int i=0;i<=sectionDataList.size()-1;i++){



            if(sectionDataList.get(i).getSectionStatus().equalsIgnoreCase("inprogress")||
                    sectionDataList.get(i).getSectionStatus().equalsIgnoreCase("incomplete") ){
                pendingCount=pendingCount+1;
            }
        }

        if(pendingCount==0){
            sectionListFragment.showSubmitBtn();
        }else {
            sectionListFragment.hideSubmitBtn();
        }


//        if(position==sectionDataList.size()-1){
//            holder.arrow.setVisibility(View.INVISIBLE);
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionListFragment.showLoadingView(R.id.please_wait);
                SelectedSectionUtility.getInstance()
                        .setSelectedSectionData(sectionDataList.get(holder.getAdapterPosition()));

                QuestionActivity.launch(mContext);

            }
        });
    }

    @Override
    public int getItemCount() {
        return sectionDataList.size();
    }
}