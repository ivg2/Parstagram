package me.ivg2.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.ivg2.parstagram.Model.Post;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("parsta")
                .clientKey("seafbu")
                .server("http://ivg2-parstagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
