package com.example.bandify.detailactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.bandify.R;
import com.example.bandify.databinding.ActivityPostDetailsBinding;
import com.example.bandify.databinding.ItemPostBinding;
import com.example.bandify.models.Post;
import com.example.bandify.models.User;
import com.parse.ParseException;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.io.IOException;

public class PostDetailsActivity extends AppCompatActivity {
    private Post post;
    private Button btnMusic;
    private Button btnPause;
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private ImageView ivUser;
    private ImageView ivPost;
    private TextView tvName;
    private TextView tvScr;
    private TextView tvDes;
    private VideoView vvPost;

    private boolean firstTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPostDetailsBinding binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        ItemPostBinding ipBinding = binding.ipDetail;
        ivUser = ipBinding.ivUser;
        ivPost = ipBinding.ivPost;
        tvName = ipBinding.tvPostUsername;
        tvScr = ipBinding.tvPostName;
        tvDes = ipBinding.tvPostDes;
        vvPost = ipBinding.vvPost;

        ivPost.setVisibility(View.VISIBLE);
        vvPost.setVisibility(View.GONE);

        btnMusic = binding.btnPlayMusic;
        btnPause = binding.btnPauseMusic;
        mediaPlayer = new MediaPlayer();
        mediaController =  new MediaController(this);

        btnMusic.setVisibility(View.GONE);
        btnPause.setVisibility(View.GONE);

        try {
            tvScr.setText(post.getUser().fetchIfNeeded().getString(User.KEY_USER_SCREEN));
            tvName.setText("@" + post.getUser().fetchIfNeeded().getUsername());
            ParseFile img = post.getUser().fetchIfNeeded().getParseFile(User.KEY_USER_IMAGE);
            if(img != null){
                Glide.with(this).load(img.getUrl()).transform(new CircleCrop()).error(R.drawable.ic_launcher_foreground).into(ivUser);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvDes.setText(post.getDescription());
        if(post.getImage()!= null) {
            Glide.with(this).load(post.getImage().getUrl()).into(ivPost);
        } else {
            ivPost.setVisibility(View.GONE);
        }
        if(post.getVideo()!= null){
            vvPost.setVisibility(View.VISIBLE);
            String videoURL = post.getVideo().getUrl();
            vvPost.setVideoPath(videoURL);
            vvPost.setMediaController(mediaController);
            vvPost.requestFocus();
            vvPost.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    vvPost.start();
                }
            });
        }

        if(post.getRecording() != null){
            btnMusic.setVisibility(View.VISIBLE);
            btnPause.setVisibility(View.VISIBLE);
        }

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstTime){
                    playMusic();
                } else{
                    mediaPlayer.start();
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });
    }

    private void playMusic(){
        ParseFile music = post.getRecording();
        String url = music.getUrl();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        firstTime = true;
    }
}