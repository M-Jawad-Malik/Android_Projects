package com.example.tris.Ecommerce.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tris.Ecommerce.model.Favorite;
import com.example.tris.Ecommerce.repository.AddFavoriteRepository;
import com.example.tris.Ecommerce.utils.RequestCallback;

import okhttp3.ResponseBody;

public class AddFavoriteViewModel extends AndroidViewModel
{
    private AddFavoriteRepository addFavoriteRepository;

    public AddFavoriteViewModel(@NonNull Application application)
    {
        super(application);
        addFavoriteRepository = new AddFavoriteRepository(application);
    }

    public LiveData<ResponseBody> addFavorite(Favorite favorite, RequestCallback callback)
    {
        return addFavoriteRepository.addFavorite(favorite,callback);
    }
}