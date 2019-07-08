package com.vogella.android.entregan1;

public class RegistroViews {

    private int i_imagen;
    private int i_hueco;
    private  int canthuecos;

    public RegistroViews() {
    }

    public RegistroViews(int i_imagen, int i_hueco) {
        this.i_imagen = i_imagen;
        this.i_hueco = i_hueco;
    }

    public RegistroViews(int i_imagen, int i_hueco, int canthuecos) {
        this.i_imagen = i_imagen;
        this.i_hueco = i_hueco;
        this.canthuecos = canthuecos;
    }


    public int getI_imagen() {
        return i_imagen;
    }

    public int getI_hueco() {
        return i_hueco;
    }

    public int getCanthuecos() {
        return canthuecos;
    }
}
