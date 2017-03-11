package com.offcasoftware.shop2.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.offcasoftware.shop2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RENT on 2017-03-11.
 */

public class Fragment1 extends Fragment {

    private static final String NAME_KEY = Fragment1.class.getCanonicalName() + "NAME_KEY";

    @BindView(R.id.textview)
    TextView mNameTextView;

    public static Fragment1 getInstance(String name) {
        final Fragment1 fragment1 = new Fragment1();
        Bundle bundle = new Bundle();
        bundle.putString(NAME_KEY, name);

        fragment1.setArguments(bundle);

        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_1, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(NAME_KEY)) {
            final String name = bundle.getString(NAME_KEY);
            mNameTextView.setText(name);
        }
    }

    public void duplicate (){
        final String text = mNameTextView.getText().toString() + mNameTextView.getText().toString();
    }
}
