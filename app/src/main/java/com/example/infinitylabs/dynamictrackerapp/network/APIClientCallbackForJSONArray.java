package com.example.infinitylabs.dynamictrackerapp.network;

import org.json.JSONArray;


public interface APIClientCallbackForJSONArray {

    void onSuccess(JSONArray success);

    void onFailure(Exception e);

    String getAPINameForGA();

    String getEventLabelForGA();

    String getScreenNameForGA();

}
