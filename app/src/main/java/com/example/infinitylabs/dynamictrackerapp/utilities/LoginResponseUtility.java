package com.example.infinitylabs.dynamictrackerapp.utilities;

import com.example.infinitylabs.dynamictrackerapp.request_response.LoginData;

/**
 * Created by infinitylabs on 10/7/17.
 */


public class LoginResponseUtility {
    public static final String LOG_TAG = "LoginResponseUtility";
    private static LoginResponseUtility instance;


    LoginData loginData;


    private LoginResponseUtility() {
        loginData = new LoginData();
    }

    public static LoginResponseUtility getInstance() {
        if (instance == null) {
            instance = new LoginResponseUtility();
        }
        return instance;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    public String getMobileNumber() {
        return loginData.getMobile();
    }

    public String getAccessToken(){
        return loginData.getAccessToken();
    }

    public String getUserId(){
        return loginData.getUserName();
    }

    public String getUserName(){
        return loginData.getUserName();
    }
}
