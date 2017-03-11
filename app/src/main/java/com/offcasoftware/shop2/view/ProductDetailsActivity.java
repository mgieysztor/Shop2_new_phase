package com.offcasoftware.shop2.view;

import com.offcasoftware.shop2.R;
import com.offcasoftware.shop2.model.Product;
import com.offcasoftware.shop2.repository.ProductRepository;
import com.offcasoftware.shop2.repository.ProductRepositoryInterface;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author maciej.pachciarek on 2017-02-18.
 */

public class ProductDetailsActivity extends AppCompatActivity {

    public static final String INTENT_PRODUCT_ID =
            ProductDetailsActivity.class.getSimpleName() + "productId";

    @BindView(R.id.product_image)
    ImageView mProductImage;

    @BindView(R.id.product_name)
    TextView mProductName;

    @BindView(R.id.product_price)
    TextView mProductPrice;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ProductRepositoryInterface mProductRepository
            = ProductRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);

        Context context = this;

        Bundle bundle = getIntent().getExtras();
        int productId = bundle.getInt(INTENT_PRODUCT_ID);
        Log.d("Shop", "Product id: " + productId);

        Product product = mProductRepository.getProduct(productId);

        //setupToolbar();
        displayData(product);
    }

    private void displayData(final Product product) {
        int drawableResourceId = this.getResources()
                .getIdentifier(product.getImageResId(), "drawable",
                        getPackageName());
        mProductImage.setImageResource(drawableResourceId);
        mProductName.setText(product.getName());
        mProductPrice.setText(String.valueOf(product.getPrice()));
    }

}
