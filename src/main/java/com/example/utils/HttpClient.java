package com.example.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

public class HttpClient {

    public static String get(String url, Map<String, String> parameter) {
        StringBuilder fullUrlBuilder = null;

        if (parameter != null) {
            fullUrlBuilder = new StringBuilder(url).append("?");
            for (Map.Entry<String, String> entry : parameter.entrySet()) {
                fullUrlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            fullUrlBuilder.deleteCharAt(fullUrlBuilder.length() - 1);
        }

        if (fullUrlBuilder != null) {
            url = fullUrlBuilder.toString();
        }
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("User-Agent", "Apifox/1.0.0 (https://www.apifox.cn)")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                return null;
            } else {
                return new String(response.body().bytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String url) {
        return get(url, null);
    }
}
