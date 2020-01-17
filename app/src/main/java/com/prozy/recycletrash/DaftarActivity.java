package com.prozy.recycletrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class DaftarActivity extends AppCompatActivity {

    EditText email, kataSandi, konfirmasiSandi,notelp;
    ImageView back_btn;
    Button daftar_klik,daftar7;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        mAuth = FirebaseAuth.getInstance();


      //  progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.INVISIBLE);
        //field declare
        email =  findViewById(R.id.email_txt);
        kataSandi =  findViewById(R.id.kataSandi_txt);
        konfirmasiSandi =  findViewById(R.id.konfirmasiKataSandi_txt);
        notelp = findViewById(R.id.no_telp);
        //----------------------------------------------------------------------------------------

        //Button declare
        daftar_klik =  findViewById(R.id.daftar);
        back_btn =  findViewById(R.id.back);
        //----------------------------------------------------------------------------------------
        daftar7 = findViewById(R.id.btn_daftar6);
        daftar7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String $email = email.getText().toString().trim();
                String $kataSandi = kataSandi.getText().toString().trim();
                String $konfirmasi = konfirmasiSandi.getText().toString().trim();

                validasiForm($email, $kataSandi, $konfirmasi);

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBack = new Intent(DaftarActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

    }

    public static boolean ValidasiEmail(String email){
        boolean validasi;
        String emailPatern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPatern) || email.matches(emailPattern2) && email.length() > 0)
        {
            validasi = true;
        }else{
            validasi = false;
        }

        return validasi;
    }

    private void validasiForm(String $email, String $kataSandi, String $konfirmasi)
    {

        if($email.isEmpty())
        {
            email.setError("Email Harus Diisi");
            email.requestFocus();

        }else if (!ValidasiEmail($email))
        {
            email.setError("Format Email Salah");
            email.requestFocus();

        }else if($kataSandi.isEmpty())
        {
            kataSandi.setError("Kata sandi Harus Diisi");
            kataSandi.requestFocus();

        }else if($konfirmasi.isEmpty())
        {
            konfirmasiSandi.setError("Silahkan Masukkan Konfrimasi Sandi");
            konfirmasiSandi.requestFocus();

        }else if($kataSandi.length() < 8) {
            kataSandi.setError("Password Minimal 8 karakater");
            kataSandi.requestFocus();

        }else if(!$kataSandi.matches($konfirmasi))
        {
            Toast.makeText(DaftarActivity.this,
                    "Kata Sandi Tidak Sama", Toast.LENGTH_SHORT).show();

            konfirmasiSandi.setText("");
            konfirmasiSandi.requestFocus();
        }else {
        //    progressBar.setVisibility(View.VISIBLE);
            DaftarUserBaru($email, $kataSandi);

        }
    }

    public void refresh()
    {
        email.setText("");
        kataSandi.setText("");
        konfirmasiSandi.setText("");
        notelp.setText("");
    }


    public void DaftarUserBaru( String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(DaftarActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
          //              progressBar.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful())
                        {
                            mAuth.getCurrentUser().sendEmailVerification().
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                Toast.makeText(DaftarActivity.this,
                                                        "Daftar berhasil, Silahkan Lakukan Verifikasi",
                                                        Toast.LENGTH_SHORT).show();
                                                refresh();
                                                startActivity(new Intent(DaftarActivity.this, MainActivity.class));

                                            }else {
                                                Toast.makeText(DaftarActivity.this,
                                                        task.getException().getMessage(),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{

                            Toast.makeText(DaftarActivity.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }




//
//    public void Masuk(View view) {
//
//        Intent intentMasuk = new Intent(DaftarActivity.this, MasukActivity.class);
//        startActivity(intentMasuk);
//    }
}

