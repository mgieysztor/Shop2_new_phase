package com.offcasoftware.shop2.database;

import com.offcasoftware.shop2.model.Product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maciej.pachciarek on 2017-03-06.
 */

public class DatabaseImpl extends SQLiteOpenHelper implements Database {

    private final static String NAME = "database.db";
    private final static int VERSION = 5;

    private static final String DB_CREATE_TODO_TABLE =
            "CREATE TABLE products(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL UNIQUE," +
                    "price INTEGER DEFAULT 0," +
                    "imageName TEXT DEFAULT dom2" +
                    ");";

    private static final String ADD_COLUMN =
            "ALTER TABLE products "
                    + "ADD test TEXT";

    private static final String DROP_TODO_TABLE =
            "DROP TABLE IF EXISTS products";

    public DatabaseImpl(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(DB_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        List<Product> products = getProducts(db);
        db.execSQL(DROP_TODO_TABLE);

        onCreate(db);
        saveProducts(products, db);
    }

    @Override
    public List<Product> getProducts() {
        SQLiteDatabase db = getReadableDatabase();
        return getProducts(db);
    }

    private List<Product> getProducts(SQLiteDatabase db) {
        List<Product> products = new ArrayList<>();
        Cursor cursor = db.query("products", null, null, null,
                null, null, null);
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(0);
            int nameColumnIndex = cursor.getColumnIndex("name");
            String name = cursor.getString(nameColumnIndex);
            int price = cursor.getInt(2);
            String imageName = cursor.getString(3);
            Product product = new Product(id, name, price, imageName);
            products.add(product);
        } while (cursor.moveToNext());
        cursor.close();
        return products;
    }

    @Override
    public void saveProducts(final List<Product> products) {
        SQLiteDatabase db = getWritableDatabase();
        saveProducts(products, db);
    }

    private void saveProducts(List<Product> products, SQLiteDatabase db) {
        for (Product product : products) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", product.getName());
            contentValues.put("price", product.getPrice());
            contentValues.put("imageName", product.getImageResId());
            long id = db.insert("products", null, contentValues);
            Log.i("database", "id inserted: " + id);
        }
    }

    @Override
    public void saveProduct(final String name, final int price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("price", price);
        long id = db.insert("products", null, contentValues);
    }

    @Override
    public Product getProduct(final int productId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("products", null, "id = ?",
                new String[] {String.valueOf(productId)},
                null, null, null);
        cursor.moveToFirst();

        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        int price = cursor.getInt(2);
        String imageName = cursor.getString(3);

        Product product = new Product(id, name, price, imageName);

        cursor.close();
        return product;
    }
}
