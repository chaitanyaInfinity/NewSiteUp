package com.example.infinitylabs.dynamictrackerapp.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class CustomJsonArraytRequest extends JsonRequest<JSONArray> {

//    private static final String LOG_TAG = CustomJsonArraytRequest.class.getSimpleName();

    private final OnNetworkTimeListener onNetworkTimeListener;

    public interface OnNetworkTimeListener {
        void onNetworkTime(long networkTimeMilli);
    }


    public CustomJsonArraytRequest(String url, JSONObject jsonRequest
            , Response.Listener<JSONArray> listener, Response.ErrorListener errorListener
            , OnNetworkTimeListener onNetworkTimeListener) {
        super(Method.POST, url, jsonRequest.toString(), listener, errorListener);

        this.onNetworkTimeListener = onNetworkTimeListener;
    }


    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}


