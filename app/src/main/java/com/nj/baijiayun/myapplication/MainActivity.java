package com.nj.baijiayun.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import java.text.MessageFormat;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tv)).setText(MessageFormat.format("{0}\n{1}\n{2}", BuildConfig.APPLICATION_ID
                , BuildConfig.BASE_H5_URL
                , BuildConfig.BASE_URL
                ,getPackageName()

        ));
    }
}
