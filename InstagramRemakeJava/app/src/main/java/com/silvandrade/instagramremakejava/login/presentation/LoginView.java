package com.silvandrade.instagramremakejava.login.presentation;

import com.silvandrade.instagramremakejava.common.view.View;

public interface LoginView extends View {
    void onFailureForm(String emailError, String passwordError);
    void onUserLogged();
}
