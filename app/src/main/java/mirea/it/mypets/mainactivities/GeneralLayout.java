package mirea.it.mypets.mainactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

import mirea.it.mypets.R;
import mirea.it.mypets.registrationandlogin.Regustration;
import mirea.it.mypets.registrationandlogin.helloPage;

public class GeneralLayout extends AppCompatActivity {

    private ImageButton galleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_layout);

        galleryButton = (ImageButton) findViewById(R.id.gallery);

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
    }
}