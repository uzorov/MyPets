package mirea.it.mypets.mainactivities;

import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.GET_USERS_APPLICATIONS_CODE;
import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.GET_USER_DATA_CODE;
import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.currentClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import mirea.it.mypets.MYSQL.BackgroundWorkerAssync;
import mirea.it.mypets.MYSQL.MYSQL_ENTITYS.Client;
import mirea.it.mypets.MYSQL.MySQL_Login.LoginActivity;
import mirea.it.mypets.R;

public class LoginAdminActivity extends AppCompatActivity  {

    EditText nEditText;
    EditText pEditText;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);



        nEditText = (EditText) findViewById(R.id.AdminName);
        pEditText = (EditText) findViewById(R.id.AdminPassword);
        findViewById(R.id.AdminButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nEditText.getText().toString().equals("admin")
                        && pEditText.getText().toString().equals("admin")) {

                    currentClient = new Client();
                    currentClient.setId("-1");
                    BackgroundWorkerAssync backgroundWorkerAssync = new BackgroundWorkerAssync(LoginAdminActivity.this);
                    backgroundWorkerAssync.execute(GET_USERS_APPLICATIONS_CODE);
                }
            }
        });







    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.


    }


}