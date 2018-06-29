package com.example.infinitylabs.dynamictrackerapp.utilities;

import com.example.infinitylabs.dynamictrackerapp.request_response.TrackingData;

/**
 * Created by infinitylabs on 5/12/17.
 */


public class SelectedTrackingDataUtility {
    public static final String LOG_TAG = "SelectedTrackingDataUtility";
    private static SelectedTrackingDataUtility instance;


    TrackingData selectedTaskData;


    private SelectedTrackingDataUtility() {
        selectedTaskData = new TrackingData();
    }

    public static SelectedTrackingDataUtility getInstance() {
        if (instance == null) {
            instance = new SelectedTrackingDataUtility();
        }
        return instance;
    }

    public void setSelectedTrackingData(TrackingData selectedTaskData) {
        this.selectedTaskData = selectedTaskData;
    }

    public String getFormId() {
        return selectedTaskData.getFormId();
    }

    public Boolean isHardwareAT(){
        return selectedTaskData.getHardwareAT();
    }


}

