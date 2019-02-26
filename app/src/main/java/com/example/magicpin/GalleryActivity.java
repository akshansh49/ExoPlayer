package com.example.magicpin;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class GalleryActivity extends AppCompatActivity {

    private SimpleExoPlayer player;
    private PlayerView playerView;
    boolean playWhenReady=true;
    int currentWindow=0;
    long playBackPosition=0;
    int previousVideoIdex=0;
    int currentVideoIndex=0;
    String urls[];
    MediaSource mediaSource;
    public static final String AUTOPLAY = "playWhenReady";
    public static final String CURRENT_WINDOW_INDEX = "currentWindow";
    public static final String PLAYBACK_POSITION = "playBackPosition";
    public static final String PREVIOUS_VIDEO="previous_video";
    Uri uri;

    private void initializePlayer(){
        if(getIntent().hasExtra("video_url")){
            uri=Uri.parse(getIntent().getStringExtra("video_url"));
        }
        player=ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(),
                new DefaultLoadControl());
        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow,playBackPosition);
        mediaSource=buildMediaSource(uri);
        player.prepare(mediaSource,false,false);
        /*Log.i("Info","initializePlayer()");
        Log.i("Info","CurrentWindow:"+Integer.toString(currentWindow));
        Log.i("Info","PlayBackPosition:"+Long.toString(playBackPosition));
        Log.i("Info","PlayWhenReady:"+Boolean.toString(playWhenReady));*/
    }

    private void releasePlayer(){
        if(player!=null){
            playBackPosition=player.getCurrentPosition();
            currentWindow=player.getCurrentWindowIndex();
            playWhenReady=player.getPlayWhenReady();
            player.release();
            player=null;
        }
    }

    private MediaSource buildMediaSource(Uri uri){
        String userAgent="exoplayer";
        if(uri.getLastPathSegment().contains("m3u8")){
            return new HlsMediaSource.Factory(
                    new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri);
        }
        else{
            return new ExtractorMediaSource.Factory(
                    new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Info","onStart()");
        if(Util.SDK_INT>23){
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        Log.i("Info","onResume()");
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Info","onStop()");
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Info","onPause()");
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Info","onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("Info","Saving the State");
        if (player == null) {
            outState.putLong(PLAYBACK_POSITION, playBackPosition);
            outState.putInt(CURRENT_WINDOW_INDEX, currentWindow);
            outState.putBoolean(AUTOPLAY, playWhenReady);
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor = getSharedPreferences("com.example.magicpin", MODE_PRIVATE).edit();
        editor.putLong("playBackPosition", playBackPosition);
        editor.putInt("currentWindow", currentWindow);
        editor.putBoolean("playWhenReady",playWhenReady);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        playerView=(PlayerView)findViewById(R.id.player_view);
        if (savedInstanceState != null) {
            playBackPosition = savedInstanceState.getLong(PLAYBACK_POSITION,0);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX,0);
            playWhenReady = savedInstanceState.getBoolean(AUTOPLAY,false);
        }
        else{
            SharedPreferences prefs = getSharedPreferences("com.example.magicpin", MODE_PRIVATE);
            playBackPosition= prefs.getLong("playBackPosition",0);
            currentWindow= prefs.getInt("currentWindow", 0);
            playWhenReady=prefs.getBoolean("playWhenReady",false);

        }
    }
}
