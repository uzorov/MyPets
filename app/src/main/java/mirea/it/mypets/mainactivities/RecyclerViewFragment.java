package mirea.it.mypets.mainactivities;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import mirea.it.mypets.R;
import mirea.it.mypets.registrationandlogin.Regustration;
import mirea.it.mypets.registrationandlogin.helloPage;
import mirea.it.mypets.usefull.PreferenceClass;

/**
 * Фрагмент, который замещает основной экран и наполняет его элементами ImageView
 * Данный класс должен выполнять следующие функции:
 * Узнать количество файлов в облачном хранилище
 * Загрузить, имеющиеся в облачном хранилище файлы в галерею
 * При добавлении новых фото создать уникальное имя на основе текущего количества фотографий в хранилище
 * Обновить страницу, отобразив только что добавленное фото
 * <p>
 * Хранить количество загруженных фотографий в локальном файле
 * <p>
 * План на 4 июня:
 * Хранить данные о количестве фотографий в хранилище локально
 * Изменить ImageButton на ImageView
 * Сохранить сделанное фото в локальное хранилище в этом Activity,
 * увеличить счётчик фото, при возврате из камеры и обновлении экрана фото автоматически добавится
 * в галерею
 */
public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private int countofimages;

    FirebaseStorage storage;
    StorageReference storageRef;
    StorageReference jackRef;

    PreferenceClass preferenceClass;
    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected Drawable[] mDataset;
    private DatabaseReference mDatabase;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceClass = new PreferenceClass(getContext());
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child(currentUser.getUid() + "/");
        jackRef = storage.getReference().child(currentUser.getUid() + "/" + "pet0");
        mDatabase = FirebaseDatabase.getInstance().getReference();


        countofimages = preferenceClass.getCount();


        initDataset();

        mAdapter = new CustomAdapter(mDataset);



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] Data = baos.toByteArray();

            Toast.makeText(getContext(), "Фото загружается в облако...", Toast.LENGTH_SHORT).show();

            Bitmap bitmap = BitmapFactory.decodeByteArray(Data, 0, Data.length);
            // Use the bytes to display the image
            new ImageSaver(getContext()).
                    setFileName(getUniceName()).
                    setDirectoryName("images").
                    save(bitmap);

            preferenceClass.setCount(countofimages+1);
            Log.d("count", "OnActivityResult_countofimages: " + Integer.toString(countofimages));


            UploadTask uploadTask = storageRef.child(getUniceName()).putBytes(Data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("Photo", "Фото НЕ сохранено");
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Intent intent2 = new Intent(getContext(), photoStorage.class);
                    startActivity(intent2);

                    Log.d("Photo", "Фото сохранено");
                }
            });


        }


    }

    private String getUniceName() {
        String name = "pet";
        countofimages = preferenceClass.getCount();

        Log.d("count", "GetUniceName_countofimages: " + Integer.toString(countofimages));
        name += Integer.toString(countofimages);
        return name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycle_view_frag, container, false);
        rootView.setTag(TAG);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);


        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }

    private void initDataset() {

        Log.d("count", "InitDatasetAgain:" + Integer.toString(countofimages));
        mDataset = new Drawable[countofimages+1];
        mDataset[0] = getResources().getDrawable(R.drawable.plus);


        for (int i = 1; i < countofimages; i++) {
            String photoName = "pet" + Integer.toString(i );
            Bitmap bitmap = new ImageSaver(getContext()).
                    setFileName(photoName).
                    setDirectoryName("images").
                    load();

            Drawable d = new BitmapDrawable(getResources(), bitmap);

            mDataset[i] = d;
        }


    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private Drawable[] mDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView button;

            public ViewHolder(View v) {
                super(v);
                button = (ImageView) v.findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (getAdapterPosition() == 0)
                            dispatchTakePictureIntent();
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    }
                });

            }

            public ImageView getButton() {
                return button;
            }
        }

        public CustomAdapter(Drawable[] dataSet) {
            mDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.text_row_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            viewHolder.getButton().setImageDrawable(mDataSet[position]);
        }

        @Override
        public int getItemCount() {
            return mDataSet.length;
        }


        private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                // display error state to the user
            }
        }

    }

}