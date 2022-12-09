package mirea.it.mypets.mainactivities;

import static mirea.it.mypets.MYSQL.BackgroundWorkerAssync.currentClient;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mirea.it.mypets.MYSQL.BackgroundWorkerAssync;
import mirea.it.mypets.R;

public class ClientAccountPage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_account_page);

        TextView ClientsName = (TextView) findViewById(R.id.ClientsName_ProfilePage);
        TextView ClientsPhoneNumber = (TextView) findViewById(R.id.ClientsPhoneNumber_ProfilePage);
        TextView ClientsMail = (TextView) findViewById(R.id.ClientsMail_ProfilePage);

        Log.d("currentClientTest", "Its OnCreate and its current user:" + currentClient.toString());
     //   ImageView imageView = (ImageView) findViewById(R.id.accountPhoto);

      //  Picasso.get()
         //       .load(R.drawable.the_red_or_white_cat_i_on_white_studio)
           //     .resize(100, 150)
             //   .into(imageView);

        ClientsName.setText(currentClient.getName());
        ClientsPhoneNumber.setText(currentClient.getPhoneNumber());
        ClientsMail.setText(currentClient.getMail());
    }
}