package com.groups.p2.saferider;

/**
 * Created by rodrigo on 13/10/15.
 */
public class item_objct {
    private String titulo;
    private int icono;

    public item_objct(String title, int icon){
        this.titulo=title;
        this.icono=icon;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo=titulo;
    }

    public int getIcono(){
        return icono;
    }

    public void setIcono(int icono){
        this.icono=icono;
    }
}
