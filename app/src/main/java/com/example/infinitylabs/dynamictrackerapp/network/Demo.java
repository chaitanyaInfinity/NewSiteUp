package com.example.infinitylabs.dynamictrackerapp.network;

/**
 * Created by infinity on 8/4/17.
 */

public class Demo {
//    LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("sysadmin");
//        loginRequest.setPassword("sysadmin@123");
//    APIClientUtils apiClientUtils = new APIClientUtils(context);
//
//        try {
//        apiClientUtils.getServiceResponseByPost(UrlConstant.getLoginUrl(),
//                new APIClientCallback<MainResponse>() {
//
//                    @Override
//                    public void onSuccess(MainResponse loginServiceResponse) {
//
//
////                            if (loginServiceResponse.getQuestionStatus().equalsIgnoreCase(ApiStatus.SUCCESS.toString())) {
////                                decideFlowBasedOnInstitutionId(loginServiceResponse);
////                            } else {
////                                view.onLoginFailed(loginServiceResponse.getError().get(0).getDESCRIPTION());
////                            }
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
////                            view.hideLoadingView();
////                            view.onLoginFailed(e.toString());
//                    }
//
//                    @NonNull
//                    @Override
//                    public Class<MainResponse> getClassOfType() {
//                        return MainResponse.class;
//                    }
//
//                }, new JSONObject(apiClientUtils.getObjectMapper().writeValueAsString(loginRequest)));
//    } catch (JSONException e) {
//        e.printStackTrace();
//    } catch (JsonProcessingException e) {
//        e.printStackTrace();
//    }
//
//
//
//
//
//    String baseURL = "https://45.117.212.150:8443/site-progress-tracker/web/index.php/api/tracksites/get-engg-status";
//
//    Uri.Builder builder = new Uri.Builder();
//        builder.appendQueryParameter("access-token", "yapu15wcJAB9f0yfck6HysvrVmXf9a22")
//                .appendQueryParameter("user_id", "sysadmin");
//    String parameters = builder.build().toString();
//    String requestUrl=baseURL+parameters;
//
//        Log.e(LOG_TAG,"URL "+requestUrl);
//
//        try {
//        apiClientUtils.getServiceResponseByGet(requestUrl,
//                new APIClientCallback<MainResponse>() {
//
//                    @Override
//                    public void onSuccess(MainResponse loginServiceResponse) {
//
//
////                            if (loginServiceResponse.getQuestionStatus().equalsIgnoreCase(ApiStatus.SUCCESS.toString())) {
////                                decideFlowBasedOnInstitutionId(loginServiceResponse);
////                            } else {
////                                view.onLoginFailed(loginServiceResponse.getError().get(0).getDESCRIPTION());
////                            }
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
////                            view.hideLoadingView();
////                            view.onLoginFailed(e.toString());
//                    }
//
//                    @NonNull
//                    @Override
//                    public Class<MainResponse> getClassOfType() {
//                        return MainResponse.class;
//                    }
//
//                });
//    } catch (Exception e) {
//        Log.e(LOG_TAG, "Exception   " + e.toString());
//    }
}
