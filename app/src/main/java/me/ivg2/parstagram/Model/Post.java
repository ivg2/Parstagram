package me.ivg2.parstagram.Model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_IMAGE = "Image";
    private static final String KEY_USER = "User";
    private static final String KEY_LIKES = "Likes";

    public boolean isFavorited = false;

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public Date getTime() {
        return getCreatedAt();
    }

    public int getLikes() {return getInt(KEY_LIKES);}

    public void setLikes(int likes) {put(KEY_LIKES, likes);}

    public boolean getIsLiked() {
        return getBoolean("isLiked");
    }

    public void setIsLiked(boolean isLiked) {
        put("isLiked", isLiked);
    }

    public static class Query extends ParseQuery<Post>{
        public Query() {
            super(Post.class);
        }

        public Query getTop() {
            setLimit(20);
            return this;
        }

        public Query withUser() {
            include("User");
            return this;
        }
    }

}
