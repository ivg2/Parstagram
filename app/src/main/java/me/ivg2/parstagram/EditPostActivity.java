package me.ivg2.parstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import me.ivg2.parstagram.Model.Post;

public class EditPostActivity extends AppCompatActivity {

    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        Intent intent = getIntent();
        imagePath = intent.getStringExtra("image_path");
        // by this point we have the camera photo on disk
        Bitmap takenImage = BitmapFactory.decodeFile(imagePath);
        // RESIZE BITMAP, see section below
        // Load the taken image into a preview
        ImageView ivPreview = (ImageView) findViewById(R.id.imageView);
        ivPreview.setImageBitmap(takenImage);
    }

    public void post(View v) {
        //make a Post object and add it to the timeline
        Post post = new Post();

        EditText descriptionInput = findViewById(R.id.editText);
        String description = descriptionInput.getText().toString();
        post.setDescription(description);

        post.setUser(ParseUser.getCurrentUser());

        //get the photo url and add it to the post
        HomeActivity home = new HomeActivity();
        home.onLaunchCamera();
        File file = home.photoFile;
        ParseFile parseFile = new ParseFile(file);
        post.setImage(parseFile);

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
