package com.tstasks.sanchellios.navicostores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Store> mStores = new ArrayList<>();
        mStores.add(new Store(0, "Babooshka", "xxx", "02", null));
        mStores.add(new Store(1, "Liubava", "yyy", "03", null));
        Bundle args = new Bundle();
        args.putParcelableArrayList("STORES", mStores);

    }
}
