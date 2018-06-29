package com.example.infinitylabs.dynamictrackerapp.network;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;


public interface APIClientCallbackForList<T> {

    void onSuccess(List<T> success);

    void onFailure(Exception e);

    @NonNull
    TypeReference<List<T>> getClassOfType();


}
