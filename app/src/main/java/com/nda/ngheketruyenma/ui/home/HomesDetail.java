package com.nda.ngheketruyenma.ui.home;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nda.ngheketruyenma.R;
import com.nda.ngheketruyenma.ui.API;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class HomesDetail extends  YouTubeBaseActivity implements View.OnClickListener {
    ImageView img_story, img_Back, img_playPauseMp3;
    TextView txt_storyAuthor,txt_storyTitle, txt_contentStory,  txt_currentTime, txt_totalDuration;
    String author, storyName, contentStory, mp3, img;
    Intent intent;
    Bundle bundle;
    MediaPlayer mediaPlayer;
    SeekBar playerSeekBar;

    private Handler handler = new Handler();

    private YouTubePlayerView youtubePlayer_view;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes_detail);
        getWindow().setStatusBarColor(ContextCompat.getColor(HomesDetail.this,R.color.black));
        mapting();

        img_Back.setOnClickListener(this);





        if (bundle.containsKey("storyDetail"))
        {
            author = intent.getStringExtra("authorDetail");
            storyName = intent.getStringExtra("storyNameDetail");
            contentStory = intent.getStringExtra("storyContentDetail");
            mp3 = intent.getStringExtra("storyMp3Detail");
            img = intent.getStringExtra("storyImgDetail");

            txt_storyAuthor.setText(author);
            txt_storyTitle.setText(storyName);
            txt_contentStory.setText(contentStory);

            Picasso.get().load(img).into(img_story);

            /*
                Load vid from youtube
             */
            onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                    youTubePlayer.loadVideo(mp3);
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                }
            };
            youtubePlayer_view.initialize(API.API_KEY,onInitializedListener);


//            playerSeekBar.setMax(100);
//            img_playPauseMp3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (mediaPlayer.isPlaying())
//                    {
//                        handler.removeCallbacks(updater);
//                        mediaPlayer.pause();
//                        img_playPauseMp3.setImageResource(R.drawable.ic_play);
//
//                    }
//                    else
//                    {
//                        mediaPlayer.start();
//                        img_playPauseMp3.setImageResource(R.drawable.ic_pause);
//                        updateSeekBar();
//                    }
//                }
//            });
//
//            prepareMp3(mp3);
//
//            /*
//
//                Go to the specific position ( of mp3 ) in seekBar
//             */
//            playerSeekBar.setOnTouchListener(new View.OnTouchListener() {
//                @SuppressLint("ClickableViewAccessibility")
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    SeekBar seekBar = (SeekBar) v;
//                    int playPosition = (mediaPlayer.getDuration() / 100) *  seekBar.getProgress();
//
//                    mediaPlayer.seekTo(playPosition);
//                    txt_currentTime.setText(milillSecondToTime(mediaPlayer.getCurrentPosition()));
//                    return false;
//                }
//            });
//
//            /*
//
//                Create buffering ( of mp3 ) in seekBar = Tạo các bóng trên thanh seekBar
//                    ( biểu thị cho việc mp3 đã load đến đâu )
//             */
//
//            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//                @Override
//                public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                    playerSeekBar.setSecondaryProgress(percent);
//                }
//            });
//
//
//            /*
//
//                When the mp3 run complete
//             */
//
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    playerSeekBar.setProgress(0);
//                    img_playPauseMp3.setImageResource(R.drawable.ic_play);
//                    txt_currentTime.setText("0");
//                    txt_totalDuration.setText("0");
//                    mediaPlayer.reset();
//                    prepareMp3(mp3);
//                }
//            });
//
       }
    }

    private void prepareMp3(String mp3) {
//        img_playMp3.setVisibility(View.GONE);
//        img_stopyMp3.setVisibility(View.VISIBLE);
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(mp3);
            mediaPlayer.prepare();
//            mediaPlayer.start();
            txt_totalDuration.setText(milillSecondToTime(mediaPlayer.getDuration()));

//            img_playMp3.setVisibility(View.VISIBLE);
//            img_stopyMp3.setVisibility(View.GONE);
//            mediaPlayer.pause();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*

        Setup seekBar with total as well as current time.

     */

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            txt_currentTime.setText(milillSecondToTime(currentDuration));

        }
    };

    private void updateSeekBar()
    {
        if (mediaPlayer.isPlaying())
        {
            playerSeekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater,1000);

        }
    }

    private String milillSecondToTime(long milliSecond)
    {
        String timeString = "";
        String secondString;

        int hour = (int)(milliSecond / (1000*60*60));
        int minute = (int)(milliSecond % (1000*60*60)/(1000*60));
        int second = (int)((milliSecond % (1000*60*60)) % (1000*60) / 1000);

        if (hour > 0)
        {
            timeString = hour + ":";

        }

        if (second < 10)
        {
            secondString = "0" + second;
        }
        else
        {
            secondString = "" + second;
        }
        timeString = timeString + minute + ":" + secondString;

        return timeString;
    }

    /*

     (End) Setup seekBar with total as well as current time.

  */
    private void mapting() {
        youtubePlayer_view = (YouTubePlayerView) findViewById(R.id.youtubePlayer_view);

        intent = getIntent();
        bundle = intent.getExtras();

        img_Back    = (ImageView) findViewById(R.id.img_Back);
        img_story   = (ImageView) findViewById(R.id.img_story);
        txt_storyAuthor    = (TextView) findViewById(R.id.txt_storyAuthor);
        txt_storyTitle     = (TextView) findViewById(R.id.txt_storyTitle);
        txt_contentStory   = (TextView) findViewById(R.id.txt_contentStory);

        mediaPlayer = new MediaPlayer();
//        txt_currentTime     = (TextView) findViewById(R.id.txt_currentTime);
//        txt_totalDuration   = (TextView) findViewById(R.id.txt_totalDuration);
//        playerSeekBar       = (SeekBar) findViewById(R.id.playerSeekBar);
//        img_playPauseMp3    = (ImageView) findViewById(R.id.img_playPauseMp3);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_Back)
        {
            mediaPlayer.stop();
            finish();
        }
    }
}