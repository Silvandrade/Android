package com.silvandrade.instagramremakejava.register.presentation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.view.AbstractFragment;
import com.silvandrade.instagramremakejava.common.view.CustomButton;
import com.silvandrade.instagramremakejava.databinding.FragmentRegisterEmailBinding;

public class RegisterEmailFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.EmailView, TextWatcher {

    private FragmentRegisterEmailBinding binding;
    private TextInputLayout textLayoutEmail;
    private EditText textEmail;
    private CustomButton buttonNext;
    private TextView textViewLogin;

    public static RegisterEmailFragment newInstance(RegisterPresenter presenter) {
        RegisterEmailFragment fragment = new RegisterEmailFragment();

        presenter.setEmailView(fragment);
        fragment.setPresenter(presenter);

        return fragment;
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
    public int getLayout() {
        return R.layout.fragment_register_email;
    }

    @Override
    public void onFailureForm(String emailError) {
        textLayoutEmail.setError(emailError);
        textEmail.setBackground(findDrawable(R.drawable.edit_text_error));
    }

    protected void bindLayoutComponents() {
        textLayoutEmail = binding.registerEditTextInputEmail;
        textEmail = binding.registerEditTextEmail;
        buttonNext = binding.registerEmailButtonNext;
        textViewLogin = binding.registerTextViewEmailLogin;
    }

    protected void setListenerComponents() {
        buttonNext.setOnClickListener(v -> {
            presenter.setEmail(textEmail.getText().toString());
        });

        textViewLogin.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().finish();
            }
        });

        textEmail.addTextChangedListener(this);
    }

    protected void configComponents(View view) {
        binding = FragmentRegisterEmailBinding.bind(view);
        bindLayoutComponents();
        setListenerComponents();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        buttonNext.setEnabled(!textEmail.getText().toString().isEmpty());
        textEmail.setBackground(findDrawable(R.drawable.edit_text_background));
        textLayoutEmail.setErrorEnabled(false);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
