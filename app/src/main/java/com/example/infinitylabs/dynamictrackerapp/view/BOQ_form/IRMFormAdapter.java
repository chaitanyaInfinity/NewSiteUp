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

import java.util.List;

/**
 * Created by infinitylabs on 8/12/17.
 */


public class IRMFormAdapter extends RecyclerView.Adapter<IRMFormAdapter.MyViewHolder> {

    private List<BOQFormData> boqFormDataList;
    Context mContext;
    IRMFragment irmFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public String LOG_TAG = IRMFormAdapter.class.getSimpleName();
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

    public IRMFormAdapter(List<BOQFormData> boqFormDataList, Context context, IRMFragment irmFragment) {
        this.boqFormDataList = boqFormDataList;
        this.mContext = context;
        this.irmFragment = irmFragment;
    }

    @Override
    public IRMFormAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.irm_row_list, parent, false);

        return new IRMFormAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final IRMFormAdapter.MyViewHolder holder, int position) {
        final BOQFormData boqFormData = boqFormDataList.get(position);
        holder.partNo.setText(boqFormData.getLabel());
        holder.description.setText(boqFormData.getDescription());
        holder.quantity.setText(boqFormData.getQuantity());
        boqFormData.setAnswer("yes");


        holder.tbSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.comment.setVisibility(View.GONE);
                    boqFormData.setAnswer("yes");

                } else {
                    holder.comment.setVisibility(View.VISIBLE);
                    boqFormData.setAnswer("no");
                }
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

