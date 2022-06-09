package mirea.it.mypets.registrationandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import mirea.it.mypets.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    private EditText mPetName;
    private EditText mPassword;
    private ImageView imageView;
    private Map<Character, String> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.signInButton).setOnClickListener(this);
        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.imageViewAdministrator).setOnClickListener(this);

        mPetName = (EditText) findViewById(R.id.petName);
        mPassword = (EditText) findViewById(R.id.Password);

        mAuth = FirebaseAuth.getInstance();

        dictionary = new HashMap<Character, String>();
        dictionary.put('а', "a");
        dictionary.put('б', "b");
        dictionary.put('в', "v");
        dictionary.put('г', "g");
        dictionary.put('д', "d");
        dictionary.put('е', "e");
        dictionary.put('ё', "yo");
        dictionary.put('ж', "zh");
        dictionary.put('з', "z");
        dictionary.put('и', "i");
        dictionary.put('й', "j");
        dictionary.put('к', "k");
        dictionary.put('л', "l");
        dictionary.put('м', "m");
        dictionary.put('н', "n");
        dictionary.put('о', "o");
        dictionary.put('п', "p");
        dictionary.put('р', "r");
        dictionary.put('с', "s");
        dictionary.put('т', "t");
        dictionary.put('у', "u");
        dictionary.put('ф', "f");
        dictionary.put('х', "x");
        dictionary.put('ц', "c");
        dictionary.put('ч', "ch");
        dictionary.put('ш', "sh");
        dictionary.put('щ', "shh");
        dictionary.put('ъ', "''");
        dictionary.put('ы', "y");
        dictionary.put('ь', "'");
        dictionary.put('э', "e");
        dictionary.put('ю', "yu");
        dictionary.put('я', "ya");
    }


    /**
     * Метод проверяет авторизован ли пользователь, если да - повторая авторизация не потребуется.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            currentUser.reload();
        }
        if (currentUser != null) {
            Intent intent = new Intent(MainActivity.this, helloPage.class);
            startActivity(intent);
        }
    }

    /**
     * Метод отслеживает какая кнопка была нажата
     * Если была нажата кнопка входа - вызывается метод singIn()
     * Если нажата кнопка регистраци - откроется другой экран
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signInButton) {
            if ((mPetName.getText()).toString().equals("")) {
                Toast.makeText(MainActivity.this, "Введите имя питомца", Toast.LENGTH_SHORT).show();
            } else if ((mPassword.getText()).toString().equals("")) {
                Toast.makeText(MainActivity.this, "Введите пароль", Toast.LENGTH_SHORT).show();
            } else {
                SignIn(transliteration(mPetName.getText().toString().toLowerCase(Locale.ROOT)), mPassword.getText().toString());
            }
        } else if (view.getId() == R.id.signUpButton) {
            Intent intent = new Intent(MainActivity.this, Regustration.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.imageViewAdministrator)
        {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Метод, который авторизует пользователя в системе
     */
    public void SignIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            /**
             * Метод, который вызывается после регистрации нового пользователя с почтой и паролем
             * В случае успешной регистрации пользователь попадает на следующую страницу.
             *
             */
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, helloPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Произошла какая-то ошибка... Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @param petName - Введённое пользователем имя питомца
     * @return Метод возвращает email адрес на английском языке, сформированный на основе имени питомца на русском
     * Процедура транслитерации необходима для хранения информации о пользователе в системе FireBase
     */
    public String transliteration(String petName) {
        String petEmail;
        petEmail = "";
        for (int i = 0; i < petName.length(); i++) {
            petEmail = petEmail + dictionary.get(petName.charAt(i));
        }
        petEmail = petEmail + "@mail.ru";
        return petEmail;
    }
}