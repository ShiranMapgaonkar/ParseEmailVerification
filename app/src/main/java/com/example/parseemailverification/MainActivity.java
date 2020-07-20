package com.example.parseemailverification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText edtemail, edtusername, edtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtemail = findViewById(R.id.edtemail);
        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
    }

    public void signupispressed(View btnView) {
        Toast.makeText(this,"Sign up pressed",Toast.LENGTH_SHORT).show();

        try {
            ParseUser user = new ParseUser();
            user.setUsername(edtusername.getText().toString());
            user.setPassword(edtpassword.getText().toString());
            user.setEmail(edtemail.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e== null) {
                        ParseUser.logOut();
                        alertDisplayer("Account Created Succesfully!","Please verify your email before login",false);
                    } else {
                        ParseUser.logOut();
                        alertDisplayer("Account Creation Failed","Account could not be created" +" :" + e.getMessage(),true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 private void alertDisplayer(String title, String message,final boolean error) {
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    if(!error) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });
    AlertDialog ok = builder.create();
    ok.show();
}
}