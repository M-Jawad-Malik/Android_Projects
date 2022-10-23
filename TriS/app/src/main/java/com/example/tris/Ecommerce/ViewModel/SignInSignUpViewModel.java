package com.example.tris.Ecommerce.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tris.Ecommerce.repository.SignInSignUpRepository;


public class SignInSignUpViewModel extends AndroidViewModel
{
    private SignInSignUpRepository signInSignUpRepository;

    public SignInSignUpViewModel(@NonNull Application application)
    {
        super(application);
        signInSignUpRepository = new SignInSignUpRepository(application);
    }

   /* public LiveData<CartApiResponse> getProductsInCart(int userId)
    {
        return signInSignUpRepository.getProductsInCart(userId);
    }*/
}
