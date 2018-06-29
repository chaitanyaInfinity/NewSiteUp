package com.example.infinitylabs.dynamictrackerapp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.infinitylabs.dynamictrackerapp.R;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;


public class APIClientUtils {
    private static final String LOG_TAG = "APIClientUtils ";
    public int retryCount = 3;
    public int timeout = 40000;// milli-second
    private Context context;

    private long networkExecutionTime;

    private int getRetryCount() {
        return retryCount;
    }

    private int getTimeout() {
        return timeout;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public APIClientUtils(Context context) {
        this.context = context;
    }

    public <T> void getServiceResponseByPost(final String url, final APIClientCallback<T> apiClientCallback,
                                       final JSONObject requestJSON) {

        trustEveryone();
        if (!isConnectedToInternet()) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.no_network_error_message)));
        } else {
            Logger.logError(LOG_TAG, "URL => " + url);
            Logger.logError(LOG_TAG, "Request => " + requestJSON.toString());

            CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(url, requestJSON,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            ObjectMapper mapper = getObjectMapper();

                            Logger.logError(LOG_TAG, "URL => " + url);
                            Logger.logError(LOG_TAG, "Response => " + response.toString());

                            try {
                                if (apiClientCallback.getClassOfType().equals(JSONObject.class)) {
                                    apiClientCallback.onSuccess((T) response);
                                } else {
                                    T parsedResponse = mapper.readValue(response.toString(),
                                            apiClientCallback.getClassOfType());
                                    apiClientCallback.onSuccess(parsedResponse);
                                }

                            } catch (Exception e) {
                                Logger.logError(LOG_TAG,"APIClientCallback exception "+e.toString());
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {

                        apiClientCallback.onFailure(error);
                    } else {
                        apiClientCallback.onFailure(new Exception(""));
                    }


                }


            }, new CustomJsonObjectRequest.OnNetworkTimeListener() {
                @Override
                public void onNetworkTime(long networkTimeMilli) {
                    networkExecutionTime = networkTimeMilli;
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
//                    headers.put("Cookie", LoginResponseUtility.getInstance().getSessionId());
                    return headers;
                }

//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
            };


            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    getTimeout(),
                    getRetryCount(),
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);

        }
    }

    public <T> void getServiceResponseByPostForTasks(final String url, final APIClientCallback<T> apiClientCallback,
                                             final JSONObject requestJSON) {

        trustEveryone();
        if (!isConnectedToInternet()) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.no_network_error_message)));
        } else {
            Logger.logError(LOG_TAG, "URL => " + url);
            Logger.logError(LOG_TAG, "Request => " + requestJSON.toString());

            CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(url, requestJSON,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            ObjectMapper mapper = getObjectMapper();

                            Logger.logError(LOG_TAG, "URL => " + url);
                            Logger.logError(LOG_TAG, "Response => " + response.toString());

                            try {
                                if (apiClientCallback.getClassOfType().equals(JSONObject.class)) {
                                    apiClientCallback.onSuccess((T) response);
                                } else {
                                    T parsedResponse = mapper.readValue(response.toString(),
                                            apiClientCallback.getClassOfType());
                                    apiClientCallback.onSuccess(parsedResponse);
                                }

                            } catch (Exception e) {
                                Logger.logError(LOG_TAG,"APIClientCallback exception "+e.toString());
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {

                        apiClientCallback.onFailure(error);
                    } else {
                        apiClientCallback.onFailure(new Exception(""));
                    }


                }


            }, new CustomJsonObjectRequest.OnNetworkTimeListener() {
                @Override
                public void onNetworkTime(long networkTimeMilli) {
                    networkExecutionTime = networkTimeMilli;
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "text/html; charset=UTF-8");
//                    headers.put("Cookie", LoginResponseUtility.getInstance().getSessionId());
                    return headers;
                }


//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
            };


            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    getTimeout(),
                    getRetryCount(),
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);

        }
    }



    public <T> void getServiceResponseByGet(final String url, final APIClientCallback<T> apiClientCallback) {
        trustEveryone();
        if (!isConnectedToInternet()) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.no_network_error_message)));
        } else {
            Logger.logError(LOG_TAG, "URL => " + url);

            CustomJsonObjectRequestForGet customJsonObjectRequestForGet = new CustomJsonObjectRequestForGet(Request.Method.GET,url, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            ObjectMapper mapper = getObjectMapper();

                            Logger.logError(LOG_TAG, "Response => " + response.toString());

                            try {
                                if (apiClientCallback.getClassOfType().equals(JSONObject.class)) {
                                    apiClientCallback.onSuccess((T) response);
                                } else {
                                    T parsedResponse = mapper.readValue(response.toString(),
                                            apiClientCallback.getClassOfType());
                                    apiClientCallback.onSuccess(parsedResponse);
                                }

                            } catch (Exception e) {

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {

                        apiClientCallback.onFailure(error);
                    } else {
                        apiClientCallback.onFailure(new Exception(""));
                    }


                }
            })
            {
//
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Cookie", LoginResponseUtility.getInstance().getSessionId());
                    return headers;
                }
//
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
            };

            customJsonObjectRequestForGet.setRetryPolicy(new DefaultRetryPolicy(
                    getTimeout(),
                    getRetryCount(),
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleyRequestQueue.getInstance(context).add(customJsonObjectRequestForGet);

        }
    }


    private boolean isConnectedToInternet() {
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    return networkInfo != null && networkInfo.isConnected();
                } else {
                    showMessageInternet();
                    return false;
                }
            }
            showMessageInternet();
            return false;
        } catch (Exception e) {
            showMessageInternet();
            return false;
        }
    }


    public <T> void getListResponse(final String url, final APIClientCallbackForList<T> apiClientCallback,
                                    final JSONObject requestJSON) {
        trustEveryone();
        if (!isConnectedToInternet()) {
            apiClientCallback.onFailure(new NetworkException(context.getString(R.string.no_network_error_message)));
        } else {
            Logger.logError(LOG_TAG, "URL => " + url);
            Logger.logError(LOG_TAG, "Request => " + requestJSON.toString());

            CustomJsonArraytRequest jsonObjectRequest = null;
            jsonObjectRequest = new CustomJsonArraytRequest(url, requestJSON,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            ObjectMapper mapper = getObjectMapper();
                            Logger.logError(LOG_TAG, "URL => " + url);
                            Logger.logError(LOG_TAG, "Response => " + response.toString());
                            try {

                                List<T> parsedResponse = mapper.readValue(response.toString(),
                                        apiClientCallback.getClassOfType());
                                apiClientCallback.onSuccess(parsedResponse);

                            } catch (IOException e) {
                                apiClientCallback.onFailure(e);
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        apiClientCallback.onFailure(error);
                    } else {
                        apiClientCallback.onFailure(new Exception(""));
                    }

                }
            }, new CustomJsonArraytRequest.OnNetworkTimeListener() {
                @Override
                public void onNetworkTime(long networkTimeMilli) {
                    networkExecutionTime = networkTimeMilli;

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();

                    headers.put("Content-Type", "application/json; charset=utf-8");
//                    headers.put("Cookie", LoginResponseUtility.getInstance().getSessionId());
                    return headers;
                }
            };


            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    getTimeout(),
                    getRetryCount(),
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            VolleyRequestQueue.getInstance(context).add(jsonObjectRequest);


        }
    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }



    private void showMessageInternet() {
//        ErrorActivity.startNewActivityForInternetIssue(context);
    }

    @NonNull
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
        return objectMapper;
    }


}

