package com.example.tris.Ecommerce.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tris.Ecommerce.net.RetrofitClient;
import com.example.tris.Ecommerce.utils.RequestCallback;
import com.example.tris.Ecommerce.model.Favorite;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFavoriteRepository
{
    private static final String TAG = AddFavoriteRepository.class.getSimpleName();
    private Application application;

    public AddFavoriteRepository(Application application)
    {
        this.application = application;
    }

    public LiveData<ResponseBody> addFavorite(Favorite favorite, RequestCallback callback)
    {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().addFavorite(favorite).enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                Log.d("onResponse", "" + response.code());

                ResponseBody responseBody = response.body();

                if(response.code() == 200)
                {
                    callback.onCallBack();
                }

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