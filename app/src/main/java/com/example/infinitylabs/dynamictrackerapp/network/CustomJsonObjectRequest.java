package com.example.infinitylabs.dynamictrackerapp.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.infinitylabs.dynamictrackerapp.utilities.Logger;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class CustomJsonObjectRequest extends JsonObjectRequest {

    private static final String LOG_TAG = CustomJsonObjectRequest.class.getSimpleName();

    private final OnNetworkTimeListener onNetworkTimeListener;

    public long getNetworkTimeMs() {
        return networkTimeMs;
    }

    public long networkTimeMs;

    public interface OnNetworkTimeListener {
        void onNetworkTime(long networkTimeMilli);
    }

    public CustomJsonObjectRequest(String url, JSONObject jsonRequest
            , Response.Listener<JSONObject> listener, Response.ErrorListener errorListener
            , OnNetworkTimeListener onNetworkTimeListener) {
        super(url, jsonRequest, listener, errorListener);

        this.onNetworkTimeListener = onNetworkTimeListener;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {

            this.networkTimeMs = response.networkTimeMs;
            onNetworkTimeListener.onNetworkTime(response.networkTimeMs);

            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

            Logger.logError(LOG_TAG,"Previous session Header "+response.headers.toString());

            String header_response = String.valueOf(response.headers.values());

            if(header_response.contains("PHPSESSID")) {
                int index1 = header_response.indexOf("PHPSESSID=");
                int index2 = header_response.indexOf("; path=/;");

                String session_id = header_response.substring(index1, index2);

//                LoginResponseUtility.getInstance().setSessionId(session_id);

                Logger.logError(LOG_TAG, "New session Header " + session_id);
            }

            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }


//    @Override
//    protected void deliverResponse(JSONObject response) {
//        Logger.logError(LOG_TAG, "deliverResponse: "+response.toString());
//        listener.onResponse(response);
//    }


}

