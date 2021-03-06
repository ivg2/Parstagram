package me.ivg2.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.Date;

import me.ivg2.parstagram.Model.Post;

public class PostDetail extends AppCompatActivity {

    public Post post;
    public TextView username;
    public TextView time;
    public TextView description;
    public ImageView image;
    public TextView likes;
    public ImageView ivProfileImage;

    public ImageButton likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        username = findViewById(R.id.username);
        time = findViewById(R.id.timeStamp);
        description = findViewById(R.id.details);
        image = findViewById(R.id.image);
        likes = findViewById(R.id.likes);
        likeButton = findViewById(R.id.likeButton);
        ivProfileImage = findViewById(R.id.profile);

        Intent intent = getIntent();

        post = intent.getParcelableExtra("post");

        username.setText(post.getUser().getUsername());
        time.setText(getRelativeTimeAgo(post.getTime()));
        description.setText(post.getDescription());

        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(image);

        if (post.getUser().getParseFile("ProfilePicture") != null) {
            Glide.with(this)
                    .load(post.getUser().getParseFile("ProfilePicture").getUrl())
                    .into(ivProfileImage);
        }

        //initialize button to correct state
        if(post.getIsLiked()) {
            likeButton.setBackgroundResource(R.drawable.ufi_heart_active);
        } else {
            likeButton.setBackgroundResource(R.drawable.ufi_heart);
        }
        likes.setText(Integer.toString(post.getLikes()));
        update();
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(Date parseDate) {
        String relativeDate = "";
        long dateMillis = parseDate.getTime();
        relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        return relativeDate;
    }

    //can like or dislike a post with this method
    public void favorite(View v) {
        if(post.getIsLiked()) {
            likeButton.setBackgroundResource(R.drawable.ufi_heart);
            post.setIsLiked(false);
            post.setLikes(post.getLikes() - 1);
        } else {
            likeButton.setBackgroundResource(R.drawable.ufi_heart_active);
            post.setIsLiked(true);
            post.setLikes(post.getLikes() + 1);
        }
        likes.setText(Integer.toString(post.getLikes()));
        update();
    }

    public void update() {
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create Post Success");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
