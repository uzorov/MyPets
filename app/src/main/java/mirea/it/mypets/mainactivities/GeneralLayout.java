package mirea.it.mypets.mainactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import mirea.it.mypets.R;
import mirea.it.mypets.MYSQL.MySQL_Login.LoginActivity;

public class GeneralLayout extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_layout);

        ImageButton galleryButton = (ImageButton) findViewById(R.id.gallery);
        ImageButton advicesButton = (ImageButton) findViewById(R.id.advices);
        ImageView accountButton = (ImageView) findViewById(R.id.account);
        TextView signOut = (TextView) findViewById(R.id.signOut);


        galleryButton.setOnClickListener(new View.OnClickListener() {
                                             /**
                                              * @param view - параметр, позволяющий получить id кнопки, которая была нажата
                                              *                  Метод вызывается при нажатии на кнопку: "Зарегистрировать питомца"
                                              *
                                              */
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(GeneralLayout.this, photoStorage.class);
                                                 startActivity(intent);
                                             }
                                         }
        );




        advicesButton.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent(GeneralLayout.this, advicesPage.class);
                                                 startActivity(intent);
                                             }
                                         }
        );

        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneralLayout.this, petAccountPage.class);
                startActivity(intent);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(GeneralLayout.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}