package mirea.it.mypets.registrationandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mirea.it.mypets.R;
import mirea.it.mypets.mainactivities.GeneralLayout;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener  {

    EditText nEditText;
    EditText pEditText;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();

        nEditText = (EditText) findViewById(R.id.AdminName);
        pEditText = (EditText) findViewById(R.id.AdminPassword);
        findViewById(R.id.AdminButton).setOnClickListener(this);
        findViewById(R.id.adminOut).setOnClickListener(this);



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            findViewById(R.id.adminOut).setVisibility(View.INVISIBLE);
        }

        if (currentUser != null) {
            currentUser.reload();
        }
        if (currentUser != null) {
            nEditText.setVisibility(View.INVISIBLE);
            pEditText.setVisibility(View.INVISIBLE);
            findViewById(R.id.AdminButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.adminOut).setVisibility(View.VISIBLE);

            String url = "https://console.firebase.google.com/project/mypets-78fa7/overview";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }

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
                    Toast.makeText(AdminActivity.this, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                    String url = "https://console.firebase.google.com/project/mypets-78fa7/overview";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else {
                    Toast.makeText(AdminActivity.this, "Произошла какая-то ошибка... Попробуйте ещё раз", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.AdminButton) {
            SignIn(nEditText.getText().toString(), pEditText.getText().toString());
        } else {
            SignOut();
        }
    }

    private void SignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent2 = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent2);
    }
}