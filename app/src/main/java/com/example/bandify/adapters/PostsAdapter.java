package com.example.bandify.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.bandify.R;
import com.example.bandify.detailactivities.PostDetailsActivity;
import com.example.bandify.models.Post;
import com.example.bandify.models.User;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    public final static String TAG = "PostsAdapter";

    private List<Post> posts;
    private Context context;
    private Fragment fragment;

    public PostsAdapter(List<Post> posts, Context context, Fragment fragment) {
        this.posts = posts;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addAll(List<Post> posts){
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }

    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivUser;
        private TextView tvUser;
        private TextView tvName;
        private ImageView ivPost;
        private TextView tvDes;
        private VideoView vvPost;
        private ImageView ivLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUser = itemView.findViewById(R.id.iv_user);
            tvUser = itemView.findViewById(R.id.tv_post_username);
            tvName = itemView.findViewById(R.id.tv_post_name);
            ivPost = itemView.findViewById(R.id.iv_post);
            tvDes = itemView.findViewById(R.id.tv_post_des);
            vvPost = itemView.findViewById(R.id.vv_post);
            ivLike = itemView.findViewById(R.id.iv_like);
            tvDes.setOnClickListener(this);
        }

        public void bind(final Post post) {
            try {
                tvUser.setText("@" + post.getUser().fetchIfNeeded().getUsername());
                tvName.setText(post.getUser().fetchIfNeeded().getString(User.KEY_USER_SCREEN));
                ParseFile img = post.getUser().fetchIfNeeded().getParseFile(User.KEY_USER_IMAGE);
                if(img != null){
                    Glide.with(context).load(img.getUrl()).transform(new CircleCrop()).error(R.drawable.ic_launcher_foreground).into(ivUser);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vvPost.setVisibility(View.GONE);
            ivPost.setVisibility(View.VISIBLE);
            if(post.getImage() != null){
                Glide.with(context).load(post.getImage().getUrl()).error(R.drawable.ic_launcher_foreground).into(ivPost);
                ivPost.setOnClickListener(this);
            } else{
                ivPost.setVisibility(View.GONE);
            }
            if(post.getVideo()!= null){
                vvPost.setVisibility(View.VISIBLE);
                String videoURL = post.getVideo().getUrl();
                vvPost.setVideoPath(videoURL);
                MediaController mediaController = new MediaController(context);
                vvPost.setMediaController(mediaController);
                vvPost.requestFocus();
                vvPost.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        vvPost.start();
                    }
                });
            }
            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    post.likePost(context);
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null){
                                e.printStackTrace();
                            }
                            Log.i(TAG, context.getString(R.string.post_l_ul) + post.getDescription());
                        }
                    });
                }
            });
            tvDes.setText(post.getDescription());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Post post = posts.get(position);
                Intent i = new Intent(context, PostDetailsActivity.class);
                i.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                context.startActivity(i);
            }
        }
    }
}
