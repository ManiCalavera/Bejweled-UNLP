package com.vogella.android.entregan1;

public class Puntajes {
    private String usuario;
    private int puntaje;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public Puntajes(String usuario, int puntaje) {
        this.usuario = usuario;
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return
                usuario + " : " + puntaje+" \n";
    }
}
