package com.example.infinitylabs.dynamictrackerapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.infinitylabs.dynamictrackerapp.network.APIClientCallback;
import com.example.infinitylabs.dynamictrackerapp.network.APIClientUtils;
import com.example.infinitylabs.dynamictrackerapp.network.UrlConstant;
import com.example.infinitylabs.dynamictrackerapp.request_response.IssueRequest;
import com.example.infinitylabs.dynamictrackerapp.request_response.QuestionListResponse;
import com.example.infinitylabs.dynamictrackerapp.utilities.MainUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTaskUtility;
import com.example.infinitylabs.dynamictrackerapp.utilities.SelectedTrackingDataUtility;
import com.example.infinitylabs.dynamictrackerapp.view.section.SectionListActivity;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IssueActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1002;
    @BindView(R.id.tv_loading_message)
    TextView tvLoadingMessage;
    @BindView(R.id.ll_progress_main)
    LinearLayout llProgressMain;
    private String stringOfImage;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, IssueActivity.class));
    }

    @BindView(R.id.et_reason_report_issue)
    EditText etReasonReportIssue;
    @BindView(R.id.img_report_issue)
    ImageView imgReportIssue;
    @BindView(R.id.btn_submit_report_issue)
    AppCompatButton btnSubmitReportIssue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        ButterKnife.bind(this);
        setUpCamera();
    }

    private void setUpCamera() {
        imgReportIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

    private void captureImage() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Bitmap bitmapRouter1 = MainUtility.getBitmapWithTimeStamp(MainUtility.getScaledImage(imageBitmap));
            imgReportIssue.setImageBitmap(bitmapRouter1);
            stringOfImage = "data:image/png;base64," + MainUtility.getBase64Image(bitmapRouter1);
        }

    }


    public void callAPI(IssueRequest issueRequest, APIClientUtils apiClientUtils) {

        try {
            apiClientUtils.getServiceResponseByPost(UrlConstant.getSubmitIssue(),
                    new APIClientCallback<QuestionListResponse>() {

                        @Override
                        public void onSuccess(QuestionListResponse questionListResponse) {

                            hideLoadingView();
                            MainUtility.showMessage(btnSubmitReportIssue, "Issue reported Successfully");
                            SectionListActivity.launch(IssueActivity.this);

                        }

                        @Override
                        public void onFailure(Exception e) {
                            hideLoadingView();
                        }

                        @NonNull
                        @Override
                        public Class<QuestionListResponse> getClassOfType() {
                            return QuestionListResponse.class;
                        }

                    }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(issueRequest)));
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }


    }


    @OnClick(R.id.btn_submit_report_issue)
    public void onViewClicked() {

        showLoadingView(R.id.please_wait);

        IssueRequest issueRequest = new IssueRequest();
        issueRequest.setOrderId(SelectedTaskUtility.getInstance().getSiteID());
        issueRequest.setFormId(SelectedTrackingDataUtility.getInstance().getFormId());
        issueRequest.setIssue(etReasonReportIssue.getText().toString());
        issueRequest.setPhoto(stringOfImage);
        callAPI(issueRequest, new APIClientUtils(this));
    }

    public void showLoadingView(int message) {
        llProgressMain.setVisibility(View.VISIBLE);
        tvLoadingMessage.setText(MainUtility.getStringFromXml(this, message));
    }

    public void hideLoadingView() {
        llProgressMain.setVisibility(View.GONE);
    }
}
