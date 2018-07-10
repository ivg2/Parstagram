package me.ivg2.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText handleInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameInput = findViewById(R.id.name);
        handleInput = findViewById(R.id.handle);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        enter = findViewById(R.id.enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nameInput.getText().toString();
                final String handle = handleInput.getText().toString();
                final String email = emailInput.getText().toString();
                final String password = passwordInput.getText().toString();

                //create the ParseUser from the given information
                ParseUser user = new ParseUser();
                user.setUsername(handle);
                user.setPassword(password);
                user.setEmail(email);

                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            //transfer user to the homesceen of the app
                            MainActivity activity = new MainActivity();
                            activity.login(handle, password);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
