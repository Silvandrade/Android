package com.silvandrade.instagramremakejava.register.presentation;

import android.content.Context;

import com.silvandrade.instagramremakejava.common.view.View;

public interface RegisterView {

    void showNextView(RegisterSteps step);

    void onUserCreated();

    interface EmailView {
        Context getContext();
        void onFailureForm(String emailError);
    }

    interface NamePasswordView extends View {
        Context getContext();
        void onFailureForm(String nameError, String passwordError);
        void onFailureCreateUser(String message);
    }

    interface WelcomeView {
    }

    interface PhotoView {

    }
}
