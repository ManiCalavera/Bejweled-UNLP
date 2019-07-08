package com.vogella.android.entregan1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void arrancarjuego(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void showmeRank(View v){
        Intent i = new Intent(this,Activity_Ranking.class);
        startActivity(i);
    }
}
