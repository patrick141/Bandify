package com.example.bandify.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.bandify.R;
import com.example.bandify.models.User;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";

    private ImageView ivProfile;
    private TextView tvScr;
    private TextView tvUse;
    private TextView tvDes;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivProfile = view.findViewById(R.id.iv_profile);
        tvScr = view.findViewById(R.id.tv_profile_name);
        tvUse = view.findViewById(R.id.tv_profile_un);
        tvDes = view.findViewById(R.id.tv_user_des);
        ParseUser user = ParseUser.getCurrentUser();
        try {
            ParseFile image = user.fetchIfNeeded().getParseFile(User.KEY_USER_IMAGE);
            String screenName = user.fetchIfNeeded().getString(User.KEY_USER_SCREEN);
            String description = user.fetchIfNeeded().getString(User.KEY_USER_DES);
            if(image != null){
                Glide.with(getContext()).load(image.getUrl()).transform(new CircleCrop()).into(ivProfile);
            }
            if(screenName != null){
                tvScr.setText(screenName);
            }
            if(description != null){
                tvDes.setText(description);
            }
            tvUse.setText("@" + user.getUsername());
            tvUse.setTextColor(getContext().getResources().getColor(android.R.color.darker_gray));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}