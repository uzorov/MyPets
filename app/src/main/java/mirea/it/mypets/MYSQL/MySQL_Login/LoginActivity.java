package mirea.it.mypets.MYSQL.MySQL_Login;

import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.LOG_IN_CODE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import mirea.it.mypets.MYSQL.BackgroundWorkerAssync;
import mirea.it.mypets.MYSQL.MySQL_Registration.RegistrationActivity;
import mirea.it.mypets.R;
import mirea.it.mypets.mainactivities.LoginAdminActivity;

public class LoginActivity extends AppCompatActivity {


    private EditText mName;
    private EditText mPassword;
    private ImageView adminAccess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        adminAccess = findViewById(R.id.imageViewAdministrator);
        adminAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LoginAdminActivity.class);
                startActivity(intent);
            }
        });

        mName = (EditText) findViewById(R.id.ClientName);
        mPassword = (EditText) findViewById(R.id.Password);

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.signInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackgroundWorkerAssync backgroundWorkerAssync = new BackgroundWorkerAssync(LoginActivity.this);
                backgroundWorkerAssync.execute(LOG_IN_CODE, mName.getText().toString(), mPassword.getText().toString());
            }
        }); //Вход
        findViewById(R.id.imageViewAdministrator); //Вход для администраторов






    }


    /**
     * Метод проверяет авторизован ли пользователь, если да - повторая авторизация не потребуется.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Метод, который авторизует пользователя в системе
     */
    public void SignIn() {

        String email =  mName.getText().toString();
        String password = mPassword.getText().toString();

    }


}