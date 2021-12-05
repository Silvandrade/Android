package com.silvandrade.instagramremakejava.login.presentation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.view.CustomButton;

public class LoginActivity extends AppCompatActivity {

    CustomButton buttonEnter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));


        EditText editTextEmail = findViewById(R.id.login_edit_text_email);
        EditText editTextPassword = findViewById(R.id.login_edit_text_password);

        editTextEmail.addTextChangedListener(watcher);
        editTextPassword.addTextChangedListener(watcher);

        buttonEnter = findViewById(R.id.login_button_enter);

        buttonEnter.setOnClickListener(v -> {
            buttonEnter.showProgress(true);

            new Handler().postDelayed(() -> {
                buttonEnter.showProgress(false);
            }, 4000);

            TextInputLayout textInputLayoutEmail = findViewById(R.id.login_edit_text_input_email);
            textInputLayoutEmail.setError("E-mail não cadastrado.");

            editTextEmail.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_error));

            TextInputLayout textInputLayoutPassword = findViewById(R.id.login_edit_text_input_password);
            textInputLayoutPassword.setError("Senha incorreta.");

            editTextPassword.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_error));
        });
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!s.toString().isEmpty()) {
                buttonEnter.setEnabled(true);
            } else {
                buttonEnter.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}