package com.silvandrade.instagramremakejava.register.presentation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.view.AbstractFragment;
import com.silvandrade.instagramremakejava.common.view.CustomButton;
import com.silvandrade.instagramremakejava.databinding.FragmentRegisterNamePasswordBinding;

public class RegisterNamePasswordFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.NamePasswordView, TextWatcher {

    private FragmentRegisterNamePasswordBinding binding;

    private EditText textName;
    private EditText textPassword;
    private EditText textConfirmPassword;

    private TextInputLayout textLayoutName;
    private TextInputLayout textLayoutPassword;
    private TextInputLayout textLayoutConfirmPassword;

    private CustomButton buttonNext;
    private TextView textViewLogin;

    public static RegisterNamePasswordFragment newInstance(RegisterPresenter presenter) {
        RegisterNamePasswordFragment fragment = new RegisterNamePasswordFragment();

        presenter.setNamePasswordView(fragment);
        fragment.setPresenter(presenter);

        return fragment;
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_register_name_password;
    }

    @Override
    protected void bindLayoutComponents() {
        textName = binding.registerEditTextName;
        textPassword = binding.registerEditTextPassword;
        textConfirmPassword = binding.registerEditTextConfirmPassword;

        textLayoutName = binding.registerEditTextInputName;
        textLayoutPassword = binding.registerEditTextInputPassword;
        textLayoutConfirmPassword = binding.registerEditTextInputConfirmPassword;

        buttonNext = binding.registerNameButtonNext;
        textViewLogin = binding.registerTextViewNamePasswordLogin;
    }

    @Override
    protected void setListenerComponents() {
        buttonNext.setOnClickListener(v -> {
            presenter.setNameAndPassword(
                    textName.getText().toString(),
                    textPassword.getText().toString(),
                    textConfirmPassword.getText().toString()
            );
        });

        textViewLogin.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().finish();
            }
        });

        textName.addTextChangedListener(this);
        textPassword.addTextChangedListener(this);
        textConfirmPassword.addTextChangedListener(this);
    }

    @Override
    protected void configComponents(View view) {
        binding = FragmentRegisterNamePasswordBinding.bind(view);
        bindLayoutComponents();
        setListenerComponents();
    }

    @Override
    public void onFailureForm(String nameError, String passwordError) {
        if(nameError != null) {
            textName.setBackground(findDrawable(R.drawable.edit_text_error));
            textLayoutName.setError(nameError);
        } else if (passwordError != null) {
            textPassword.setBackground(findDrawable(R.drawable.edit_text_error));
            textLayoutPassword.setError(passwordError);
        }
    }

    @Override
    public void onFailureCreateUser(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        buttonNext.showProgress(true);
    }

    @Override
    public void hideProgressBar() {
        buttonNext.showProgress(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        buttonNext.setEnabled(
                !textName.getText().toString().isEmpty()
                && !textPassword.getText().toString().isEmpty()
                && !textConfirmPassword.getText().toString().isEmpty()
        );

        textName.setBackground(findDrawable(R.drawable.edit_text_background));
        textLayoutName.setError(null);

        textPassword.setBackground(findDrawable(R.drawable.edit_text_background));
        textLayoutPassword.setError(null);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
