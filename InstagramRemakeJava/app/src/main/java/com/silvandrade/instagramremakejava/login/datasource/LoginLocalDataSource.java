package com.silvandrade.instagramremakejava.login.datasource;

import com.silvandrade.instagramremakejava.common.model.Database;
import com.silvandrade.instagramremakejava.common.model.UserAuth;
import com.silvandrade.instagramremakejava.common.presenter.Presenter;

public class LoginLocalDataSource implements LoginDataSource {

    public LoginLocalDataSource(){};

    @Override
    public void login(String email, String password, Presenter presenter) {
        Database.getInstance().addOnSuccessListener(new Database.OnSuccessListener<UserAuth>() {
                    @Override
                    public void onSuccess(UserAuth response) {
                        presenter.onSuccess(response);
                    }
                })
                .addOnFailureListener(new Database.OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        presenter.onError(e.getMessage());
                    }
                })
                .addOnCompleteListener(new Database.OnCompleteListener() {
                    @Override
                    public void onComplete() {
                        presenter.onComplete();
                    }
                })
                .login(email, password);
    }
}
