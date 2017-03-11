package com.offcasoftware.shop2.view;

import com.offcasoftware.shop2.AndroidApplication;
import com.offcasoftware.shop2.R;
import com.offcasoftware.shop2.database.Database;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author maciej.pachciarek on 2017-02-20.
 */

public class AddProductActivity extends AppCompatActivity {

    @BindView(R.id.product_name)
    EditText mProductName;

    @BindView(R.id.product_price)
    EditText mProductPrice;

    private Database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);

        mDatabase = AndroidApplication.getDatabase();
    }

    @OnClick(R.id.button_add_product)
    public void onAddProductClicked(View view) {
        String name = mProductName.getText().toString().trim();
        String price = mProductPrice.getText().toString().trim();
        int priceInt = Integer.parseInt(price);

        mDatabase.saveProduct(name, priceInt);

        onBackPressed();
    }

    @OnClick(R.id.product_available)
    public void onDataPickerClick(View view) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this, dataPickerListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private final DatePickerDialog.OnDateSetListener dataPickerListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year,
                        int month, int dayOfMonth) {
                }
            };
}
