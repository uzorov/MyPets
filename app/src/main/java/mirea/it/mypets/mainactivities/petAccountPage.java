package mirea.it.mypets.mainactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import mirea.it.mypets.Pet;
import mirea.it.mypets.R;
import mirea.it.mypets.usefull.PreferenceClass;

public class petAccountPage extends AppCompatActivity {

    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Pet pet;
    PreferenceClass preferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_account_page);

        preferenceClass = new PreferenceClass(petAccountPage.this);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("pets").child(currentUser.getUid());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                pet = dataSnapshot.getValue(Pet.class);

                TextView petName = (TextView) findViewById(R.id.petSetAccountPageName);
                TextView petType = (TextView) findViewById(R.id.petSetAccountPageType);

                petName.setText(pet.name);
                petType.setText(pet.type);

                ImageView imageView = (ImageView) findViewById(R.id.accountPhoto);

                if (Objects.equals(pet.type, "Кот (Кошка)"))
                {
                    Picasso.get()
                            .load(R.drawable.the_red_or_white_cat_i_on_white_studio)
                            .resize(100, 150)
                            .into(imageView);
                }
                else
                {
                    Picasso.get()
                            .load(R.drawable.pexels_poodles_doodles_1458926)
                            .into(imageView);
                }
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("onCancellec", "loadPost:onCancelled", databaseError.toException());
            }
        };

        TextView cloudPhotosNumber = (TextView) findViewById(R.id.petSetAccountPageNumberOdCloudPhotos);
        cloudPhotosNumber.setText(Integer.toString(preferenceClass.getCount(currentUser.getUid())-1).concat(" фото в облачном хранилище"));

        mDatabase.addValueEventListener(postListener);
    }
}