package com.example.infinitylabs.dynamictrackerapp.view.custom_view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infinitylabs.dynamictrackerapp.R;

/**
 * Created by infinitylabs on 7/11/17.
 */

public class StepView extends RelativeLayout {

    TextView stepName;
    TextView stepDetails;
    ImageView stepImage;
    View stepLine;
    LayoutInflater mInflater;

    public StepView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();

    }

    public StepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        View v = mInflater.inflate(R.layout.step_view, this, true);
        stepImage = (ImageView) v.findViewById(R.id.img_check_stop);
        stepName = (TextView) v.findViewById(R.id.tv_step_name);
        stepDetails = (TextView) v.findViewById(R.id.tv_step_details);
        stepLine = (View) v.findViewById(R.id.view_line);
    }

    public void hideStepLine() {
        stepLine.setVisibility(GONE);
    }

    public void setStepName(String stepTitle) {
        stepName.setText(stepTitle);
    }

    public void setStepDetails(String stepdetails) {
        stepDetails.setText(stepdetails);
    }

    public void setStepImage (Drawable stepLogo){
        stepImage.setImageDrawable(stepLogo);
    }

}
