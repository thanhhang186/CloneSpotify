package com.khtn.clonespotify.sign.view;

public interface SignUpView {
    void showValidationError();
    void signUpSuccess();
    void signUpError();
    void setProgressVisibility(boolean visibility);
}
