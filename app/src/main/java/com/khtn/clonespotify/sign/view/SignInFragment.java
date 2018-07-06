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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.khtn.clonespotify.LaunchFragment;
import com.khtn.clonespotify.MainActivity;
import com.khtn.clonespotify.R;
import com.khtn.clonespotify.home.view.HomeActivity;
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
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    public SignInFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this,view);
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
            loadHomeAct();
        });

    }

    private void loadHomeAct() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
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

    }

    @Override
    public void loginSuccess() {
        Utils.showMessage(getActivity(), "Login Success !");
    }

    @Override
    public void loginError() {
        Utils.showMessage(getActivity(), "Login Error !");
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
}
