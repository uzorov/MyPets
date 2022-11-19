package mirea.it.mypets.MYSQL.MySQL_Registration;

import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.SIGN_UP_CODE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import mirea.it.mypets.MYSQL.BackgroundWorkerAssync;
import mirea.it.mypets.R;

/**
 * Метод, позволяюший зарегистрироваться пользователю в системе
 * В теле метода выполняются следующие действия:
 * Получение типа животного, имени животного и пароля
 * Создание адреса почты при помощи метода transliteration()
 * Регистрация пользователя в системе с почтой и паролем
 * Сохранение в базе данных типа животного,
 * имени животного и пароля по id только что созданного пользователя
 */
public class RegistrationActivity extends AppCompatActivity {




    String typeOfPet;
    private EditText mClientsPhoneNumber;
    private EditText mClientsMailAddress;
    private EditText mClientsName;
    private EditText mPassword;
    private Button setNewUserButton;
    Map<Character, String> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        mClientsName = (EditText) findViewById(R.id.ClientsName);
        mPassword = (EditText) findViewById(R.id.ClientsPassword);
        mClientsMailAddress = (EditText) findViewById(R.id.ClientsMailAddress);
        mClientsPhoneNumber = (EditText) findViewById(R.id.ClientsPhoneNumber);

        setNewUserButton = (Button) findViewById(R.id.signUpButton);
        setNewUserButton.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View view) {
                                                    OnSignUp();
                                                }
                                            }
        );
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
        dictionary.put(' ', "");
    }


    public String transliteration(String userRussianName) {
        String userEnglishName;
        userEnglishName = "";
        for (int i = 0; i < userRussianName.length(); i++) {
            userEnglishName = userEnglishName + dictionary.get(userRussianName.charAt(i));
        }
        return userEnglishName;
    }

    public void OnSignUp() {
        String clientsName = mClientsName.getText().toString();
        String password = mPassword.getText().toString();
        String clientsPhoneNumber = mClientsPhoneNumber.getText().toString();
        String clientsMail = mClientsMailAddress.getText().toString();


        BackgroundWorkerAssync backgroundWorkerAssync = new BackgroundWorkerAssync(this);
        backgroundWorkerAssync.execute(SIGN_UP_CODE, clientsName, password, clientsPhoneNumber, clientsMail);


    }


}

