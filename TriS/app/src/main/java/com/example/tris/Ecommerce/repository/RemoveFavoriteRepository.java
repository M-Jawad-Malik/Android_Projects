package com.example.tris.Ecommerce.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tris.Ecommerce.net.RetrofitClient;
import com.example.tris.Ecommerce.utils.RequestCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveFavoriteRepository
{
    private static final String TAG = RemoveFavoriteRepository.class.getSimpleName();
    private Application application;

    public RemoveFavoriteRepository(Application application)
    {
        this.application = application;
    }

    public LiveData<ResponseBody> removeFavorite(int userId, int productId, RequestCallback callback)
    {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().removeFavorite(userId,productId).enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                Log.d("onResponse", "" + response.code());

                if(response.code() == 200)
                {
                    callback.onCallBack();
                }

                ResponseBody responseBody = response.body();

                if (response.body() != null)
                {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                Log.d("onFailure", "" + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}