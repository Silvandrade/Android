package com.silvandrade.instagramremakejava.register.presentation;

import com.silvandrade.instagramremakejava.R;
import com.silvandrade.instagramremakejava.common.model.UserAuth;
import com.silvandrade.instagramremakejava.common.presenter.Presenter;
import com.silvandrade.instagramremakejava.common.util.Strings;
import com.silvandrade.instagramremakejava.register.datasource.RegisterDataSource;

public class RegisterPresenter implements Presenter<UserAuth> {

    private final RegisterDataSource dataSource;
    private RegisterView registerView;
    private RegisterView.EmailView emailView;
    private RegisterView.NamePasswordView namePasswordView;

    private String email, name, password;

    public RegisterPresenter(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    public void setEmailView(RegisterView.EmailView emailView) {
        this.emailView = emailView;
    }

    public void setNamePasswordView(RegisterView.NamePasswordView namePasswordView) {
        this.namePasswordView = namePasswordView;
    }

    public void setEmail(String email) {
        if(!Strings.emailIsValid(email)) {
            emailView.onFailureForm(emailView.getContext().getString(R.string.invalid_Email));
            return;
        }

        this.email = email;
        registerView.showNextView(RegisterSteps.NAME_PASSWORD);
    }

    public void setNameAndPassword(String name, String password, String confirmPassword) {
        if(!password.equals(confirmPassword)) {
            namePasswordView.onFailureForm(null, namePasswordView.getContext().getString(R.string.password_not_equal));
            return;
        }

        this.name = name;
        this.password = password;

        namePasswordView.showProgressBar();
        dataSource.createUser(this.name, this.email, this.password, this);
    }

    public void showPhotoView() {
        registerView.showNextView(RegisterSteps.PHOTO);
    }

    public void jumpRegistration() {
        registerView.onUserCreated();
    }

    public String getName() {
        return name;
    }

    @Override
    public void onSuccess(UserAuth response) {
        registerView.showNextView(RegisterSteps.WELCOME);
    }

    @Override
    public void onError(String message) {
        namePasswordView.onFailureCreateUser(message);
    }

    @Override
    public void onComplete() {
        namePasswordView.hideProgressBar();
    }
}
