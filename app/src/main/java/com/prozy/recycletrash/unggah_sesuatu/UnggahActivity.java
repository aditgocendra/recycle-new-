package com.prozy.recycletrash.unggah_sesuatu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prozy.recycletrash.R;
import com.prozy.recycletrash.model.Unggah_model;
import com.prozy.recycletrash.view.MenuMainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnggahActivity extends AppCompatActivity {

    private ImageView back_btn;
    private TextView deskripsi_edt,url_edt;
    private RadioButton radio_premi, radio_non, radio_choice;
    private RadioGroup radioGroup;
    private Button kirim;
    private DatabaseReference refrence;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unggah);

        back_btn =  findViewById(R.id.back);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        clickRadio();
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBack = new Intent(UnggahActivity.this, MenuMainActivity.class);
                startActivity(intentBack);
            }
        });
    }

    private void clickRadio(){
        kirim = findViewById(R.id.kirim_btn);
        radioGroup = findViewById(R.id.radioGroupPN);
        radio_premi = findViewById(R.id.radioPremi);
        radio_non = findViewById(R.id.radioNonPremi);

        url_edt = findViewById(R.id.salin_url);
        deskripsi_edt = findViewById(R.id.deskripsi_text);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int seletected = radioGroup.getCheckedRadioButtonId();
                radio_choice = findViewById(seletected);

                validasiForm();
            }
        });
    }

    private void saveUnggah(){

        refrence = FirebaseDatabase.getInstance().getReference("Unggah");

        String urlUtube = getYouTubeId(url_edt.getText().toString());

        Unggah_model unggah_model = new Unggah_model(
                urlUtube,
                radio_choice.getText().toString(),
                deskripsi_edt.getText().toString(),
                mUser.getPhotoUrl().toString());

        refrence.push()
                .setValue(unggah_model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UnggahActivity.this, "Sukses", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void validasiForm(){
        String url = url_edt.getText().toString();
        String desk = deskripsi_edt.getText().toString();
        if (url.isEmpty()){
            url_edt.setError("Url Masih Kosong");
        }else if (desk.isEmpty()){
            deskripsi_edt.setError("Deskripsi Masih Kosong");
        }else {
            saveUnggah();
        }

    }



    private String getYouTubeId(String youTubeUrl) {
        String pattern = "https?://(?:[0-9A-Z-]+\\.)?(?:youtu\\.be/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|</a>))[?=&+%\\w]*";

        Pattern compiledPattern = Pattern.compile(pattern,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }


}
