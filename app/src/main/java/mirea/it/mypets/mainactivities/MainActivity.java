package mirea.it.mypets.mainactivities;

import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.clientsApplications;
import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.currentClient;
import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.pets;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mirea.it.mypets.MYSQL.MYSQL_ENTITYS.Client;
import mirea.it.mypets.MYSQL.MySQL_Login.LoginActivity;
import mirea.it.mypets.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_layout);

        ImageButton galleryButton = (ImageButton) findViewById(R.id.application_link_button_general_activity);
        ImageButton advicesButton = (ImageButton) findViewById(R.id.advices);
        ImageView accountButton = (ImageView) findViewById(R.id.account);
        TextView signOut = (TextView) findViewById(R.id.signOut);

        Log.d("currentClientTest", "Now I should kept him in memory: " + currentClient.toString());

        galleryButton.setOnClickListener(new View.OnClickListener() {
                                             /**
                                              * @param view - параметр, позволяющий получить id кнопки, которая была нажата
                                              *                  Метод вызывается при нажатии на кнопку: "Зарегистрировать питомца"
                                              *
                                              */
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(MainActivity.this, ApplicationActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );


        advicesButton.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(MainActivity.this, NewsAboutPetsActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClientAccountPage.class);
                startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientsApplications.clear();
                currentClient = new Client();
                pets.clear();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}