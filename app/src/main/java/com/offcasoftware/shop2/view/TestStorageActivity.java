package com.offcasoftware.shop2.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.offcasoftware.shop2.R;
import com.offcasoftware.shop2.model.Product;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestStorageActivity extends AppCompatActivity {

    @BindView(R.id.textFilesDir)
    TextView mFilesDirTextView;

    @BindView(R.id.textCacheDir)
    TextView mCacheDirTextView;

    @BindView(R.id.textStringDir)
    TextView mStringDirTextView;

    @BindView(R.id.imageLoaded)
    ImageView mImageView;

    @BindView(R.id.textProduct)
    TextView mProductTextView;

    @BindView(R.id.editSharedPreferences)
    EditText mSharedPreferencesEditText;

    @BindView(R.id.buttonSaveSharedPreferences)
    Button mSaveSharedPreferencesButton;

    @BindView(R.id.buttonShowSharedPreferences)
    Button mShowSharedPreferences;

    String filename = "myfile";
    String bitmapFilename = "mybitmapfile";
    String string = "Hello world!";
    String fileAsString = null;
    String objectFilename = "objectfile";

    private final SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            if czy zmienia się klucz
            key = getString(R.string.get_high_score);
            Toast.makeText(TestStorageActivity.this, sharedPreferences.getString(key,null),Toast.LENGTH_LONG).show();
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_storage);
        ButterKnife.bind(this);
        saveString();
        openString();
        saveObject();

        mFilesDirTextView.setText("Internal storage: " + getFilesDir().getAbsolutePath());
        mCacheDirTextView.setText("Cache storage:    " + getCacheDir().getAbsolutePath());
        mStringDirTextView.setText("String content:  " + fileAsString);

        mImageView.setImageBitmap(bitmapStore());

        mProductTextView.setText(readObject());

        mSaveSharedPreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToSharedPreferences(mSharedPreferencesEditText.getText().toString());
            }
        });

        mShowSharedPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestStorageActivity.this, showSharedPreferences(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void saveString() {
        Product product = new Product(101, "domeczek", 125000, "dom1");
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openString() {
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(filename);
            BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            fileAsString = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap bitmapStore() {
        FileOutputStream outputBitmapStream = null;

        try {
            outputBitmapStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputBitmapStream.write(string.getBytes());
            outputBitmapStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dom1);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputBitmapStream);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        try {
            bitmap = BitmapFactory.decodeStream(openFileInput(bitmapFilename), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void saveObject() {
        Product product = new Product(101, "domeczek", 125000, "dom1");
        ObjectOutputStream objectOutputStream;

        try {
            objectOutputStream =
                    new ObjectOutputStream(openFileOutput(objectFilename, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(product);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readObject() {

        Product productLoaded = null;

        FileInputStream fis;
        try {
            fis = openFileInput(objectFilename);
            ObjectInputStream is = new ObjectInputStream(fis);
            productLoaded = (Product) is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e ){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return productLoaded.toString();
    }

    private void saveToSharedPreferences(String newHighScore) {
        SharedPreferences sharedPreferences =
                getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.get_high_score), newHighScore);
        editor.commit();
    }

    private String showSharedPreferences() {
        SharedPreferences sharedPreferences =
                getPreferences(Context.MODE_PRIVATE);

        return sharedPreferences.getString(getString(R.string.get_high_score), "brak");

    }
    public void registerOnSharedPreferencesChangeListener (){
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(mListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.unregisterOnSharedPreferenceChangeListener(mListener);
    }
}
