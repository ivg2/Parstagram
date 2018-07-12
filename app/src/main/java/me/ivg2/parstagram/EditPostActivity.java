package me.ivg2.parstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import me.ivg2.parstagram.Model.Post;

public class EditPostActivity extends AppCompatActivity {

    String imagePath;
    public final String APP_TAG = "MyCustomApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        Intent intent = getIntent();
        imagePath = intent.getStringExtra("image_path");
        // by this point we have the camera photo on disk
        Bitmap takenImage = BitmapFactory.decodeFile(imagePath);
        // RESIZE BITMAP, see section
        // Load the taken image into a preview
        ImageView ivPreview = (ImageView) findViewById(R.id.imageView);
        ivPreview.setImageBitmap(takenImage);
    }

    public void post(View v) {
        // on some click or some loading we need to wait for...
        ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        //make a Post object and add it to the timeline
        Post post = new Post();

        EditText descriptionInput = findViewById(R.id.editText);
        String description = descriptionInput.getText().toString();
        post.setDescription(description);

        post.setUser(ParseUser.getCurrentUser());
        post.setLikes(0);

        //get the photo url and add it to the post
        String photoFileName = "photo.jpg";
        File file = getPhotoFileUri(photoFileName);
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

        //pass the user back to the timeline screen
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        pb.setVisibility(ProgressBar.INVISIBLE);
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }
}
