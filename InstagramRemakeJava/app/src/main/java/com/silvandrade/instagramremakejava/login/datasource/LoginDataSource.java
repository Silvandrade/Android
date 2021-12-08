package com.silvandrade.instagramremakejava.login.datasource;

import com.silvandrade.instagramremakejava.common.presenter.Presenter;

public interface LoginDataSource {
    void login(String email, String password, Presenter presenter);
}
