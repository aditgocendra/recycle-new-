package com.prozy.recycletrash.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prozy.recycletrash.R;
import com.prozy.recycletrash.adapter.YoutubeAdapter;
import com.prozy.recycletrash.model.Unggah_model;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends YouTubeBaseActivity {


    RecyclerView recyclerView_;
    YoutubeAdapter adapter;
    LinearLayoutManager manager;
    List<Unggah_model> unggahModelList;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

         reference = FirebaseDatabase.getInstance().getReference("Unggah");

        recyclerView_ = findViewById(R.id.recycle_vid);

        manager = new LinearLayoutManager(TutorialActivity.this);
        recyclerView_.setLayoutManager(manager);
//
//        adapter = new YoutubeAdapter(this, unggahModelList);
//        recyclerView_.setAdapter(adapter);

        unggahModelList = new ArrayList<>();

        adapter = new YoutubeAdapter(TutorialActivity.this, unggahModelList);
        recyclerView_.setAdapter(adapter);

        getData();



    }


    private void getData(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){

                        Unggah_model unggah_model = ds.getValue(Unggah_model.class);

                     //   Toast.makeText(TutorialActivity.this, ""+unggah_model.getUrl(), Toast.LENGTH_SHORT).show();

                        unggahModelList.add(unggah_model);
                    }
                    adapter = new YoutubeAdapter(TutorialActivity.this, unggahModelList);
                    recyclerView_.setAdapter(adapter);

                }else {
                    Toast.makeText(TutorialActivity.this, "Data Masih Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TutorialActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

}
