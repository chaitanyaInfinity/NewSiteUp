package com.example.infinitylabs.dynamictrackerapp.utilities;

import com.example.infinitylabs.dynamictrackerapp.request_response.Data;

/**
 * Created by infinitylabs on 5/7/17.
 */

public class SelectedTaskUtility {
    public static final String LOG_TAG = "SelectedTaskUtility";
    private static SelectedTaskUtility instance;


    private Data selectedTaskData;
    String nipToUpload;


    private SelectedTaskUtility() {
        selectedTaskData = new Data();
    }

    public static SelectedTaskUtility getInstance() {
        if (instance == null) {
            instance = new SelectedTaskUtility();
        }
        return instance;
    }

    public void setSelectedTaskData(Data selectedTaskData) {
        this.selectedTaskData = selectedTaskData;
    }

    public String getSiteID() {
        return selectedTaskData.getId();
    }

    public String getSiteName() {
        return selectedTaskData.getSiteName();
    }


    public String getEnggContactNumber() {
        return selectedTaskData.getSiteEngineerNumber();
    }

    public String getEnggName() {
        return selectedTaskData.getSiteEngineerName();
    }

    public String getLatitude() {
        return selectedTaskData.getSiteLatitude();
    }

    public String getLongitude() {
        return selectedTaskData.getSiteLongitude();
    }

    public String getSiteCompletionStatus(){
        if(selectedTaskData.getStatus()!=null) {
            return selectedTaskData.getStatus();
        }else {
            return "";
        }
    }

    public void setNipToUpload(String nipToUpload){
        this.nipToUpload = nipToUpload;
    }

    public String getNipToUpload() {
        return nipToUpload;
    }
}

