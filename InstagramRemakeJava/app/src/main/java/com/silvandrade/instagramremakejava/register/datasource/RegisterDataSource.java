package com.silvandrade.instagramremakejava.register.datasource;

import com.silvandrade.instagramremakejava.common.presenter.Presenter;

public interface RegisterDataSource {
    void createUser(String name, String email, String password, Presenter presenter);
}
