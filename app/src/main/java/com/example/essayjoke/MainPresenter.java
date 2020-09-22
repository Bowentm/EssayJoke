package com.example.essayjoke;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baselibrary.OnClick;
import com.example.baselibrary.ViewById;

public class MainPresenter {

    @ViewById(R.id.btn)
    private Button button;

    @OnClick(R.id.btn)
    private void onClick(View view){
        Log.d("---------","num");
    }


}
