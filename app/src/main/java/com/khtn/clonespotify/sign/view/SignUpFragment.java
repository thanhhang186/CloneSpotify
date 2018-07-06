package com.khtn.clonespotify.sign.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.khtn.clonespotify.LaunchFragment;
import com.khtn.clonespotify.MainActivity;
import com.khtn.clonespotify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    @BindView(R.id.sign_up_back)
    ImageView btnBack;
    public SignUpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
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
    }

    private void loadLaunchFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new LaunchFragment(), MainActivity.LAUNCH_FRAGMENT)
                .commit();
    }
}
