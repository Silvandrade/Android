package com.silvandrade.instagramremakejava.register.datasource;

import com.silvandrade.instagramremakejava.common.model.Database;
import com.silvandrade.instagramremakejava.common.model.UserAuth;
import com.silvandrade.instagramremakejava.common.presenter.Presenter;

public class RegisterLocalDataSource implements RegisterDataSource {

    @Override
    public void createUser(String name, String email, String password, Presenter presenter) {
        Database.getInstance()
                .addOnSuccessListener((Database.OnSuccessListener<UserAuth>) userAuth  -> {
                    presenter.onSuccess(userAuth);
                })

                .addOnFailureListener(e -> {
                    presenter.onError(e.getMessage());
                })

                .addOnCompleteListener(() -> {
                    presenter.onComplete();
                })

                .createUser(name, email, password);
    }
}
