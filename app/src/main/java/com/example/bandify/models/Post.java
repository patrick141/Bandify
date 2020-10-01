package com.example.bandify.models;

import android.content.Context;
import android.widget.Toast;

import com.example.bandify.R;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Arrays;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_VIDEO = "video";
    public static final String KEY_RECORDING = "recording";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_COMMENTS = "comments";

    public Post() {}

    public ParseUser getUser() {
        try {
            return fetchIfNeeded().getParseUser(KEY_USER);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getDescription() {
        try {
            return fetchIfNeeded().getString(KEY_DESCRIPTION);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        try {
            return fetchIfNeeded().getParseFile(KEY_IMAGE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseFile getVideo() {
        try {
            return fetchIfNeeded().getParseFile(KEY_VIDEO);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setVideo(ParseFile video) {
        put(KEY_VIDEO, video);
    }

    public ParseFile getRecording() {
        try {
            return fetchIfNeeded().getParseFile(KEY_RECORDING);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setRecording(ParseFile recording) {
        put(KEY_RECORDING, recording);
    }

    public ArrayList<Comment> getComments() {
        try {
            return (ArrayList<Comment>) fetchIfNeeded().get(KEY_COMMENTS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeComment(Comment comment) {
        removeAll(KEY_COMMENTS, Arrays.asList(comment));
    }

    public ArrayList<ParseUser> getLikers(){
        try {
            return (ArrayList<ParseUser>) fetchIfNeeded().get(Post.KEY_LIKES);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getLikes() {
        return getComments() != null ? getComments().size() : 0;
    }

    public void likePost(Context context) {
        ParseUser user = ParseUser.getCurrentUser();
        if(!getLikers().contains(user)){
            Toast.makeText(context, context.getString(R.string.like_post), Toast.LENGTH_SHORT).show();
            add(KEY_LIKES, user);
        } else {
            Toast.makeText(context, context.getString(R.string.unlike_post), Toast.LENGTH_SHORT).show();
            removeAll(KEY_LIKES, Arrays.asList(user));
        }
    }

}
