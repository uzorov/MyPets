package mirea.it.mypets.MYSQL.MYSQL_GettingData;

import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.GET_USER_DATA_CODE;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import mirea.it.mypets.MYSQL.BackgroundWorkerAssync;
import mirea.it.mypets.R;


/**
 * Загрузочный экран. Получает с сервера данные об авторизованном клиенте, используя
 * текущее имя и пароль пользователя.
 * Сохраняет во внутренеей памяти следующие данные в виде ключ-значение:
 * id клиента
 * Имя клиента
 * Пароль
 * Номер телефона
 * Почта
 * <p>
 * Заявки клиента (по id)
 * Питомцы клиента (по id)
 */
public class LoadingScreenActivity extends AppCompatActivity {


    private TextView helloPet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_page);


        helloPet = (TextView) findViewById(R.id.helloPet);
        // helloPet.setText("Привет, \n".concat("Питомец"));

                    BackgroundWorkerAssync backgroundWorkerAssync = new BackgroundWorkerAssync(LoadingScreenActivity.this, helloPet);
                    backgroundWorkerAssync.execute(GET_USER_DATA_CODE);
                    //Intent intent = new Intent(LoadingScreenActivity.this, GeneralLayout.class);
                    //startActivity(intent);

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }


}