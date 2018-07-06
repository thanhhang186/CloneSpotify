package com.khtn.clonespotify.sign.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.sign.view.SignInView;

public class SignInPresenterImpl implements SignInPresenter {
    private FirebaseAuth auth;
    private SignInView signInView;

    public SignInPresenterImpl(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Override
    public void signIn(String email, String password) {
        signInView.setProgressVisibility(true);
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            signInView.showVadidationError();
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    signInView.setProgressVisibility(false);
                    if(!task.isSuccessful()){
                        signInView.loginError();
                    } else {
                        signInView.loginSuccess();
                    }
                }
            });
        }

    }

    @Override
    public void checkSignIn() {

    }

    @Override
    public void attachView(SignInView view) {
        signInView = view;
    }

    @Override
    public void detachView() {
        signInView = null;
    }
}
