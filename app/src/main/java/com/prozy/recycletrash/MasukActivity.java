package com.prozy.recycletrash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prozy.recycletrash.view.MenuMainActivity;

public class MasukActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private CallbackManager callbackManager;
    //punya GOOGLE
    static final int GOOGLE_SIGN_IN = 123;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    TextView text;
    ImageView btn_signG;
    LoginButton btn_signF;
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    private ImageView back_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        mEmail = findViewById(R.id.email_masuk_txt);
        mPassword = findViewById(R.id.kataSandi_masuk_txt);
        mLoginBtn = findViewById(R.id.btn_masuk);
        fAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar_Masuk);
        progressBar.setVisibility(View.INVISIBLE);
        //MULAI DAFTAR MANUAL
        back_btn =  findViewById(R.id.back);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBack = new Intent(MasukActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;

                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }
                if(password.length() < 6 ){
                    mPassword.setError("Password harus lebih dari 6 charakter");
                    return;
                }

                //authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            if (fAuth.getCurrentUser().isEmailVerified())
                            {
                                FirebaseUser currentUser = mAuth.getCurrentUser();

                                Toast.makeText(MasukActivity.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MenuMainActivity.class));
                                finish();

                            }else {
                                Toast.makeText(MasukActivity.this, "Akun Belum Terverifikasi", Toast.LENGTH_SHORT).show();

                            }


                        }
                        else{
                            Toast.makeText(MasukActivity.this, "Email atau Kata sandi salah ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }); //SELESAI DAFTAR MANUAL


    } //penutup onCreate


    public void Daftar(View view) {
        startActivity(new Intent(getApplicationContext(),DaftarActivity.class));
    }

    public void bantuan_masuk(View view) {


    }
}
