package com.example.gamelist.Utils;

import android.util.Log;

import com.example.gamelist.Constants;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttputils {
    public static String sendGetOkHttpRequest(String url) throws Exception {

        Log.w("tag", "url : " + url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        if (response.code() < 200 || response.code() >= 300) {
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        } else {
            return response.body().string();
        }

    }

    public static String sendPostOkHttpRequest(String url, String post) throws Exception {

        Log.w("tag", "url : " + url);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody requestBody = RequestBody.create(mediaType, post);
        Request request = new Request.Builder().url(url)
                .addHeader("user-key", Constants.API_KEY)
                .addHeader("Accept", "application/json")
                .post(requestBody).build();
        Response response = client.newCall(request).execute();

        if (response.code() < 200 || response.code() >= 300) {
            throw new Exception("Réponse du serveur incorrect : " + response.code() + "Reason : " + response.toString());
        } else {
            return response.body().string();
        }

    }
}
