package com.westerosatwar.got.Network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.westerosatwar.got.Model.Battle;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Arjun.
 */
public class NetworkClient {

    Context context;

    public NetworkClient(Context context) {
        this.context = context;
    }

    public void getData(final ResponseCallback callback) {
        String url = "http://starlord.hackerearth.com/gotjson";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Type collectionType = new TypeToken<List<Battle>>(){}.getType();
                List<Battle> battles = new Gson().fromJson(
                        response.body().charStream(), collectionType);

                if (callback != null) {
                    callback.onResponse(battles);
                }
            }
        });
    }

}
