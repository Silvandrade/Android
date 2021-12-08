package com.silvandrade.instagramremakejava.register.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.view.AbstractFragment;
import com.silvandrade.instagramremakejava.common.view.CustomButton;
import com.silvandrade.instagramremakejava.databinding.FragmentRegisterEmailBinding;
import com.silvandrade.instagramremakejava.databinding.FragmentRegisterWelcomeBinding;

public class RegisterWelcomeFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.WelcomeView {

    private FragmentRegisterWelcomeBinding binding;
    private CustomButton buttonNext;
    private TextView textWelcome;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext.setEnabled(true);
        textWelcome.setText(getString(R.string.welcome_to_instagram, presenter.getName()));
    }

    public static RegisterWelcomeFragment newInstance(RegisterPresenter presenter) {
        RegisterWelcomeFragment fragment = new RegisterWelcomeFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_welcome;
    }

    @Override
    protected void bindLayoutComponents() {
        buttonNext = binding.registerButtonNextWelcome;
        textWelcome = binding.registerTextViewWelcome;
    }

    @Override
    protected void setListenerComponents() {
        buttonNext.setOnClickListener(v -> {
            presenter.showPhotoView();
        });
    }

    @Override
    protected void configComponents(View view) {
        binding = FragmentRegisterWelcomeBinding.bind(view);
        bindLayoutComponents();
        setListenerComponents();
    }
}
