package com.example.myhomeapplication.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myhomeapplication.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = view.findViewById(R.id.loginButton);
        registerButtonForward = view.findViewById(R.id.registerButtonForward);
        emailInput = view.findViewById(R.id.loginEmailEditInput);
        passwordInput = view.findViewById(R.id.loginPasswordEditInput);

        loginButton.setOnClickListener(v -> login());
        registerButtonForward.setOnClickListener(v -> registerForward());


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

    }

    private void registerForward() {
        NavHostFragment.findNavController(this).navigate(R.id.openRegisterFragmentAction);
    }
}