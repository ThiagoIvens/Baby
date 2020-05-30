package com.thiagoivens.babe.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AlertDialog;

import com.thiagoivens.babe.CadNewEvento;
import com.thiagoivens.babe.dominio.entidades.Rotina;

import java.util.ArrayList;
import java.util.List;

public class RotinaRepositorio {

    private static String ultimo;
    SQLiteDatabase conexao;

    public RotinaRepositorio(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void insert(Rotina rotina){
        ContentValues contentValues = new ContentValues();
        contentValues.put("DIA", String.valueOf(rotina.dia));
        contentValues.put("HORA", String.valueOf(rotina.hora));
        contentValues.put("TIPO", rotina.tipo);

        setUltimo(rotina.tipo);
        conexao.insertOrThrow("ROTINA", null, contentValues);
    }

    public void delete(int id){
        String[] parametros =  new String[1];
        parametros[0] = String.valueOf(id);
        conexao.delete("ROTINA", "ID = ?", parametros);
    }

    public List<Rotina> getAll(){
        List<Rotina> rotina = new ArrayList<Rotina>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, DIA, HORA, TIPO ");
        sql.append(" FROM ROTINA ");
        sql.append(" ORDER BY ID DESC ");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if( resultado.getCount() > 0){
            resultado.moveToFirst();
            do {
                Rotina rot = new Rotina();
                rot.id = resultado.getInt( resultado.getColumnIndexOrThrow("ID"));
                rot.dia = resultado.getString(resultado.getColumnIndexOrThrow("DIA"));
                rot.hora = resultado.getString(resultado.getColumnIndexOrThrow("HORA"));
                rot.tipo = resultado.getString(resultado.getColumnIndexOrThrow("TIPO"));

                rotina.add(rot);
            }while(resultado.moveToNext());
        }
        RotinaRepositorio.setUltimo(rotina.get(0).tipo);
        return rotina;
    }

    public List<Rotina> getType(String type){
        List<Rotina> rotina = new ArrayList<Rotina>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, DIA, HORA, TIPO ");
        sql.append(" FROM ROTINA ");
        sql.append(" WHERE TIPO IN ('"+type+"')");
        sql.append(" ORDER BY ID DESC ");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if( resultado.getCount() > 0){
            resultado.moveToFirst();
            do {
                Rotina rot = new Rotina();
                rot.id = resultado.getInt( resultado.getColumnIndexOrThrow("ID"));
                rot.dia = resultado.getString(resultado.getColumnIndexOrThrow("DIA"));
                rot.hora = resultado.getString(resultado.getColumnIndexOrThrow("HORA"));
                rot.tipo = resultado.getString(resultado.getColumnIndexOrThrow("TIPO"));

                rotina.add(rot);
            }while(resultado.moveToNext());
        }
        return rotina;
    }

    public List<Rotina> getResumoSono(){
        List<Rotina> rotina = new ArrayList<Rotina>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, DIA, HORA, TIPO ");
        sql.append(" FROM ROTINA ");
        sql.append(" WHERE TIPO IN ('Dormiu', 'Acordou') ");
        sql.append(" ORDER BY ID DESC ");

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if( resultado.getCount() > 0){
            resultado.moveToFirst();
            do {
                Rotina rot = new Rotina();
                rot.id = resultado.getInt( resultado.getColumnIndexOrThrow("ID"));
                rot.dia = resultado.getString(resultado.getColumnIndexOrThrow("DIA"));
                rot.hora = resultado.getString(resultado.getColumnIndexOrThrow("HORA"));
                rot.tipo = resultado.getString(resultado.getColumnIndexOrThrow("TIPO"));

                rotina.add(rot);
            }while(resultado.moveToNext());
        }
        return rotina;
    }

    public static String getUltimo() {
        return ultimo;
    }

    public static void setUltimo(String ultimo) {
        RotinaRepositorio.ultimo = ultimo;
    }
}
