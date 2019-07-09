package com.vogella.android.entregan1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GridLayout gridLayout;

    private int[] images = {R.drawable.blue,R.drawable.green, R.drawable.orange, R.drawable.purple, R.drawable.red, R.drawable.yellow};
    private int clicks = 0;
    private int posicionview1, posicionview2;
    private ImageView imageview1, imageview2;
    private TextView textview_puntaje;
    private int i1, i2, j1, j2;
    private int[][] matriz = new int[8][8];
    //private int [][] matrizvertical = new int[8][8];
    private int idimagenclick1 = -2;
    private int idimagenclick2;
    private int cant = -25;
    private int[] aBorrar = new int[64];
    private int sinimagen = -1;
    private int array = 0;
    private int contadorhandler = 0;
    private boolean barridohorizontal = true;
    private boolean barridovertical = true;
    public RegistroViews parametroscontarhuecos = new RegistroViews();
    public RegistroViews parametrosbajarimagen = new RegistroViews();
    private int puntaje = 0;
    private boolean haycoincidencia=false;
    private boolean haymovimient= false;
    private boolean clickisenabled =true;
    private Button refrescar;
    private Button terminar;
    private JSONObject Tabla = new JSONObject();
    private Puntajes  []rank = new Puntajes [10];
    ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        try {
            Inicializar();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadviews();

        loadimages();

        haycoincidencia=haycoincidencia();
        limpiartablerodecoincidencias();





    }










    private String leerpreferencias(){
        SharedPreferences preferencias = getSharedPreferences("ID",MODE_PRIVATE);
        String vec = preferencias.getString("to","vacio");
        System.out.println(" la informacion leida en preferencias: "+vec);
        return vec;

    }




    private void guardarpreferencias(){
        SharedPreferences preferencias = getSharedPreferences("ID",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("to",Tabla.toString());
        System.out.println("la informacion guardada fue  "+Tabla.toString());
        editor.commit();

        String vec = preferencias.getString("to","vacio1");

        System.out.println("La informacion fue guardada fue :  "+vec);

    }





    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
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
    }




    private void unsetclickeable() {
        clickisenabled= false;
        for (int i = 0; i < 64; i++) {
            gridLayout.getChildAt(i).setOnClickListener(null);
            gridLayout.getChildAt(i).setClickable(false);
            gridLayout.getChildAt(i).setEnabled(false);
        }
        terminar.setOnClickListener(null);
        terminar.setClickable(false);
        terminar.setEnabled(false);
        terminar.setAlpha(0.5f);
    }

    private void setclickeable() {

        for (int i = 0; i < 64; i++) {

            gridLayout.getChildAt(i).setOnClickListener(this);
            gridLayout.getChildAt(i).setClickable(true);
            gridLayout.getChildAt(i).setEnabled(true);
        }
        clickisenabled = true;
        terminar.setAlpha(1f);
        terminar.setOnClickListener(this);
        terminar.setClickable(true);
        terminar.setEnabled(true);
    }

    private void Inicializar() throws JSONException {
        for (int i = 0; i < 64; i++) {
            aBorrar[i] = -100;
        }
        String vec = leerpreferencias();
        if (vec.equals("vacio")) {

            Puntajes r0 = new Puntajes("nombre", 0);

            Puntajes r1 = new Puntajes("nombre", 0);

            Puntajes r2 = new Puntajes("nombre", 0);

            Puntajes r3 = new Puntajes("nombre", 0);

            Puntajes r4 = new Puntajes("nombre", 0);

            Puntajes r5 = new Puntajes("nombre", 0);

            Puntajes r6 = new Puntajes("nombre", 0);

            Puntajes r7 = new Puntajes("nombre", 0);

            Puntajes r8 = new Puntajes("nombre", 0);

            Puntajes r9 = new Puntajes("nombre", 0);

            Puntajes[] r = {r0, r1, r2, r3, r4, r5, r6, r7, r8, r9};
            rank = r;
            for (int i = 0; i < 10; i++) {
                System.out.println("jugador " + i);
                System.out.println("usuario:  " + rank[i].getUsuario());
                System.out.println("Puntaje:  " + rank[i].getPuntaje());
            }
        }else{
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
        }

    private boolean barridohorizontal() {
        mostrarMatriz();
        mostrarvectoraBorrar();
        boolean cumple = false;
        int previo = -9;
        int actual = -10;
        int coincidecias = 1;
        int j;

        for (int i = 0; i < 8; i++) {

            for (j = 0; j < 8; j++) {

                actual = matriz[i][j];


                if ((actual != -1) && (actual == previo)) {
                    coincidecias++;
                    previo = actual;
                } else {
                    if (coincidecias > 2) {
                        cumple = true;
                        for (int posicion = j - coincidecias; posicion < j; posicion++) {
                            aBorrar[array] = (i * 8) + posicion;
                            array++;
                        }


                    }

                    previo = actual;

                    coincidecias = 1;


                }


            }

            if (coincidecias > 2) {
                cumple = true;
                for (int posicion = j - coincidecias; posicion < j; posicion++) {
                    aBorrar[array] = (i * 8) + posicion;
                    array++;
                }

            }

            coincidecias = 1;
            previo = -9;


        }

        System.out.println("el barrido horizontal es " + cumple);
        return cumple;
    }


    private boolean barridovertical() {

        boolean cumple = false;
        mostrarMatriz();
        mostrarvectoraBorrar();

        int previo = -9;
        int actual = -10;
        int coincidecias = 1;
        int i;
        for (int j = 0; j < 8; j++) {

            for (i = 0; i < 8; i++) {

                actual = matriz[i][j];


                if ((actual != -1) && (actual == previo)) {

                    coincidecias++;
                    previo = actual;
                    System.out.println(coincidecias + " el actual es " + actual + " el previo es " + previo);

                } else {
                    if (coincidecias > 2) {
                        System.out.println("hay más de dos coincidencias y son " + coincidecias);
                        cumple = true;
                        for (int posicion = i - coincidecias; posicion < i; posicion++) {
                            aBorrar[array] = (posicion * 8) + j;
                            array++;
                        }
                    }


                    previo = actual;

                    coincidecias = 1;


                }


            }

            if (coincidecias > 2) {
                System.out.println("hay más de dos coincidencias y son " + coincidecias);
                cumple = true;
                for (int posicion = i - coincidecias; posicion < i; posicion++) {
                    aBorrar[array] = (posicion * 8) + j;
                    array++;
                }

            }


            coincidecias = 1;
            previo = -9;


        }


        System.out.println("el barrido vertical es" + cumple);
        mostrarMatriz();
        mostrarvectoraBorrar();
        return cumple;
    }


    private void loadviews() {
        gridLayout = findViewById(R.id.Father);
        refrescar = findViewById(R.id.mainactivity_btn1);
        refrescar.setAlpha(0.5f);
        terminar = findViewById(R.id.mainactivity_btn2);
        terminar.setAlpha(0.5f);
        textview_puntaje = findViewById(R.id.numero);
        textview_puntaje.setText(String.valueOf(puntaje));
    }

    private void loadimages() {
        //int contador = -1; //esta linea de código es para generar una pantalla sin movimientos posibles

        int randomimage;
        ImageView imageview;

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {

                /*
                //***********estas lineas de código son para generar una pantalla sin movimientos posibles***

                if (contador < 5){
                    contador++;
                }
                else{
                    contador = 0;
                }

                randomimage = contador;
                    */



                randomimage = (int) (Math.random() * 6);
                imageview = (ImageView) gridLayout.getChildAt((i * 8) + j);
                imageview.setImageResource(images[randomimage]);
                imageview.setTag(R.id.j, j);
                imageview.setTag(R.id.i, i);
                imageview.setTag(R.id.imagen, randomimage);
                matriz[i][j] = (randomimage);
            }
        }
    }

    @Override
    public void onClick(View v) {



        if (terminar.getId() != v.getId()) {
            clicks++;

            if ((clicks == 1) && (clickisenabled)) {
                idimagenclick1 = (int) v.getTag(R.id.imagen);
                System.out.println("el click 1 contiene la imagen: " + idimagenclick1);
                imageview1 = (ImageView) v;
                i1 = (int) v.getTag(R.id.i);
                j1 = (int) v.getTag(R.id.j);
                imageview1.setBackgroundColor(Color.RED);

            }

            if (clicks == 2) {

                unsetclickeable();
                idimagenclick2 = (int) v.getTag(R.id.imagen);
                System.out.println("el click 1 contiene la imagen: " + idimagenclick2);
                i2 = (int) v.getTag(R.id.i);
                j2 = (int) v.getTag(R.id.j);
                imageview1.setBackgroundColor(Color.TRANSPARENT);


                //si las imágenes son limítrofes

                if ((((j2 - j1 == 1) | (j2 - j1 == -1) && (i2 - i1 == 0))) | (((i1 - i2 == 1) | (i1 - i2 == -1)) && (j1 - j2 == 0))) {

                    imageview2 = (ImageView) v;


                    matriz[i1][j1] = idimagenclick2;
                    matriz[i2][j2] = idimagenclick1;
                    System.out.println("Estoy adentro del if limitrofes y los clicks son " + idimagenclick1 + " y " + idimagenclick2);


                    //cada barrido me devuelve un boolean que indica si encontró coincidencias
                    haycoincidencia = haycoincidencia();
                    if (!haycoincidencia) {   //si el movimiento que se hizo está en rango pero no provoca coincidencias

                        matriz[i1][j1] = idimagenclick1;
                        matriz[i2][j2] = idimagenclick2;
                        System.out.println("estoy adentro del if barridos son verdaderos y los clicks son " + idimagenclick1 + " y " + idimagenclick2);

                        Toast.makeText(MainActivity.this, "Ups! movimiento no válido", Toast.LENGTH_SHORT).show();
                        setclickeable();

                    } else { //si el movimiento está en rango y provoca coincidencias, se hace el intercambio de imágenes

                        //Hace efectivo el cambio de imágenes
                        imageview1.setImageResource(images[idimagenclick2]);
                        imageview1.setTag(R.id.imagen, idimagenclick2);
                        imageview2.setImageResource(images[idimagenclick1]);
                        imageview2.setTag(R.id.imagen, idimagenclick1);
                        System.out.println("cambie las imagenes y los clicks son + " + idimagenclick1 + " y " + idimagenclick2);

                        limpiartablerodecoincidencias();


                    }
                } else { //el movimiento que se hizo no está en rango
                    Toast.makeText(MainActivity.this, "Ups! movimiento no válido", Toast.LENGTH_SHORT).show();
                    setclickeable();

                }

                clicks = 0;

            }
        }
        else {
            try {
                evaluarpuntaje();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void mostrarMatriz() {
        System.out.println("enter");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print("       " + matriz[i][j] + "       ");
            }
            System.out.println();
        }
        System.out.println("enter");

    }

    private void mostrarvectoraBorrar() {
        System.out.println("ESTE ES EL VECTOR CON POSCICIONES A BORRAR");
        for (int i = 0; i < 64; i++) {

            System.out.println("   " + aBorrar[i]);
        }
    }


    //necesitamos la pos en donde borrar , cada vez que se la llama , la pos se vuelve 0;
    private void turninvisible() {
        ImageView imagen;

        int i;
        int j;

        //Posiciono la variable acumuladora de invisibles en la última posición usada
        if (array > 0) {
            array--;
        }

        if ((array >= 0) && (aBorrar[array] != -100)) {

            puntaje += array+1;
            textview_puntaje.setText(String.valueOf(puntaje));

            for (int k = array; k >= 0; k--) {
                imagen = (ImageView) gridLayout.getChildAt(aBorrar[k]);
                imagen.setAlpha(0.5f);
                i = (int) imagen.getTag(R.id.i);
                j = (int) imagen.getTag(R.id.j);

                matriz[i][j] = -1;
                aBorrar[k] = -100;


            }
        }
        array = 0;
        mostrarvectoraBorrar();
        mostrarMatriz();


    }


    private RegistroViews contarhuecos(int i_hueco, int j) {

        int cant = 0;


        //I_hueco va a almacenar la posición del primer hueco que encuentre, si lo hay.
        //Me  fijo si hay huecos
        while ((i_hueco >= 0) && (matriz[i_hueco][j] != -1)) {
            i_hueco--;
        }

        // i_imagen es la variable que va a almacenar la primera posición luego de los huecos. Puede ser una imagen o puede que se salga de la matriz
        int i_imagen = i_hueco;
        //Cuento los huecos
        if (i_hueco >= 0) {
            while ((i_imagen >= 0) && (matriz[i_imagen][j] == -1)) {
                i_imagen--;
                cant++;
            }

        }


        return new RegistroViews(i_imagen, i_hueco, cant);

    }


    //borrar
    private boolean hayImagenArriba(int i, int j) {


        return ((i >= 0) && (matriz[i][j] != -1));
    }

    private int contarimagenesentodalacolumna(int i, int j) {
        int cant = 0;
        while (i >= 0) {
            if (matriz[i][j] != -1) {
                cant++;

            }
            i--;
        }
        return cant;
    }

    private int contarimagenes(int i, int j) {
        int cant = 0;
        while ((i >= 0) && (matriz[i][j] != -1)) {
            i--;
            cant++;
        }
        return cant;
    }


    private RegistroViews bajarimagenes(int i_hueco, int i_imagen, int j, int canthuecos, int cantimagenes) {

        ImageView image;


        while ((canthuecos != 0) && (cantimagenes != 0)) {


            //Bajo la imagen y actualizo la matriz en el hueco
            matriz[i_hueco][j] = matriz[i_imagen][j];


            //Actualizo la imagen en la View y la visibilizo
            image = (ImageView) gridLayout.getChildAt((i_hueco * 8) + j);
            image.setImageResource(images[matriz[i_imagen][j]]);
            image.setTag(R.id.imagen, matriz[i_imagen][j]);
            image.setAlpha(1f);

            //Invisibilizo la view a la que le acabo de sacar la imagen
            image = (ImageView) gridLayout.getChildAt(((i_imagen) * 8) + j);
            image.setAlpha(0.5f);

            //Actualizo la matriz en la imagen
            matriz[i_imagen][j] = -1;


            //Actualizo cantidad de huecos para quizás después hacer un return y utilizarlo para rellenar con imágenes random
            canthuecos--;

            //reduzco la cantidad de imagenes para estar seguro de las imagenes que tengo que bajar
            cantimagenes--;


            //Actualizo la posición del hueco a reemplazar y la imagen a sustituir
            i_hueco--;
            i_imagen--;

        }
        mostrarMatriz();
        return new RegistroViews(i_imagen, i_hueco);


    }

    public void crearImagenes(int i, int j) {
        int randomimage;
        ImageView imageview;
        for (int k = i; k >= 0; k--) {
            if (matriz[k][j] == -1) {

                //Creo imagenes nuevas, actualizo y visibilizo
                randomimage = (int) (Math.random() * 6);
                imageview = (ImageView) gridLayout.getChildAt((k * 8) + j);
                imageview.setImageResource(images[randomimage]);
                imageview.setTag(R.id.imagen, randomimage);
                imageview.setAlpha(1f);


                //Pego la imagen nueva en matriz
                matriz[k][j] = randomimage;
            }


        }


    }

    private void bajarycrearImagenes() {

        int canthuecos;
        int cantimagenes = 0;
        int i_imagen;
        int i_hueco;

        for (int j = 7; j >= 0; j--) {

            parametroscontarhuecos = contarhuecos(7, j);
            canthuecos = parametroscontarhuecos.getCanthuecos();
            i_imagen = parametroscontarhuecos.getI_imagen();
            i_hueco = parametroscontarhuecos.getI_hueco();

            if (canthuecos != 0) {
                if (hayImagenArriba(i_imagen, j)) {
                    cantimagenes = contarimagenes(i_imagen, j);
                    parametrosbajarimagen = bajarimagenes(i_hueco, i_imagen, j, canthuecos, cantimagenes);
                    cantimagenes = contarimagenesentodalacolumna(parametrosbajarimagen.getI_imagen(), j);


                    while (cantimagenes > 0) {
                        parametroscontarhuecos = contarhuecos(parametrosbajarimagen.getI_hueco(), j);
                        canthuecos = parametroscontarhuecos.getCanthuecos();
                        i_imagen = parametroscontarhuecos.getI_imagen();
                        i_hueco = parametroscontarhuecos.getI_hueco();
                        parametrosbajarimagen = bajarimagenes(i_hueco, i_imagen, j, canthuecos, contarimagenes(i_imagen, j));
                        cantimagenes = contarimagenesentodalacolumna(parametrosbajarimagen.getI_imagen(), j);
                    }

                    crearImagenes(parametrosbajarimagen.getI_hueco(), j);


                } else {
                    crearImagenes(i_hueco, j);
                }

                //Relleno con imágenes random




            }


        }

        mostrarMatriz();


    }


    private void limpiartablerodecoincidencias() {


        final Handler handler = new Handler();








            handler.postDelayed(new Runnable() {
                @Override


                public void run() {


                        if (MainActivity.this.contadorhandler ==0) {
                            if (haycoincidencia) {


                                turninvisible();


                            }
                            contadorhandler++;
                            handler.postDelayed(this,500);

                        }

                        else {
                            if (contadorhandler ==1) {

                                MainActivity.this.bajarycrearImagenes();
                                contadorhandler++;
                                handler.postDelayed(this,500);

                            }

                            else {
                                contadorhandler = 0;
                                haycoincidencia=haycoincidencia();
                                if (haycoincidencia) {
                                    handler.postDelayed(this, 500);
                                }
                                else {

                                    if (sigojugando()) {
                                        setclickeable();
                                    }
                                }

                            }


                        }




                }
            }, 500);

        }


        private boolean haycoincidencia () {
            return ((barridohorizontal())| (barridovertical()));

        }

    private boolean barridovseguirjugando() {

        boolean cumple = false;

        int previo = -9;
        int actual = -10;
        int coincidecias = 1;
        int i;
        for (int j = 0; j < 8; j++) {

            for (i = 0; i < 8; i++) {

                actual = matriz[i][j];


                if ((actual != -1) && (actual == previo)) {

                    coincidecias++;
                    previo = actual;

                } else {
                    if (coincidecias > 2) {
                        System.out.println("hay más de dos coincidencias y son " + coincidecias);
                        System.out.println("Coincidencia en la view" + ((i * 8) + j));

                        cumple = true;
                    }


                    previo = actual;

                    coincidecias = 1;


                }


            }

            if (coincidecias > 2) {
                System.out.println("hay más de dos coincidencias y son " + coincidecias);
                System.out.println("Coincidencia en la view" + ((i * 8) + j));

                cumple = true;

            }


            coincidecias = 1;
            previo = -9;


        }


        System.out.println("el barrido vertical seguir jugando es" + cumple);
        return cumple;
    }
    private boolean barridohseguirjugando() {
        boolean cumple = false;
        int previo = -9;
        int actual = -10;
        int coincidecias = 1;
        int j;

        for (int i = 0; i < 8; i++) {

            for (j = 0; j < 8; j++) {

                actual = matriz[i][j];


                if ((actual != -1) && (actual == previo)) {
                    coincidecias++;
                    previo = actual;
                } else {
                    if (coincidecias > 2) {
                        System.out.println("Coincidencia en la view" + ((i * 8) + j));
                        cumple = true;


                    }

                    previo = actual;

                    coincidecias = 1;


                }


            }

            if (coincidecias > 2) {
                System.out.println("Coincidencia en la view" + ((i * 8) + j));

                cumple = true;

            }

            coincidecias = 1;
            previo = -9;


        }

        System.out.println("el barrido horizontal seguir jugando es " + cumple);
        return cumple;
    }




    private boolean sigojugando () {
        int posicion = 0;
        int posicion1, posicion2;
        int bucle = 0;
        int vecino = 0;
        boolean sigojugando = false;
        int multiplos;


        while ((posicion <=63)&& (!sigojugando)) {


            switch (bucle) {

                case 0:
                    vecino = posicion + 1;
                    //con este if evito que evalúe a 7 como vecino de 8
                    if (mod(vecino) == 0) {
                        vecino = -999;
                    }
                    break;

                case 1:
                    vecino = posicion - 1;
                    //con este if evito que evalúe a 7 como vecino de 8
                    if (mod(posicion) == 0){
                        vecino = -9999;
                    }
                    break;

                case 2:
                    vecino = posicion + 8;
                    break;

                case 3:
                    vecino = posicion - 8;
                    bucle = -1;
                    posicion++;
                    break;

            }

            if ((vecino >= 0) && (vecino < 64) && (posicion <= 63)) {
                int i1 = posicion / 8;
                int j1 = mod( posicion);
                int i2 = vecino /8;
                int j2 = mod (vecino);
                posicion1 = matriz[i1][j1];
                posicion2 =  matriz[i2][j2];
                matriz[i1][j1] = posicion2;
                matriz[i2][j2] = posicion1;

                sigojugando = (barridohseguirjugando()) || (barridovseguirjugando());

                //devuelvo las posiciones a su lugar original
                matriz[i1][j1] = posicion1;
                matriz[i2][j2] = posicion2;



            }



            bucle++;


        }

        System.out.println("Sigo jugando es : " + sigojugando + " gracias a la view: " + posicion);
        if (!sigojugando){
            refrescar.setAlpha(1f);
            refrescar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            Toast.makeText(this, "No hay más movimientos", Toast.LENGTH_SHORT).show();
        }
        return sigojugando;
    }




    private int mod (int x) {
        int result = x % 8;
        if (result < 0)
            result += 8;
        return result;
    }

    private void ordenar(){
        int punt;
        String nom;
        for(int i=9;i>=0;i--){
            punt= rank[i].getPuntaje();
            nom = rank[i].getUsuario();
            if(i!=0){
           if( rank[i].getPuntaje() >rank[i-1].getPuntaje()) {
               rank[i].setUsuario(rank[i-1].getUsuario());
               rank[i].setPuntaje(rank[i-1].getPuntaje());
               rank[i-1].setPuntaje(punt);
               rank[i-1].setUsuario(nom);
           }
           }
        }
    }

    private void convertirYguardarJson() throws JSONException {
        JSONArray integrantesJson = new JSONArray();
        for (int i = 0; i < rank.length; i++) {
            JSONObject integranteJson = new JSONObject();
            integranteJson.put("nombre", rank[i].getUsuario());
            System.out.println(rank[i].getUsuario());


            integranteJson.put("puntaje",rank[i].getPuntaje());

            integrantesJson.put(i, integranteJson);
        }

        Tabla.put("jugadores", integrantesJson);
        System.out.println("el json tostring  es :  "+Tabla.toString());


    }

    public void evaluarpuntaje() throws JSONException {

        System.out.println("el puntaje que vamos a evaluar es :  "+rank[9].getPuntaje());
        if(puntaje>rank[9].getPuntaje()){
            View view = ((LayoutInflater.from(MainActivity.this))).inflate(R.layout.dialog_userranking, null);
            AlertDialog.Builder alertbuilder = new AlertDialog.Builder(MainActivity.this);
            alertbuilder.setView(view);
            final EditText usuario = view.findViewById(R.id.dialog_et_usuario);

            alertbuilder.setCancelable(true)
                    .setTitle("Felicidades!")
                    .setMessage("Tu puntaje se encuentra entre los primeros 10, ingresa tu nombre para figurar en el ranking")
                    .setNegativeButton("No quiero figurar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent intent = new Intent (MainActivity.this, Activity_Ranking.class);
                            startActivity(intent);
                        }
                    })

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    rank[9].setUsuario(usuario.getText().toString());


                                    rank[9].setPuntaje(puntaje);


                                    ordenar();

                                    try {
                                        if (convertiryguardarjasonyguardarpreferencias()) {
                                            finish();
                                            Intent intent = new Intent (MainActivity.this, Activity_Ranking.class);
                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            });

            Dialog dialog = alertbuilder.create();
            dialog.show();
        }
        else{
            Toast.makeText(this,"Tu puntaje es muy bajo para participar en el Ranking", Toast.LENGTH_SHORT).show();
            finish();
        }






    }

    private boolean convertiryguardarjasonyguardarpreferencias () throws JSONException {
        convertirYguardarJson();
        guardarpreferencias();
        return true;
    }


}













