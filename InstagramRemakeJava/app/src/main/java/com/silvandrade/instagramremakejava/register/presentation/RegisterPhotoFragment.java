package com.silvandrade.instagramremakejava.register.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.view.CustomDialog;

public class RegisterPhotoFragment extends Fragment {

    public RegisterPhotoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_photo, container, false);
        // TODO: Scroll gravity top.
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
