package me.ivg2.parstagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import me.ivg2.parstagram.Model.Post;

public class ProfileFragment extends Fragment {
    List<Post> posts;
    private GridAdapter gridAdapter;
    private RecyclerView rvPosts;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvPosts= (RecyclerView) view.findViewById(R.id.rvGrid);
        posts = new ArrayList<>();
        gridAdapter = new GridAdapter(posts);

        //RecyclerView setup -- layout manager and use adapter
        rvPosts.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvPosts.setAdapter(gridAdapter);

        retrieveUserPosts();

        gridAdapter.clear();
        gridAdapter.addAll(posts);
    }

    public void retrieveUserPosts() {
        //get all posts using a query
        //grabs all of the posts in the background
        final Post.Query postsQuery = new Post.Query();
        postsQuery.whereEqualTo("User", ParseUser.getCurrentUser());

        postsQuery.addDescendingOrder("createdAt");

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    gridAdapter.clear();
                    gridAdapter.addAll(objects);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
