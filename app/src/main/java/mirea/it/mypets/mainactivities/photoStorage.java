package mirea.it.mypets.mainactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.*;
import android.util.*;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import mirea.it.mypets.R;

public class photoStorage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        Log.d("count", "Im in the photoStorage");
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_photo_storage);

        Toolbar _toolbar = (Toolbar) findViewById(R.id._toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {

                Intent intent = new Intent(photoStorage.this, GeneralLayout.class);
                startActivity(intent);
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();

    }
}