package com.silvandrade.instagramremakejava.login.presentation;

import android.os.Handler;

import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.model.UserAuth;
import com.silvandrade.instagramremakejava.common.presenter.Presenter;
import com.silvandrade.instagramremakejava.common.util.Strings;
import com.silvandrade.instagramremakejava.login.datasource.LoginDataSource;


public class LoginPresenter implements Presenter<UserAuth> {

    private final LoginView view;
    private final LoginDataSource dataSource;

    public LoginPresenter(LoginView view, LoginDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    public void login(String email, String password) {

        if (!Strings.emailIsValid(email)) {
            view.onFailureForm(view.getContext().getString(R.string.invalid_email), null);
            return;
        }

        view.showProgressBar();
        dataSource.login(email, password, this);

        new Handler().postDelayed(() -> {
            view.hideProgressBar();
        }, 2000);
    }

    @Override
    public void onSuccess(UserAuth userAuth) {
        view.onUserLogged();
    }

    @Override
    public void onError(String message) {
        view.onFailureForm(null, message);
    }

    @Override
    public void onComplete() {
        view.hideProgressBar();
    }
}
