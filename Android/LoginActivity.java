package com.example.alexandre.chatclient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText host;
    private EditText porta;
    private EditText username;
    private View mConectView;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        host = (EditText) findViewById(R.id.host);
        porta = (EditText) findViewById(R.id.porta);
        username = (EditText) findViewById(R.id.username);

        Button conectar = (Button) findViewById(R.id.conectar_button);

        mConectView = findViewById(R.id.login_form);

    }

    public void conectarServer(View v){
        i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("username", getUserName());
        i.putExtra("host", getHost());
        i.putExtra("porta", getPorta());

        startActivity(i);
    }

    public String getHost(){
        return host.getText().toString();
    }

    public String getPorta(){
        return porta.getText().toString();
    }

    public String getUserName(){
        return username.getText().toString();
    }
}

