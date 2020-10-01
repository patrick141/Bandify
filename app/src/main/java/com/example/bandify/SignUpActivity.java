package com.example.bandify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bandify.databinding.ActivitySignUpBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class SignUpActivity extends AppCompatActivity{
    private EditText etNewUsername;
    private EditText etNewPassword;
    private EditText etNewEmail;
    private EditText etNewPhone;
    private Button btnSignUp;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignUpBinding binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

}