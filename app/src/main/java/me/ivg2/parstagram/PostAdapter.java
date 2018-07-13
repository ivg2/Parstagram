package me.ivg2.parstagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import me.ivg2.parstagram.Model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    List<Post> posts;
    Context context;

    public PostAdapter(List<Post> list) {
        posts = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, viewGroup, false);
        ViewHolder view = new ViewHolder(postView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);

        //populate the views according to the position
        viewHolder.tvUsername.setText(post.getUser().getUsername());
        viewHolder.tvDescription.setText(post.getDescription());
        viewHolder.tvTime.setText(getRelativeTimeAgo(post.getTime()));


        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(viewHolder.ivImage);

        if (post.getUser().getParseFile("ProfilePicture") != null) {
            Glide.with(context)
                    .load(post.getUser().getParseFile("ProfilePicture").getUrl())
                    .into(viewHolder.ivProfileImage);
        }

        //initialize button to correct state
        if(post.getIsLiked()) {
            viewHolder.likeButton.setBackgroundResource(R.drawable.ufi_heart_active);
        } else {
            viewHolder.likeButton.setBackgroundResource(R.drawable.ufi_heart);
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(Date parseDate) {
        String relativeDate = "";
        long dateMillis = parseDate.getTime();
        relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        return relativeDate;
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    /*
        Start the ViewHolder class to store the Post
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvDescription;
        public TextView tvUsername;
        public ImageView ivImage;
        public TextView tvTime;
        public ImageView ivProfileImage;
        public ImageView likeButton;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.postDescription);
            tvUsername = itemView.findViewById(R.id.postUsername);
            tvTime = itemView.findViewById(R.id.currentTime);
            ivImage = itemView.findViewById(R.id.postImage);
            ivProfileImage = itemView.findViewById(R.id.profile);
            likeButton = itemView.findViewById(R.id.likeButton);

            itemView.setOnClickListener(this);
        }

        //when a user clicks on a row show the Post details
        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post post = posts.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, PostDetail.class);

                intent.putExtra("post", post);

                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
