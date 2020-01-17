package com.prozy.recycletrash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prozy.recycletrash.view.MenuMainActivity;

public class AkunActivity extends AppCompatActivity {

    private ImageView back_btn, photouser;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private TextView namauser, emailuser, nomortelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        back_btn = findViewById(R.id.back);
        namauser = findViewById(R.id.nama_user_akun);
        emailuser = findViewById(R.id.email_user_akun);
        nomortelp = findViewById(R.id.no_telp_akun);
        photouser = findViewById(R.id.profile_image_akun);

        loadUser();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBack = new Intent(AkunActivity.this, MenuMainActivity.class);
                intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentBack);
            }
        });
    }


    private void loadUser() {

        if (firebaseUser != null) {

            if (firebaseUser.getPhotoUrl() != null) {
                Glide.with(this).load(firebaseUser.getPhotoUrl()).into(photouser);

            } else {
                photouser.setImageResource(R.drawable.ic_person);
            }

            if (firebaseUser.getDisplayName() != null) {
                namauser.setText(firebaseUser.getDisplayName());
            } else {
                namauser.setText("Username");
            }

            if (firebaseUser.getEmail() != null) {
                emailuser.setText(firebaseUser.getEmail());
            } else {
                emailuser.setText("xyz@gmail.com");
            }

            if (firebaseUser.getPhoneNumber().equals("")) {

                nomortelp.setText("+62 xxx xxxx xxxx");
            }else {
                nomortelp.setText(firebaseUser.getPhoneNumber());
            }
        }


    }


    }

