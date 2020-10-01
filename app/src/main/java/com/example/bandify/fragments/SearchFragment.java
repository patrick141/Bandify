package com.example.bandify.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bandify.R;
import com.example.bandify.adapters.PostsAdapter;
import com.example.bandify.databinding.FragmentSearchBinding;
import com.example.bandify.models.Post;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";
    private SearchView searchView;
    private TabLayout tabLayout;
    private RecyclerView rvList;
    private List<Post> posts;
    private PostsAdapter pAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchView);
        tabLayout = view.findViewById(R.id.tl_search);

        posts = new ArrayList<>();
        rvList = view.findViewById(R.id.rv_list);
        pAdapter = new PostsAdapter(posts, getContext(), this);
        rvList.setAdapter(pAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(linearLayoutManager);

        searchView.setQueryHint(getString(R.string.sample_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                querySearchPosts(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!s.isEmpty()){
                    querySearchPosts(s);
                }
                return true;
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                return;
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                return;
            }
        });
    }

    private void querySearchPosts(String input) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.whereFullText(Post.KEY_DESCRIPTION, input);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    e.printStackTrace();
                }
                for(Post post: posts){
                    Log.i(TAG,post.getUser().toString() + post.getImage() + "");
                }
                pAdapter.clear();
                pAdapter.addAll(posts);
            }
        });
    }

}