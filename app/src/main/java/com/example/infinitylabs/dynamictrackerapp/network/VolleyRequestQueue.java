package com.example.infinitylabs.dynamictrackerapp.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



public class VolleyRequestQueue {
    private static RequestQueue ourInstance;

    public static RequestQueue getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = Volley.newRequestQueue(context);
        }
        return ourInstance;
    }

    private VolleyRequestQueue() {

    }
}
