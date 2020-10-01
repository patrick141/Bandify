package com.example.bandify.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;

@ParseClassName("Band")
public class Band extends ParseObject {
    public static final String KEY_NAME = "name";
    public static final String KEY_LEADER = "leader";
    public static final String KEY_SIZE = "size";
    public static final String KEY_DES = "description";
    public static final String KEY_PRIVATE = "private";
    public static final String KEY_MEMBERS = "members";
    public static final String KEY_REQUESTS = "requests";

    public Band(){}

    public String getName() {
        try {
            return fetchIfNeeded().getString(KEY_NAME);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setName(String name){put(KEY_NAME, name);}

    public String getDescription() {
        try {
            return fetchIfNeeded().getString(KEY_DES);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setDescription(String description){put(KEY_DES, description);}

    public ParseUser getLeader() {
        try {
            return fetchIfNeeded().getParseUser(KEY_LEADER);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setLeader(ParseUser leader){put(KEY_LEADER, leader);}

    public int getSize() {
        try {
            return fetchIfNeeded().getInt(KEY_SIZE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setSize(int size){put(KEY_SIZE, size);}

    public ArrayList<ParseUser> getMembers() {
        try {
            return(ArrayList<ParseUser>) fetchIfNeeded().get(KEY_MEMBERS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isPrivate() {
        try {
            return fetchIfNeeded().getBoolean(KEY_PRIVATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setPrivate(boolean bool){put(KEY_PRIVATE, bool);}

    public int getCurrSize(){return getMembers() != null ? getMembers().size():0;}
}
