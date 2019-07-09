package com.vogella.android.entregan1;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Activity_Ranking extends AppCompatActivity {


    private Puntajes [] rank = new Puntajes[10];
    ShareActionProvider shareActionProvider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__ranking);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        try {
            infoaJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        generartabla();

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menuactionbar, menu);

        // Obtener el item de menu que se desea configurar
        MenuItem item = menu.findItem(R.id.action_share);

        // Obtener el ShareActionProvider asociado al item de menu
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent i = new Intent();
        i.setType("tex/plain");
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, leerpreferencias());

        shareActionProvider.setShareIntent(i);


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Volver")
                        .setMessage("Desea volver al menu pricipal?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }





    private String leerpreferencias(){
        SharedPreferences preferencias = getSharedPreferences("ID",MODE_PRIVATE);
        String vec = preferencias.getString("to","vacio");
        System.out.println(" la informacion leida en preferencias: "+vec);
        return vec;

    }

    public void infoaJson() throws JSONException {
        String info = leerpreferencias();
        if(!info.equals("vacio") ){
            JSONObject TABLA = new JSONObject(info);
            JSONArray integrantes = TABLA.getJSONArray("jugadores");
            for(int i=0;i<rank.length;i++){
                JSONObject integrante = integrantes.getJSONObject(i);
                Puntajes jugador = new Puntajes(integrante.getString("nombre"),integrante.getInt("puntaje"));
                rank[i]= jugador;
                System.out.print("el nombre del integrante numero "+i+" es  "+rank[i].getUsuario());
                System.out.println(" y su puntaje es:  "+rank[i].getPuntaje());
            }

        }


        }

        public void generartabla(){
            int pun= rank[0].getPuntaje();
            TextView pos1 = (TextView) findViewById(R.id.activity_ranking_tv1);
            pos1.setText(rank[0].getUsuario());

            TextView pos2 = (TextView) findViewById(R.id.activity_ranking_tv2);
            pos2.setText(Integer.toString(pun));

            TextView pos3 = (TextView) findViewById(R.id.activity_ranking_tv3);
            pos3.setText(rank[1].getUsuario());

            TextView pos4 = (TextView) findViewById(R.id.activity_ranking_tv4);
            pos4.setText(Integer.toString(rank[1].getPuntaje()));

            TextView pos5 = (TextView) findViewById(R.id.activity_ranking_tv5);
            pos5.setText(rank[2].getUsuario());

            TextView pos6= (TextView) findViewById(R.id.activity_ranking_tv6);
            pos6.setText(Integer.toString(rank[2].getPuntaje()));

            TextView pos7 = (TextView) findViewById(R.id.activity_ranking_tv7);
            pos7.setText(rank[3].getUsuario());

            TextView pos8 = (TextView) findViewById(R.id.activity_ranking_tv8);
            pos8.setText(Integer.toString(rank[3].getPuntaje()));

            TextView pos9 = (TextView) findViewById(R.id.activity_ranking_tv9);
            pos9.setText(rank[4].getUsuario());

            TextView pos10 = (TextView) findViewById(R.id.activity_ranking_tv10);
            pos10.setText(Integer.toString(rank[4].getPuntaje()));

            TextView pos11= (TextView) findViewById(R.id.activity_ranking_tv11);
            pos11.setText(rank[5].getUsuario());

            TextView pos12= (TextView) findViewById(R.id.activity_ranking_tv12);
            pos12.setText(Integer.toString(rank[5].getPuntaje()));

            TextView pos13= (TextView) findViewById(R.id.activity_ranking_tv13);
            pos13.setText(rank[6].getUsuario());

            TextView pos14= (TextView) findViewById(R.id.activity_ranking_tv14);
            pos14.setText(Integer.toString(rank[6].getPuntaje()));

            TextView pos15= (TextView) findViewById(R.id.activity_ranking_tv15);
            pos15.setText(rank[7].getUsuario());

            TextView pos16= (TextView) findViewById(R.id.activity_ranking_tv16);
            pos16.setText(Integer.toString(rank[7].getPuntaje()));

            TextView pos17= (TextView) findViewById(R.id.activity_ranking_tv17);
            pos17.setText(rank[8].getUsuario());

            TextView pos18= (TextView) findViewById(R.id.activity_ranking_tv18);
            pos18.setText(Integer.toString(rank[8].getPuntaje()));

            TextView pos19 = (TextView) findViewById(R.id.activity_ranking_tv19);
            pos19.setText(rank[9].getUsuario());

            TextView pos20 = (TextView) findViewById(R.id.activity_ranking_tv20);
            pos20.setText(Integer.toString(rank[9].getPuntaje()));
    }


    }



