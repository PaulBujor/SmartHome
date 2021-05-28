package com.example.myhomeapplication.View.auth;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myhomeapplication.Models.User;
import com.example.myhomeapplication.R;
import com.example.myhomeapplication.View.MainActivity;
import com.example.myhomeapplication.ViewModel.CO2ViewModel;
import com.example.myhomeapplication.ViewModel.auth.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {
    private View view;
    private LoginViewModel viewModel;

    private Button loginButton, registerButtonForward;
    private TextInputEditText emailInput, passwordInput;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = view.findViewById(R.id.loginButton);
        registerButtonForward = view.findViewById(R.id.registerButtonForward);
        emailInput = view.findViewById(R.id.loginEmailEditInput);
        passwordInput = view.findViewById(R.id.loginPasswordEditInput);

        loginButton.setOnClickListener(v -> login());
        registerButtonForward.setOnClickListener(v -> registerForward());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        //Validating input
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isFilledEmail = !emailInput.getText().toString().isEmpty();
                boolean isFilledPassword = !passwordInput.getText().toString().isEmpty();
                boolean setEnabled = isFilledEmail && isFilledPassword;
                loginButton.setEnabled(setEnabled);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //nothing
            }
        };

        emailInput.addTextChangedListener(textWatcher);
        passwordInput.addTextChangedListener(textWatcher);

        return view;
    }

    private void login() {
        /*Activity activity = ((MainActivity) getActivity());
        activity.finish();*/
        final NavController navController = Navigation.findNavController(view);
        //NavHostFragment.findNavController(this).navigate(R.id.openMainGraph);
        navController.navigate(R.id.openMainGraph);
        ((MainActivity) getActivity()).setupMain();
        User user = new User(emailInput.getText().toString(), passwordInput.getText().toString());
        viewModel.login(user);
        /*activity.startActivity(activity.getIntent());*/
    }

    private void registerForward() {
        NavHostFragment.findNavController(this).navigate(R.id.openRegisterFragmentAction);
    }
}