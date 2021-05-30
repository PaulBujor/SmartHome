package com.example.myhomeapplication.ViewModel.auth;

import androidx.lifecycle.ViewModel;

import com.example.myhomeapplication.Authorization.UserManager;
import com.example.myhomeapplication.Models.User;

public class LoginViewModel extends ViewModel {
    private UserManager userManager;

    public LoginViewModel() {
        userManager = UserManager.getInstance();
    }

    public void login(User user) {
        userManager.logIn(user);
    }
}
