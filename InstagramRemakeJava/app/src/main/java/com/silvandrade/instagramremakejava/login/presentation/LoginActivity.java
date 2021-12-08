package com.silvandrade.instagramremakejava.login.presentation;

import androidx.annotation.RequiresApi;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.view.AbstractActivity;
import com.silvandrade.instagramremakejava.common.view.CustomButton;
import com.silvandrade.instagramremakejava.databinding.ActivityLoginBinding;
import com.silvandrade.instagramremakejava.login.datasource.LoginDataSource;
import com.silvandrade.instagramremakejava.login.datasource.LoginLocalDataSource;
import com.silvandrade.instagramremakejava.main.presentation.MainActivity;
import com.silvandrade.instagramremakejava.register.presentation.RegisterActivity;


public class LoginActivity extends AbstractActivity implements LoginView, TextWatcher {

    private ActivityLoginBinding binding;
    private CustomButton buttonEnter;
    private EditText textEmail;
    private EditText textPassword;
    private TextInputLayout textLayoutEmail;
    private TextInputLayout textLayoutPassword;
    private TextView textViewRegister;

    private LoginPresenter loginPresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBarDark();
        bindLayoutComponents();
        setOnClickListenerComponents();

        textEmail.addTextChangedListener(this);
        textPassword.addTextChangedListener(this);
    }

    private void bindLayoutComponents() {
        textEmail = binding.loginEditTextEmail;
        textPassword = binding.loginEditTextPassword;
        textLayoutEmail = binding.loginEditTextInputEmail;
        textLayoutPassword = binding.loginEditTextInputPassword;

        buttonEnter = binding.loginButtonEnter;
        textViewRegister = binding.loginTextViewRegister;
    }

    private void setOnClickListenerComponents() {
        buttonEnter.setOnClickListener(v -> {
            loginPresenter.login(textEmail.getText().toString(), textPassword.getText().toString());
        });

        textViewRegister.setOnClickListener(v -> {
            RegisterActivity.launch(this);
        });
    }

    @Override
    protected View getView() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    protected void onInject() {
        LoginDataSource dataSource = new LoginLocalDataSource();
        loginPresenter = new LoginPresenter(this, dataSource);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        buttonEnter.setEnabled(!editTextIsEmpty(textEmail, textPassword));

        if (s.hashCode() == textEmail.getText().hashCode()) {
            textLayoutEmail.setErrorEnabled(false);
            textEmail.setBackground(findDrawable(R.drawable.edit_text_background));
        } else if (s.hashCode() == textPassword.getText().hashCode()) {
            textLayoutPassword.setErrorEnabled(false);
            textPassword.setBackground(findDrawable(R.drawable.edit_text_background));
        }
    }

    public Boolean editTextIsEmpty(EditText... editTexts) {
        Boolean empty = false;

        for (EditText editText : editTexts) {
            empty = empty || editText.getText().toString().isEmpty();
        }

        return empty;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFailureForm(String emailError, String passwordError) {
        if (emailError != null) {
            textLayoutEmail.setError(emailError);
            textEmail.setBackground(findDrawable(R.drawable.edit_text_error));
        }

        if (passwordError != null) {
            textLayoutPassword.setError(passwordError);
            textPassword.setBackground(findDrawable(R.drawable.edit_text_error));
        }
    }

    @Override
    public void onUserLogged() {
        MainActivity.launch(this);
    }

    @Override
    public void showProgressBar() {
        buttonEnter.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        buttonEnter.showProgress(false);
    }
}