package com.thiagoivens.babe.dominio.entidades;

import java.sql.Date;
import java.sql.Time;

public class Rotina {

    public int id;
    public String dia;
    public String hora;
    public String tipo;

    public Rotina() {
    }

    public Rotina(String dia, String hora, String tipo) {
        this.dia = dia;
        this.hora = hora;
        this.tipo = tipo;
    }
}
