package com.thiagoivens.babe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.thiagoivens.babe.adapters.RotinaAdapter;
import com.thiagoivens.babe.database.DataOpenHelper;
import com.thiagoivens.babe.dominio.entidades.Rotina;
import com.thiagoivens.babe.dominio.repositorio.RotinaRepositorio;
import com.thiagoivens.babe.adapters.ResumoAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rotina;
    private SQLiteDatabase conexao;
    private DataOpenHelper dataOpenHelper;
    private RotinaAdapter rotinaAdapter;
    private RotinaRepositorio rotinaRepositorio;
    List<Rotina> listRotina;
    List<String> listSpinnerFilter;
    ArrayAdapter<?> arrayAdapter;
    Spinner spinnerFilter;
    String[] categorias = {"Todos","Dormiu","Acordou","Trocou","Mamou","Resumo do Sono"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rotina = findViewById(R.id.recyclerview_id);

        criarConexao();
        initializeViews();
    }

    private void initializeViews(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rotina.setLayoutManager(linearLayoutManager);
        rotinaRepositorio = new RotinaRepositorio(conexao);
        listRotina = rotinaRepositorio.getAll();
        rotinaAdapter = new RotinaAdapter(listRotina);
        rotina.setAdapter(rotinaAdapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rotina);

        //SPINNER RESUMO
        spinnerFilter = findViewById(R.id.spinner_filter);
        spinnerFilter.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categorias));
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < categorias.length){
                    getSelectedCategoryData(position);
                }else{
                    Toast.makeText(MainActivity.this, "A categoria nao existe", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void criarConexao(){
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

    public void add(View view){
        Intent intent = new Intent(MainActivity.this, CadNewEvento.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Rotina> listRotina = rotinaRepositorio.getAll();
        rotinaAdapter = new RotinaAdapter(listRotina);
        rotina.setAdapter(rotinaAdapter);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            RotinaRepositorio rotinaRepositorio = new RotinaRepositorio(conexao);
            rotinaRepositorio.delete(listRotina.get(viewHolder.getAdapterPosition()).id);
            listRotina.remove(viewHolder.getAdapterPosition());
            rotinaAdapter.notifyDataSetChanged();
        }
    };

    private void getSelectedCategoryData(int id){
        switch (id){
            case 0:
                listRotina = rotinaRepositorio.getAll();
                rotinaAdapter = new RotinaAdapter(listRotina);
                rotina.setAdapter(rotinaAdapter);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rotina);
                break;
            case 1:
                listRotina = rotinaRepositorio.getType("Dormiu");
                rotinaAdapter = new RotinaAdapter(listRotina);
                rotina.setAdapter(rotinaAdapter);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rotina);
                break;
            case 2:
                listRotina = rotinaRepositorio.getType("Acordou");
                rotinaAdapter = new RotinaAdapter(listRotina);
                rotina.setAdapter(rotinaAdapter);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rotina);
                break;
            case 3:
                listRotina = rotinaRepositorio.getType("Trocou");
                rotinaAdapter = new RotinaAdapter(listRotina);
                rotina.setAdapter(rotinaAdapter);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rotina);
                break;
            case 4:
                listRotina = rotinaRepositorio.getType("Mamou");
                rotinaAdapter = new RotinaAdapter(listRotina);
                rotina.setAdapter(rotinaAdapter);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rotina);
                break;
            case 5:
                listRotina = rotinaRepositorio.getResumoSono();
                ResumoAdapter resumoAdapter = new ResumoAdapter(listRotina);
                rotina.setAdapter(resumoAdapter);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rotina);
                break;
        }
    }

}
