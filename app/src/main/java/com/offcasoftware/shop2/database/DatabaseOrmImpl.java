package com.offcasoftware.shop2.database;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.offcasoftware.shop2.model.Product;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.List;

/**
 * @author maciej.pachciarek on 2017-03-07.
 */

public class DatabaseOrmImpl extends OrmLiteSqliteOpenHelper
        implements Database {

    private static final String DATABASE_NAME = "shopDatabase";
    private static final int DATABASE_VERSION = 1;

    private RuntimeExceptionDao<Product, Integer> mProductDao;

    public DatabaseOrmImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mProductDao = getRuntimeExceptionDao(Product.class);
    }

    @Override
    public void onCreate(SQLiteDatabase database,
            ConnectionSource connectionSource) {

        try {
            TableUtils.createTableIfNotExists(connectionSource,
                    Product.class);
        } catch (SQLException e) {
            Log.e("OrmLiteDatabse", "Error onCreate", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
            final int oldVersion, final int newVersion) {

    }

    @Override
    public void saveProducts(final List<Product> products) {
        for (Product product : products) {
            mProductDao.createIfNotExists(product);
        }
    }

    @Override
    public List<Product> getProducts() {
        return mProductDao.queryForAll();
    }

    @Override
    public Product getProduct(final int productId) {
        try {
            QueryBuilder<Product, Integer> query =
                    mProductDao.queryBuilder();
            Where where = query.where();
            where.eq("id", productId);

            return query.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveProduct(String name, int price) {

    }
}
