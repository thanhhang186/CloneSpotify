package com.khtn.clonespotify.sign.view;


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

import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.LaunchFragment;
import com.khtn.clonespotify.MainActivity;
import com.khtn.clonespotify.R;
import com.khtn.clonespotify.sign.presenter.SignUpPresenter;
import com.khtn.clonespotify.sign.presenter.SignUpPresenterImpl;
import com.khtn.clonespotify.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements SignUpView {
    @BindView(R.id.edt_regist_email)
    EditText edtEmail;
    @BindView(R.id.edt_regist_pass)
    EditText edtPass;
    @BindView(R.id.sign_up_back)
    ImageView btnBack;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private SignUpPresenter signUpPresenter;
    private FirebaseAuth auth;
    public SignUpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        auth = FirebaseAuth.getInstance();
        signUpPresenter = new SignUpPresenterImpl(getActivity(), auth);
        signUpPresenter.attachView(this);
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
        btnSignUp.setOnClickListener(v->{
            int id = (int) (System.currentTimeMillis() / 1000);
            signUpPresenter.signUp(id+"" ,edtEmail.getText().toString(), edtPass.getText().toString());
        });
    }

    private void loadLaunchFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LaunchFragment(), MainActivity.LAUNCH_FRAGMENT)
                .commit();
    }

    @Override
    public void showValidationError() {
        Utils.showMessage(getActivity(),"Please enter a valid email address and password");
    }

    @Override
    public void signUpSuccess() {
        Utils.showMessage(getActivity(),"Sign up successfully");
    }

    @Override
    public void signUpError() {
        Utils.showMessage(getActivity(),"Sign up failed");
    }

    @Override
    public void setProgressVisibility(boolean visibility) {
        if(visibility){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
