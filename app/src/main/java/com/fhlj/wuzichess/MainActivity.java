package com.fhlj.wuzichess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fhlj.manmachine.ManMachineActivity;
import com.fhlj.manman.ManManActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startManMan(View view){
        startActivity(new Intent(this, ManManActivity.class));
    }

    public void startManMachine(View view){
        startActivity(new Intent(this, ManMachineActivity.class));
    }

}
