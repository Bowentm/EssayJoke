package com.example.essayjoke;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baselibrary.CheckNet;
import com.example.baselibrary.OnClick;
import com.example.baselibrary.ViewById;
import com.example.baselibrary.ViewUtils;

public class MainActivity extends AppCompatActivity  {

    @ViewById(R.id.tv_test)
    private TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        mTvTest.setText("成功了");
    }

    @OnClick(R.id.tv_test)
    @CheckNet
    private  void onClick(View view){
        Toast.makeText(this,"点击了",Toast.LENGTH_LONG).show();
    }


}
