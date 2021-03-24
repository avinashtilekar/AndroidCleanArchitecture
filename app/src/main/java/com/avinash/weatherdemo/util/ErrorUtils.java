package com.avinash.weatherdemo.util;

import android.util.Log;

import com.avinash.data.source.remote.ApiServiceGenerator;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    private final static String TAG = "ErrorUtils";


    private static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter = ApiServiceGenerator.getRetrofit()
                .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
            error.setStatusCode(response.code());
        } catch (IOException e) {
            return new APIError();
        }
        return error;
    }


    public static APIError parseThrowable(Throwable t) {
        APIError apiError = new APIError();
        Gson gson = new Gson();
        try {
            if (t instanceof HttpException) {
//                ResponseBody responseBody = ((HttpException) t).response().errorBody();
                apiError = parseError(((HttpException) t).response());
            } else if (t instanceof retrofit2.HttpException) {
                apiError = parseError(((retrofit2.HttpException) t).response());
            } else {
                new APIError();
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return apiError;
    }

}

