package com.example.infinitylabs.dynamictrackerapp.view.BOQ_form;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.request_response.BOQFormData;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;


import java.util.List;

/**
 * Created by infinitylabs on 8/12/17.
 */


public class BOQFormAdapter extends RecyclerView.Adapter<BOQFormAdapter.MyViewHolder> {

    private static final String LOG_TAG = BOQFormAdapter.class.getSimpleName();

    private List<BOQFormData> boqFormDataList;
    Context mContext;
    BOQFragment boqFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public String LOG_TAG = BOQFormAdapter.class.getSimpleName();
        public TextView partNo, description, quantity;
        public Switch tbSwitch;
        public EditText comment;

        public MyViewHolder(View view) {
            super(view);
            partNo = (TextView) view.findViewById(R.id.tv_part_no);
            description = (TextView) view.findViewById(R.id.tv_description);
            quantity = (TextView) view.findViewById(R.id.tv_quantity);
            tbSwitch = (Switch) view.findViewById(R.id.switch_btn);
            comment = (EditText) view.findViewById(R.id.et_comment);
        }
    }

    public BOQFormAdapter(List<BOQFormData> boqFormDataList, Context context, BOQFragment boqFragment) {
        this.boqFormDataList = boqFormDataList;
        this.mContext = context;
        this.boqFragment = boqFragment;
    }

    @Override
    public BOQFormAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.boq_row_list, parent, false);

        return new BOQFormAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BOQFormAdapter.MyViewHolder holder, int position) {
        final BOQFormData boqFormData = boqFormDataList.get(position);
        holder.setIsRecyclable(false);
        holder.partNo.setText(boqFormData.getLabel());
        holder.description.setText(boqFormData.getDescription());
        holder.quantity.setText(boqFormData.getQuantity());
        holder.comment.setText(boqFormData.getComment());

        if (boqFormData.getAnswer().equalsIgnoreCase("yes")) {
            holder.tbSwitch.setChecked(true);
            holder.comment.setHint("Enter Serial Number");
        } else {
            holder.tbSwitch.setChecked(false);
            holder.comment.setHint("Enter Comment");
        }

        holder.tbSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    boqFormData.setAnswer("yes");
                    holder.comment.setHint("Enter Serial Number");
                } else {
                    boqFormData.setAnswer("no");
                    holder.comment.setHint("Enter Comment");
                }

                Logger.logError(LOG_TAG, "Size " + getBoqFormDataList().toString());

            }
        });


        holder.comment.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
//                    Logger.logError("112233","ONtext changed " + new String(s.toString()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
//                    Logger.logError("112233","beforeTextChanged " + new String(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
//                    Logger.logError("112233","afterTextChanged " + new String(s.toString()));
                boqFormData.setComment(s.toString());
                Logger.logError(LOG_TAG, "Size " + getItemCount());
            }
        });


    }

    @Override
    public int getItemCount() {
        return boqFormDataList.size();
    }

    public List<BOQFormData> getBoqFormDataList() {
        return boqFormDataList;
    }
}
