package me.ivg2.parstagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ivg2.parstagram.Model.Post;

public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.ViewHolder>{
    private List<Post> posts;
    Context context;

    public GridAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.grid_post_view, viewGroup, false);
        GridAdapter.ViewHolder view = new GridAdapter.ViewHolder(postView);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder viewHolder, int i) {
        Post post = posts.get(i);

        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(viewHolder.post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView post;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            post = itemView.findViewById(R.id.gridPost);
        }
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
}
