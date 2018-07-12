package com.khtn.clonespotify.sign.presenter;

import com.khtn.clonespotify.model.User;
import com.khtn.clonespotify.sign.view.SignUpView;

public interface SignUpPresenter {
    void signUp(String id, String email, String password);
    void attachView(SignUpView signUpView);
    void detachView();
}
