package com.silvandrade.instagramremakejava.register.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ScrollingView;
import androidx.fragment.app.Fragment;

import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.view.AbstractFragment;
import com.silvandrade.instagramremakejava.common.view.CustomButton;
import com.silvandrade.instagramremakejava.common.view.CustomDialog;
import com.silvandrade.instagramremakejava.databinding.FragmentRegisterPhotoBinding;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

    private FragmentRegisterPhotoBinding binding;
    private CustomButton buttonNext;
    private AppCompatButton buttonJump;
    private ScrollingView scrollingView;

    public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }

    @Override
    protected void bindLayoutComponents() {
        buttonNext = binding.registerEmailButtonNext;
        buttonJump = binding.registerButtonJump;
        scrollingView = binding.getRoot().findViewById(R.id.register_scroll_view);
    }

    @Override
    protected void setListenerComponents() {
        buttonNext.setOnClickListener(v -> {

        });

        buttonJump.setOnClickListener(v -> {
            presenter.jumpRegistration();
        });
    }

    @Override
    protected void configComponents(View view) {
        binding = FragmentRegisterPhotoBinding.bind(view);
        bindLayoutComponents();
        setListenerComponents();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonNext.setEnabled(true);

        final CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton(v -> {
                    switch (v.getId()) {
                        case R.string.take_picture:
                            Log.i("CustomDialogClick", "Take Pic");
                            break;
                        case R.string.search_gallery:
                            Log.i("CustomDialogClick", "Search Gallery");
                            break;
                    }

                }, R.string.take_picture, R.string.search_gallery)
                .build();
        customDialog.show();
    }
}
