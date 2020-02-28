package com.nj.baijiayun.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nj.baijiayun.myapplication.attrtab.ITab;
import com.nj.baijiayun.myapplication.attrtab.TabIndicatorView;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    private TabIndicatorView mTabIndict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tv)).setText(MessageFormat.format("{0}\n{1}\n{2}", BuildConfig.APPLICATION_ID
                , BuildConfig.BASE_H5_URL
                , BuildConfig.BASE_URL
                , getPackageName()

        ));
        mTv = findViewById(R.id.tv);
        mTabIndict = findViewById(R.id.tabIndict);

        mTabIndict.addTabs(
                new ITab[]{new RepeatSelectTab() {
                }, new TestTab() {
                    @Override
                    public View createContentView(Context context, ViewGroup parent) {
                        View viewById = findViewById(R.id.framlayout);
                        final View contentView = super.createContentView(context, (ViewGroup) findViewById(R.id.framlayout));
                        contentView.setClickable(true);
                        viewById.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isOpen()) {
                                  getTabAttrCallBack().close();
                                }
                            }
                        });


//                        View contentView = super.createContentView(context, (ViewGroup) findViewById(R.id.framlayout));
                        return contentView;
                    }
                }}
        );
        mTabIndict.setItemDataCallBack(new TabIndicatorView.ItemDataCallBack() {
            @Override
            public void call(Object... data) {
                String result = "";
                for (int i = 0; i < data.length; i++) {
                    result += data[i] + "____";
                }

                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void testClick(View view) {
        Toast.makeText(this, "tttt", Toast.LENGTH_SHORT).show();

    }
}
