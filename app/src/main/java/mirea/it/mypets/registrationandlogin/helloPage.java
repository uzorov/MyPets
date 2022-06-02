package mirea.it.mypets.registrationandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import mirea.it.mypets.Pet;
import mirea.it.mypets.R;
import mirea.it.mypets.mainactivities.GeneralLayout;

/**
 * Класс представляет приветстеенную страницу, которую пользователь видит некоторое время.
 * По прошествии нескольких секунд страница перенаправляет пользователя на главную страницу.
 * Никаких действий со стороны пользователя не требуется.
 */
public class helloPage extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView helloPet;
    FirebaseAuth mAuth;
    Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_page);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String petID = currentUser.getUid();
        mDatabase.child("pets").child(petID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    pet = task.getResult().getValue(Pet.class);
                    if (pet != null) {
                        helloPet = (TextView) findViewById(R.id.helloPet);
                        helloPet.setText("Привет, \n".concat(pet.name));
                    }
                    else
                    {
                        helloPet = (TextView) findViewById(R.id.helloPet);
                        helloPet.setText("Привет, \n".concat("Питомец"));
                    }
                    stop();
                }
            }

            private void stop() {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(3);
                            Intent intent2 = new Intent(helloPage.this, GeneralLayout.class);
                            startActivity(intent2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                thread.start();
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            currentUser.reload();
        }
        if (currentUser == null) {
            Intent intent = new Intent(helloPage.this, MainActivity.class);
            startActivity(intent);
        }
    }
}