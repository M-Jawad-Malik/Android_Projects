package com.example.tris.Ecommerce.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tris.Ecommerce.repository.RemoveFavoriteRepository;
import com.example.tris.Ecommerce.utils.RequestCallback;

import okhttp3.ResponseBody;

public class RemoveFavoriteViewModel extends AndroidViewModel
{
    private RemoveFavoriteRepository removeFavoriteRepository;

    public RemoveFavoriteViewModel(@NonNull Application application)
    {
        super(application);
        removeFavoriteRepository = new RemoveFavoriteRepository(application);
    }

    public LiveData<ResponseBody> removeFavorite(int userId, int productId, RequestCallback callback)
    {
        return removeFavoriteRepository.removeFavorite(userId, productId, callback);
    }
}