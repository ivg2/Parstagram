package me.ivg2.parstagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    public File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define fragments
        final Fragment fragment1 = new TimelineFragment();
        final Fragment fragment2 = new NewPostFragment();
        final Fragment fragment3 = new ProfileFragment();

        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
        fragmentTransaction1.replace(R.id.homeFragment, fragment1).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //currently I only need two cases - home timeline and creating a post
                switch (item.getItemId()) {
                    case R.id.home:
                        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                        fragmentTransaction1.replace(R.id.homeFragment, fragment1).commit();
                        return true;
                    case R.id.post:
                        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                        fragmentTransaction2.replace(R.id.homeFragment, fragment2).commit();
                        onLaunchCamera();
                        return true;
                    case R.id.person:
                        FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                        fragmentTransaction3.replace(R.id.homeFragment, fragment3).commit();
                }
                return false;
            }
        });
    }


    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "me.ivg2.parstagram", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Intent editPostIntent = new Intent(this, EditPostActivity.class);
                editPostIntent.putExtra("image_path", photoFile.getAbsolutePath());
                startActivity(editPostIntent);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void logout(View v) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(HomeActivity.this, "Logout Failure", Toast.LENGTH_SHORT);
        }
    }
}
