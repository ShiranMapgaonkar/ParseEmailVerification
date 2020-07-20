package com.example.parseemailverification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edtlogin, edtloginpw;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtlogin=findViewById(R.id.edtlogin);
        edtloginpw=findViewById(R.id.edtloginpw);
    }

    public void loginpressed(View btnView) {

        ParseUser.logInInBackground(edtlogin.getText().toString(), edtloginpw.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    if (parseUser.getBoolean("email verified")) {
                        alertDisplayer("Login succesful", "Welcome" + edtlogin.getText().toString() + "!", false);
                    } else {
                        parseUser.logOut();
                        alertDisplayer("Login failed", "Please verify your username & pw.", true);
                    }
                } else {
                    parseUser.logOut();
                    alertDisplayer("Login failed", e.getMessage() + "Please retry.", true);
                }
            }
            });


    }

    private void alertDisplayer(String title, String message,final boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}