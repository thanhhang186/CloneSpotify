package com.khtn.clonespotify.sign.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.LaunchFragment;
import com.khtn.clonespotify.MainActivity;
import com.khtn.clonespotify.R;
import com.khtn.clonespotify.home.view.HomeActivity;
import com.khtn.clonespotify.sign.presenter.SignInPresenter;
import com.khtn.clonespotify.sign.presenter.SignInPresenterImpl;
import com.khtn.clonespotify.sign.presenter.SignUpPresenter;
import com.khtn.clonespotify.sign.presenter.SignUpPresenterImpl;
import com.khtn.clonespotify.utils.PrefUtils;
import com.khtn.clonespotify.utils.Utils;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements SignInView {

    @BindView(R.id.sign_in_back)
    ImageView btnBack;
    @BindView(R.id.sign_in_frag)
    TextView btnToSignUpFrag;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.edt_login_email)
    EditText edtEmail;
    @BindView(R.id.edt_login_pass)
    EditText edtPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private SignInPresenter signInPresenter;
    private FirebaseAuth auth;
    public SignInFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this,view);
        auth = FirebaseAuth.getInstance();
        signInPresenter = new SignInPresenterImpl(getActivity(), auth);
        signInPresenter.attachView(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        btnBack.setOnClickListener(v->{
            loadLaunchFragment();
        });
        btnToSignUpFrag.setOnClickListener(v->{
            loadSignUpFragment();
        });
        btnSignIn.setOnClickListener(v->{
            signInPresenter.signIn(edtEmail.getText().toString(), edtPassword.getText().toString());
        });

    }

    private void loadSignUpFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SignUpFragment(), MainActivity.SIGN_UP_FRAGMENT)
                .commit();
    }

    private void loadLaunchFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LaunchFragment(), MainActivity.LAUNCH_FRAGMENT)
                .commit();
    }

    @Override
    public void showVadidationError() {
            Utils.showMessage(getActivity(), "Please enter a valid email address and password !");
        setProgressVisibility(false);
    }

    @Override
    public void loginSuccess() {
        Utils.showMessage(getActivity(), "Login Success !");
        setProgressVisibility(false);
    }

    @Override
    public void loginError() {
        Utils.showMessage(getActivity(), "Login Error !");
        setProgressVisibility(false);
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if(visibility){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void isLogin(boolean isLogin) {

    }

    @Override
    public void loadHomeAct() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }
}
