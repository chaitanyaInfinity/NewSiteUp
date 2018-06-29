package com.example.infinitylabs.dynamictrackerapp.utilities;

import com.example.infinitylabs.dynamictrackerapp.request_response.SectionData;

/**
 * Created by infinitylabs on 5/7/17.
 */

public class SelectedSectionUtility {
    public static final String LOG_TAG = "SelectedSectionUtility";
    private static SelectedSectionUtility instance;


    SectionData selectedSectionData;


    private SelectedSectionUtility() {
        selectedSectionData = new SectionData();
    }

    public static SelectedSectionUtility getInstance() {
        if (instance == null) {
            instance = new SelectedSectionUtility();
        }
        return instance;
    }

    public void setSelectedSectionData(SectionData selectedSectionData) {
        this.selectedSectionData = selectedSectionData;
    }

    public String getSectionID() {
        return selectedSectionData.getId();
    }

    public String getSectionName() {
        return selectedSectionData.getSectionName();
    }

    public String getSectionStatus(){
        return selectedSectionData.getSectionStatus();
    }
}



