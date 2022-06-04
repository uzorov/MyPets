package mirea.it.mypets.registrationandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import mirea.it.mypets.Pet;
import mirea.it.mypets.R;
import mirea.it.mypets.mainactivities.GeneralLayout;
import mirea.it.mypets.mainactivities.ImageSaver;
import mirea.it.mypets.usefull.PreferenceClass;

/**
 * Класс представляет приветстеенную страницу, которую пользователь видит некоторое время.
 * По прошествии нескольких секунд страница перенаправляет пользователя на главную страницу.
 * Никаких действий со стороны пользователя не требуется.
 */
public class helloPage extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference storageRef;
    private int countofimages;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private DatabaseReference mDatabase;
    private TextView helloPet;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Pet pet;

    PreferenceClass preferenceClass;
    String currentPhotoPath;

    void helloPage()
    {
        this.countofimages = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_page);

        mAuth = FirebaseAuth.getInstance();

        preferenceClass = new PreferenceClass(helloPage.this);

        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String petID = currentUser.getUid();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child(currentUser.getUid() + "/");
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
                    updateCountOfImages();
                    downloadAndSavePhotos();
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

    private void downloadAndSavePhotos() {
        for (int i = 1; i <= countofimages; i++) {
            String photoName = "pet" + Integer.toString(i);
            int position = i;
            storageRef.child(photoName).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    // Use the bytes to display the image
                    new ImageSaver(helloPage.this).
                            setFileName(photoName).
                            setDirectoryName("images").
                            save(bitmap);
                    Log.d("dddd", "Фотография скачана и сохранена");
                    Log.d("dddd", (helloPage.this).getApplicationInfo().dataDir);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("dddd", "Фотография НЕ скачана и не сохранена");
                    // Handle any errors
                }
            });
        }
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


    public int updateCountOfImages() {

       countofimages = preferenceClass.getCount(currentUser.getUid());

        Log.d("count", "HelloPage_countofimages: " + Integer.toString(countofimages));

        return countofimages;
    }

}