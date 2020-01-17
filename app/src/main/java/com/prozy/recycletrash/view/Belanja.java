package com.prozy.recycletrash.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prozy.recycletrash.R;
import com.prozy.recycletrash.model.FirebaseViewHolder;
import com.prozy.recycletrash.model.datasetfire;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Belanja extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<datasetfire> arrayList;
    private FirebaseRecyclerOptions<datasetfire> options;
    private FirebaseRecyclerAdapter<datasetfire, FirebaseViewHolder> adapter;
    private DatabaseReference databasereference,daa;


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belanja);
        recyclerView = findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<datasetfire>();
        databasereference = FirebaseDatabase.getInstance().getReference("produk");
        databasereference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<datasetfire>().setQuery(databasereference,datasetfire.class).build();

        adapter = new FirebaseRecyclerAdapter<datasetfire, FirebaseViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull final datasetfire model) {

                holder.namab.setText(model.getNamabarang());
                holder.dateb.setText(model.getDate());
                holder.catb.setText(model.getJenis());
                holder.name.setText(model.getNomorhp());
                Picasso.get().load(model.getImage()).into(holder.image);



            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup ViewGroup, int  i) {
                return new FirebaseViewHolder(LayoutInflater.from(Belanja.this).inflate(R.layout.row1,ViewGroup,false));
            }
        };


        recyclerView.setAdapter(adapter);


    }
}
