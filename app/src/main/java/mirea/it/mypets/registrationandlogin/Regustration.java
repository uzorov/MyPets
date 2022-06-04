package mirea.it.mypets.registrationandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import mirea.it.mypets.Pet;
import mirea.it.mypets.R;
import mirea.it.mypets.usefull.PreferenceClass;

/**
 * Метод, позволяюший зарегистрироваться пользователю в системе
 * В теле метода выполняются следующие действия:
 * Получение типа животного, имени животного и пароля
 * Создание адреса почты при помощи метода transliteration()
 * Регистрация пользователя в системе с почтой и паролем
 * Сохранение в базе данных типа животного,
 * имени животного и пароля по id только что созданного пользователя
 */
public class Regustration extends AppCompatActivity {

    PreferenceClass preferenceClass;

    FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private RadioGroup radioGroup;
    private RadioButton mCat;
    private RadioButton mDog;
    String typeOfPet;
    private EditText mPetName;
    private EditText mPassword;
    private Button mButton;
    Map<Character, String> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regustration);


        mAuth = FirebaseAuth.getInstance();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case -1:
                        Log.d("Reg", "Nothingselected");
                        break;
                    case R.id.Cat:
                        typeOfPet = "Кот (Кошка)";
                        break;
                    case R.id.Dog:
                        typeOfPet = "Собака";
                        break;
                }
            }
        });

        mPetName = (EditText) findViewById(R.id.petNameReg);
        mPassword = (EditText) findViewById(R.id.petPasswordReg);
        mCat = (RadioButton) findViewById(R.id.Cat);
        mDog = (RadioButton) findViewById(R.id.Dog);
        mButton = (Button) findViewById(R.id.signUpButton);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mButton.setOnClickListener(new View.OnClickListener() {
                                       /**
                                        * @param view - параметр, позволяющий получить id кнопки, которая была нажата
                                        *                  Метод вызывается при нажатии на кнопку: "Зарегистрировать питомца"
                                        *
                                        */
                                       @Override
                                       public void onClick(View view) {
                                           if (!mCat.isChecked() && !mDog.isChecked()) {
                                               Toast.makeText(Regustration.this, "Выберите вид животного", Toast.LENGTH_SHORT).show();
                                           } else if ((mPetName.getText()).toString().equals("")) {
                                               Toast.makeText(Regustration.this, "Введите имя питомца", Toast.LENGTH_SHORT).show();
                                           } else if ((mPassword.getText()).toString().equals("")) {
                                               Toast.makeText(Regustration.this, "Введите пароль", Toast.LENGTH_SHORT).show();
                                           } else {
                                               SignUp(transliteration(mPetName.getText().toString().toLowerCase(Locale.ROOT)), mPassword.getText().toString());
                                           }
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


    public String transliteration(String petName) {
        String petEmail;
        petEmail = "";
        for (int i = 0; i < petName.length(); i++) {
            petEmail = petEmail + dictionary.get(petName.charAt(i));
        }
        petEmail = petEmail + "@mail.ru";
        return petEmail;
    }

    public void SignUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            /**
             * Метод, который вызывается после регистрации нового пользователя с почтой и паролем.
             * В случае успешной регистрации пользователь попадает на следующую страницу.
             *
             */
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Regustration.this, "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                    wrtiteToDataBase(mPetName.getText().toString(), typeOfPet);
                    Intent intent = new Intent(Regustration.this, helloPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Regustration.this, "Произошла какая-то ошибка... Попробуйте ещё", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void wrtiteToDataBase(String name, String typeOfPet) {
        currentUser = mAuth.getCurrentUser();

        preferenceClass = new PreferenceClass(this);

        preferenceClass.setCount(1, currentUser.getUid());


        Pet pet = new Pet(name, typeOfPet);
        mDatabase.child("pets").child(currentUser.getUid()).setValue(pet);
    }
}

