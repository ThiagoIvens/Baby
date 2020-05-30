package com.thiagoivens.babe;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.thiagoivens.babe.database.DataOpenHelper;
import com.thiagoivens.babe.dominio.entidades.Rotina;
import com.thiagoivens.babe.dominio.repositorio.RotinaRepositorio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CadNewEvento extends AppCompatActivity {
    Spinner tipos;
    private SQLiteDatabase conexao;
    private DataOpenHelper dataOpenHelper;
    Button add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadnew);

        initializeViews();
        CriarConexao();
    }

    public void initializeViews(){
        tipos = findViewById(R.id.spinner);
        if(!RotinaRepositorio.getUltimo().contentEquals("Dormiu")) {
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.tipos, android.R.layout.simple_spinner_item);
            tipos.setAdapter(adapter);
        }else {
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.tipoParaDormiu, android.R.layout.simple_spinner_item);
            tipos.setAdapter(adapter);
        }

        //event button
        add = findViewById(R.id.buttonAddEvent);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");

                Date data = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(data);
                Date hora_atual = cal.getTime();

                String dataFormatada = formataData.format(data);
                String horaFormatada = dateFormat_hora.format(hora_atual);
                String tipoSelecionado = tipos.getSelectedItem().toString();
                Rotina nova = new Rotina(dataFormatada, horaFormatada, tipoSelecionado);

                RotinaRepositorio r = new RotinaRepositorio(conexao);
                r.insert(nova);

                Intent intent = new Intent(CadNewEvento.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CriarConexao(){
        try {
            dataOpenHelper = new DataOpenHelper(this);
            conexao = dataOpenHelper.getWritableDatabase();
        }catch (SQLException e){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("ERRO");
            dlg.setMessage(e.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

}
