package com.example.infinitylabs.dynamictrackerapp.view.question;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.QuestionListData;
import com.example.infinitylabs.dynamictrackerapp.request_response.QuestionRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.QuestionResponse;
import com.example.infinitylabs.dynamictrackerapp.request_response.SectionRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.SectionResponse;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedQuestionUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTaskUtility;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by infinitylabs on 5/7/17.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private static final String LOG_TAG = QuestionAdapter.class.getSimpleName();

    private List<QuestionListData> questionDataList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public String LOG_TAG = QuestionAdapter.class.getSimpleName();
        public TextView question;
        public ImageView checked, blank;

        public MyViewHolder(View view) {
            super(view);
            question = (TextView) view.findViewById(R.id.tv_question);
            checked = (ImageView) view.findViewById(R.id.ib_checked);
            blank = (ImageView) view.findViewById(R.id.ib_blank);
        }
    }

    public QuestionAdapter(List<QuestionListData> questionDataList, Context context) {
        this.questionDataList = questionDataList;
        this.mContext = context;
    }

    @Override
    public QuestionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_question_row_list, parent, false);

        return new QuestionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuestionAdapter.MyViewHolder holder, int position) {
        QuestionListData questionData = questionDataList.get(position);
        holder.question.setText(questionData.getTask());

        if (questionData.getTaskStatus()) {
            holder.checked.setVisibility(View.GONE);
            holder.blank.setVisibility(View.VISIBLE);
//            holder.itemView.setClickable(true);
//            holder.itemView.setEnabled(true);
//            holder.itemView.setFocusable(true);
        } else {
            holder.checked.setVisibility(View.VISIBLE);
            holder.blank.setVisibility(View.GONE);
//            holder.itemView.setClickable(false);
//            holder.itemView.setEnabled(false);
//            holder.itemView.setFocusable(false);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((QuestionActivity) mContext).showLoadingView(R.id.please_wait);
                QuestionRequest questionRequest = new QuestionRequest();
                questionRequest.setTaskId(questionDataList.get(holder.getAdapterPosition()).getTaskId());
                questionRequest.setSiteId(SelectedTaskUtility.getInstance().getSiteID());
                callAPI(questionRequest, new APIClientUtils(mContext));

            }
        });
    }

    public void callAPI(final QuestionRequest questionRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getGetQuestionforId(),
                    new APIClientCallback<QuestionResponse>() {

                        @Override
                        public void onSuccess(QuestionResponse questionResponse) {

                            if (questionResponse.getData() != null) {
                                SelectedQuestionUtility.getInstance().setSelectedQuestionData(questionResponse.getData());

                                setAnswerView(questionResponse);

                            }
                        }

                        @Override
                        public void onFailure(Exception e) {

                        }

                        @NonNull
                        @Override
                        public Class<QuestionResponse> getClassOfType() {
                            return QuestionResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(questionRequest)));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    private void setAnswerView(QuestionResponse questionResponse) {

        String answerType = questionResponse.getData().getType();

        Logger.logError(LOG_TAG, "answerType " + answerType);

        ((QuestionActivity) mContext).hideLoadingView();

        switch (answerType) {
            case "Radio":

                if (SelectedQuestionUtility.getInstance().isHardwareAt()) {
                    ((QuestionActivity) mContext).rgQuestion.setVisibility(View.VISIBLE);
                } else {
                    ((QuestionActivity) mContext).rgQuestion.setVisibility(View.GONE);
                }

                ((QuestionActivity) mContext).viewQuestion.setVisibility(View.VISIBLE);
                ((QuestionActivity) mContext).tvQuestion.setText(SelectedQuestionUtility.getInstance().getTask());

                if (SelectedQuestionUtility.getInstance().getDescription().equalsIgnoreCase("")) {
                    ((QuestionActivity) mContext).ibInfoQuestion.setVisibility(View.GONE);

                } else {
                    ((QuestionActivity) mContext).ibInfoQuestion.setVisibility(View.VISIBLE);
                    ((QuestionActivity) mContext).popupString = SelectedQuestionUtility.getInstance().getDescription();
//                    ((QuestionActivity) mContext).tvPopupText.setText(SelectedQuestionUtility.getInstance().getDescription());
                }

//                ((QuestionActivity) mContext).radioButton1.setText(SelectedQuestionUtility.getInstance().getFirstAnswerValue());
//                ((QuestionActivity) mContext).radioButton2.setText(SelectedQuestionUtility.getInstance().getSecondAnswerValue());
                ArrayList answerList = SelectedQuestionUtility.getInstance().getAnswerlist();


                ArrayAdapter<String> answerListAdapter = new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_spinner_item, answerList);
                answerListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ((QuestionActivity) mContext).spinnerQuestion.setAdapter(answerListAdapter);


                Animation slide_up1 = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
                ((QuestionActivity) mContext).viewQuestion.setAnimation(slide_up1);
                ((QuestionActivity) mContext).viewMainQuestion.setVisibility(View.GONE);

                break;
            case "Photo":

                if (SelectedQuestionUtility.getInstance().isHardwareAt()) {
                    ((QuestionActivity) mContext).rgCamera.setVisibility(View.VISIBLE);
                } else {
                    ((QuestionActivity) mContext).rgCamera.setVisibility(View.GONE);
                }

                ((QuestionActivity) mContext).viewCamera.setVisibility(View.VISIBLE);
                ((QuestionActivity) mContext).tvQuestionCamera.setText(SelectedQuestionUtility.getInstance().getTask());

                if (SelectedQuestionUtility.getInstance().getDescription().equalsIgnoreCase("")) {
                    ((QuestionActivity) mContext).ibInfoCamera.setVisibility(View.GONE);

                } else {
                    ((QuestionActivity) mContext).ibInfoCamera.setVisibility(View.VISIBLE);
                    ((QuestionActivity) mContext).popupString = SelectedQuestionUtility.getInstance().getDescription();
//                    ((QuestionActivity) mContext).tvPopupText.setText(SelectedQuestionUtility.getInstance().getDescription());
                }

                Animation slide_up2 = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
                ((QuestionActivity) mContext).viewCamera.setAnimation(slide_up2);
                ((QuestionActivity) mContext).viewMainQuestion.setVisibility(View.GONE);


                break;

            case "Location":

                if (SelectedQuestionUtility.getInstance().isHardwareAt()) {
                    ((QuestionActivity) mContext).rgLocation.setVisibility(View.VISIBLE);
                } else {
                    ((QuestionActivity) mContext).rgLocation.setVisibility(View.GONE);
                }
                ((QuestionActivity) mContext).viewLocation.setVisibility(View.VISIBLE);
                Animation slide_up3 = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
                ((QuestionActivity) mContext).viewLocation.setAnimation(slide_up3);
                ((QuestionActivity) mContext).viewMainQuestion.setVisibility(View.GONE);

                break;

            case "TextInput":

                Logger.logError(LOG_TAG, "dis pop " + SelectedQuestionUtility.getInstance().getDescription());

                if (SelectedQuestionUtility.getInstance().isHardwareAt()) {
                    ((QuestionActivity) mContext).rgComment.setVisibility(View.VISIBLE);
                } else {
                    ((QuestionActivity) mContext).rgComment.setVisibility(View.GONE);
                }

                ((QuestionActivity) mContext).viewComment.setVisibility(View.VISIBLE);
                ((QuestionActivity) mContext).tvQuestionComment.setText(SelectedQuestionUtility.getInstance().getTask());

                if (SelectedQuestionUtility.getInstance().getDescription().equalsIgnoreCase("")) {
                    ((QuestionActivity) mContext).ibInfoComment.setVisibility(View.GONE);

                } else {
                    ((QuestionActivity) mContext).ibInfoComment.setVisibility(View.VISIBLE);
                    ((QuestionActivity) mContext).popupString = SelectedQuestionUtility.getInstance().getDescription();
                }

                Animation slide_up4 = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
                ((QuestionActivity) mContext).viewComment.setAnimation(slide_up4);
                ((QuestionActivity) mContext).viewMainQuestion.setVisibility(View.GONE);

                break;

            case "Scanner":

                if (SelectedQuestionUtility.getInstance().isHardwareAt()) {
                    ((QuestionActivity) mContext).rgBarcode.setVisibility(View.VISIBLE);
                } else {
                    ((QuestionActivity) mContext).rgBarcode.setVisibility(View.GONE);
                }

                ((QuestionActivity) mContext).viewBarcode.setVisibility(View.VISIBLE);
                ((QuestionActivity) mContext).tvQuestionBarcode.setText(SelectedQuestionUtility.getInstance().getTask());

                if (SelectedQuestionUtility.getInstance().getDescription().equalsIgnoreCase("")) {
                    ((QuestionActivity) mContext).ibInfoBarcode.setVisibility(View.GONE);

                } else {
                    ((QuestionActivity) mContext).ibInfoBarcode.setVisibility(View.VISIBLE);
                    ((QuestionActivity) mContext).popupString = SelectedQuestionUtility.getInstance().getDescription();
                }

                Animation slide_up5 = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
                ((QuestionActivity) mContext).viewBarcode.setAnimation(slide_up5);
                ((QuestionActivity) mContext).viewMainQuestion.setVisibility(View.GONE);

                break;

            case "Signature":

                if (SelectedQuestionUtility.getInstance().isHardwareAt()) {
                    ((QuestionActivity) mContext).rgSignature.setVisibility(View.VISIBLE);
                } else {
                    ((QuestionActivity) mContext).rgSignature.setVisibility(View.GONE);
                }

                ((QuestionActivity) mContext).viewSignature.setVisibility(View.VISIBLE);
                ((QuestionActivity) mContext).tvQuestionSignature.setText(SelectedQuestionUtility.getInstance().getTask());

                if (SelectedQuestionUtility.getInstance().getDescription().equalsIgnoreCase("")) {
                    ((QuestionActivity) mContext).ibInfoSignature.setVisibility(View.GONE);

                } else {
                    ((QuestionActivity) mContext).ibInfoSignature.setVisibility(View.VISIBLE);
                    ((QuestionActivity) mContext).popupString = SelectedQuestionUtility.getInstance().getDescription();
//                    ((QuestionActivity) mContext).tvPopupText.setText(SelectedQuestionUtility.getInstance().getDescription());
                }

                Animation slide_up6 = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
                ((QuestionActivity) mContext).viewSignature.setAnimation(slide_up6);
                ((QuestionActivity) mContext).viewMainQuestion.setVisibility(View.GONE);

                break;

            case "RouterConfiguration":

                Logger.logError(LOG_TAG, "Hardware at value " + SelectedQuestionUtility.getInstance().isHardwareAt());

                if (SelectedQuestionUtility.getInstance().isHardwareAt()) {
                    ((QuestionActivity) mContext).rgRouterConfig.setVisibility(View.VISIBLE);
                } else {
                    ((QuestionActivity) mContext).rgRouterConfig.setVisibility(View.GONE);
                }

                ((QuestionActivity) mContext).viewRouterConfiguration.setVisibility(View.VISIBLE);
                ((QuestionActivity) mContext).tvQuestionRouterConfig.setText(SelectedQuestionUtility.getInstance().getTask());

                if (SelectedQuestionUtility.getInstance().getDescription().equalsIgnoreCase("")) {
                    ((QuestionActivity) mContext).ibInfoRouterConfig.setVisibility(View.GONE);

                } else {
                    ((QuestionActivity) mContext).ibInfoRouterConfig.setVisibility(View.VISIBLE);
                    ((QuestionActivity) mContext).popupString = SelectedQuestionUtility.getInstance().getDescription();
//                    ((QuestionActivity) mContext).tvPopupText.setText(SelectedQuestionUtility.getInstance().getDescription());
                }

                Animation slide_up7 = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
                ((QuestionActivity) mContext).viewRouterConfiguration.setAnimation(slide_up7);
                ((QuestionActivity) mContext).viewMainQuestion.setVisibility(View.GONE);
                ((QuestionActivity) mContext).command = "\n" + SelectedQuestionUtility.getInstance().getFirstAnswer() + "\n";
                ((QuestionActivity) mContext).openUsbDevice();

                break;
        }
    }


    @Override
    public int getItemCount() {
        return questionDataList.size();
    }
}
