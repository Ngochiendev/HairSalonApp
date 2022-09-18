package com.ngochien.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.ngochien.myapplication.Model.TV;
import com.ngochien.myapplication.R;

public class ShowYouTubeActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView youTubePlayerView;
    TV tv;
    int REQUEST_VIDEO=123;
    String API_KEY="AIzaSyCWEkjrTbimrCR-m-jLLxR_KFRLx52fEaw";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_you_tube);
        youTubePlayerView = findViewById(R.id.myYoutube);

        intent();
        youTubePlayerView.initialize(API_KEY,this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean b) {
        Log.d("id",tv.getLinkvideo()) ;
        youTubePlayer.cueVideo(tv.getLinkvideo());
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(ShowYouTubeActivity.this,REQUEST_VIDEO);
        }
        else{
            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_VIDEO){
            youTubePlayerView.initialize(API_KEY,this);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void intent(){
        Intent intent = getIntent();
        if(intent!=null) {
            if (intent.hasExtra("item")) {
                tv = intent.getParcelableExtra("item");
            }
        }
    }
}