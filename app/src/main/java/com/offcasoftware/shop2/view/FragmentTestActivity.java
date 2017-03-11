package com.offcasoftware.shop2.view;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.offcasoftware.shop2.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentTestActivity extends AppCompatActivity {

    Fragment1 fragment1 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        ButterKnife.bind(this);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.activity_fragment_test, Fragment1.getInstance("MIKE"), Fragment1.class.getCanonicalName())
//                .commit();

        fragment1 = (Fragment1) getSupportFragmentManager().findFragmentById(R.id.fragment_1);
//        fragment1 = (Fragment1) getSupportFragmentManager().findFragmentByTag(Fragment1.class.getCanonicalName());
    }

    @OnClick(R.id.duplicate)
    public void onDuplicateButtonClicked (View view){
        fragment1.duplicate();
    }
}
