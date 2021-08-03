package com.nda.ngheketruyenma.ui.home;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nda.ngheketruyenma.BuildConfig;
import com.nda.ngheketruyenma.R;
import com.nda.ngheketruyenma.ui.API;
import com.nda.ngheketruyenma.ui.home.nativeAds.AdapterWithNativeAd;
import com.squareup.picasso.Picasso;
import com.startapp.sdk.ads.banner.BannerListener;
import com.startapp.sdk.ads.banner.Mrec;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.AutoInterstitialPreferences;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomesDetail extends  YouTubeBaseActivity implements View.OnClickListener {
    /*
    Setup native ads
 */
    private static final String LOG_TAG = HomesDetail.class.getSimpleName();

    @Nullable
    protected AdapterWithNativeAd adapter;

    RecyclerView recyclerView;
    LinearLayout ll_rcvHomeDetailNativeAds;

    /*
         (END) Setup native ads
  */
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
        nativeAds();


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
            try
            {
                youtubePlayer_view.initialize(API.API_KEY,onInitializedListener);
            }catch (Exception e)
            {
                Toast.makeText(this, "Error Occur : API (DETAIL)", Toast.LENGTH_SHORT).show();
            }


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

        ll_rcvHomeDetailNativeAds   = (LinearLayout) findViewById(R.id.ll_rcvHomeDetailNativeAds);
//        txt_currentTime     = (TextView) findViewById(R.id.txt_currentTime);
//        txt_totalDuration   = (TextView) findViewById(R.id.txt_totalDuration);
//        playerSeekBar       = (SeekBar) findViewById(R.id.playerSeekBar);
//        img_playPauseMp3    = (ImageView) findViewById(R.id.img_playPauseMp3);


        /*
            Ads
         */

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_Back)
        {
            mediaPlayer.stop();
            finish();
            StartAppAd.showAd(this);
        }



    }

    private void nativeAds() {
        // NOTE always use test ads during development and testing
        //StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

//        setContentView(R.layout.recycler_view);

        recyclerView = findViewById(R.id.rcv_homeDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomesDetail.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter = new AdapterWithNativeAd(HomesDetail.this));

        loadData();
        loadNativeAd();
    }
    private void loadNativeAd() {
        final StartAppNativeAd nativeAd = new StartAppNativeAd(HomesDetail.this);

        nativeAd.loadAd(new NativeAdPreferences()
                .setAdsNumber(1)
                .setAutoBitmapDownload(true)
                .setPrimaryImageSize(2), new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                if (adapter != null) {
                    ll_rcvHomeDetailNativeAds.setVisibility(View.VISIBLE);
                    adapter.setNativeAd(nativeAd.getNativeAds());
                }
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                if (BuildConfig.DEBUG) {
                    recyclerView.setVisibility(View.GONE);
                    Log.v(LOG_TAG, "onFailedToReceiveAd: " + ad.getErrorMessage());
                }
            }
        });
    }

    // TODO example of loading JSON array, change this code according to your needs
    @UiThread
    private void loadData() {
        if (adapter != null) {
//            adapter.setData(Collections.singletonList("Loading..."));
        }

        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            @WorkerThread
            public void run() {
                String url = "https://raw.githubusercontent.com/StartApp-SDK/StartApp_InApp_SDK_Example/master/app/data.json";

                final List<String> data = new ArrayList<>();

                try (InputStream is = new URL(url).openStream()) {
                    if (is != null) {
                        JsonReader reader = new JsonReader(new InputStreamReader(is));
                        reader.beginArray();

                        while (reader.peek() == JsonToken.STRING) {
                            data.add(reader.nextString());
                        }

                        reader.endArray();
                    }
                } catch (RuntimeException | IOException ex) {
                    data.clear();
                    data.add(ex.toString());
                } finally {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (adapter != null) {
//                                adapter.setData(data);
//                            }
//                        }
//                    });
                }
            }
        });
    }
}