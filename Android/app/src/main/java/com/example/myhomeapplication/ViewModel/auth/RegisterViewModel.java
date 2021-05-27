package com.example.myhomeapplication.ViewModel.auth;

import androidx.lifecycle.ViewModel;

import com.example.myhomeapplication.Authorization.UserManager;
import com.example.myhomeapplication.Models.User;

public class RegisterViewModel extends ViewModel {
    private UserManager userManager;

    public RegisterViewModel() {
        userManager = UserManager.getInstance();
    }

    public void register(User user) {
        userManager.register(user);
    }
}
