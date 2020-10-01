package com.example.bandify;

import android.app.Application;

import com.example.bandify.models.Band;
import com.example.bandify.models.Post;
import com.example.bandify.models.Request;
import com.example.bandify.models.Room;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Band.class);
        ParseObject.registerSubclass(Request.class);
        ParseObject.registerSubclass(Room.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.parse_id))
                .clientKey(getString(R.string.parse_client_key))
                .server(getString(R.string.parse_server))
                .build()
        );
    }
}
