package me.ivg2.parstagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewPostFragment extends Fragment {

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_new_post, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        ImageView profileImage = view.findViewById(R.id.profileImageView);
//
//        if (ParseUser.getCurrentUser().getParseFile("ProfilePicture") != null) {
//            Glide.with(this)
//                    .load(ParseUser.getCurrentUser().getParseFile("ProfilePicture").getUrl())
//                    .into(profileImage);
//        }
    }
}
