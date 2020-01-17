package com.prozy.recycletrash.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.prozy.recycletrash.R;
import com.prozy.recycletrash.model.Unggah_model;
import com.prozy.recycletrash.view.DetailVIdeo;

import java.util.List;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.ViewHolder> {


    Context context;
    List<Unggah_model> models;

    public YoutubeAdapter(Context context, List<Unggah_model> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_post_vid, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Unggah_model unggah_model = models.get(position);

        String key = "AIzaSyA7BiWThMvwyV-HDJfc6kMtXo6-ctCRCQw";

        tampilVid(holder, key, unggah_model);


        holder.mPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailVIdeo.class);
                intent.putExtra("idVid", unggah_model.getUrl());
                context.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        YouTubeThumbnailView mPlayer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mPlayer = itemView.findViewById(R.id.vid_yt);


       }
    }


    private void tampilVid(ViewHolder holder, String key, final Unggah_model unggah_model) {
//        holder.mPlayer.initialize(key, new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
//                if (!wasRestored){
//                    youTubePlayer.cueVideo(unggah_model.getUrl());
//                    youTubePlayer.play();
//                }
//            }
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        });


        final String vid = unggah_model.getUrl();

        holder.mPlayer.initialize(key, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(vid);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }


}
