package com.example.infinitylabs.dynamictrackerapp.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class CustomJsonObjectRequestForGet extends JsonObjectRequest {

    private static final String LOG_TAG = CustomJsonObjectRequestForGet.class.getSimpleName();


    public long getNetworkTimeMs() {
        return networkTimeMs;
    }

    public long networkTimeMs;

    public interface OnNetworkTimeListener {
        void onNetworkTime(long networkTimeMilli);
    }

    public CustomJsonObjectRequestForGet(int method,String url
            , Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method,url, listener, errorListener);

    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {



            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

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

