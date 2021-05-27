package com.example.myhomeapplication.View.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myhomeapplication.Models.User;
import com.example.myhomeapplication.R;
import com.example.myhomeapplication.ViewModel.CO2ViewModel;
import com.example.myhomeapplication.ViewModel.auth.RegisterViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterFragment extends Fragment {
    private RegisterViewModel viewModel;

    private TextInputEditText emailInput, passwordInput;
    private Button registerButton, backToLoginButton;

    public RegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        emailInput = view.findViewById(R.id.registerEmailEditInput);
        passwordInput = view.findViewById(R.id.registerPasswordEditInput);
        registerButton = view.findViewById(R.id.registerButton);
        backToLoginButton = view.findViewById(R.id.goBackToLoginButton);

        registerButton.setOnClickListener(v -> register());
        backToLoginButton.setOnClickListener(v -> backToLogin());

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

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
                registerButton.setEnabled(setEnabled);
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

    private void register() {
        User user = new User(emailInput.getText().toString(), passwordInput.getText().toString());
        viewModel.register(user);
    }

    private void backToLogin() {
        NavHostFragment.findNavController(this).popBackStack();
    }
}