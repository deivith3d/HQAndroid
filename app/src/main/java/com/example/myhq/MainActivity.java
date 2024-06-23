package com.example.myhq;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.Manifest;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView lvGibis;
   // private ArrayAdapter adapter;
   AdapterCustomizado adapterCustomizado;

    //private ArrayAdapter<AdapterCustomizado> adapter;
    private List<Gibi> listaDeGibis;

    private ArrayList<String> titulos;
    private ArrayList<String>series;
    private ArrayList<String> editoras;
    //int[] capas = { R.drawable.xmn030301t, R.drawable.xmn030302t, R.drawable.xmn030303t };
    private ArrayList<String> capas;
    private ArrayList<Integer> numeros;

    private ArrayList<String> anos;

    private ArrayList<Integer> adquiridos;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        lvGibis = findViewById(R.id.listGibis);
        carregarGibis();
        FloatingActionButton fab = findViewById(R.id.floatingActionButtonInluir);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });

        lvGibis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listaDeGibis != null && position >= 0 && position < listaDeGibis.size()) {
                    Gibi gibi = listaDeGibis.get(position);
                    if (gibi != null) {
                        int idg = gibi.getId();
                        Log.d("MainActivity", "idProduto: " + idg);

                        Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
                        intent.putExtra("acao", "editar");
                        intent.putExtra("idGibi", idg);
                        startActivity(intent);
                    } else {
                        Log.e("MainActivity", "Gibi at position is null");
                    }
                } else {
                    Log.e("MainActivity", "Invalid position or listaDeGibis is null");
                }
            }
        });

        lvGibis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //System.out.println("Entrou clique longo");
                excluir(position);
                return true;
            }
        });
    }
    private void excluir(int posicao) {
        Gibi gb = listaDeGibis.get(posicao);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Excluir...");
        alerta.setIcon(android.R.drawable.ic_delete);
        alerta.setMessage("Confirma a exclusão do produto " + gb.getTitulo() + " n: "+ gb.getNumero() +" ?");
        alerta.setNeutralButton("Cancelar", null);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GibiDAO.excluir(MainActivity.this, gb.getId());
                carregarGibis();
            }
        });
        alerta.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarGibis();
    }

    private void carregarGibis() {
        listaDeGibis = GibiDAO.getGibis(this);
        titulos = new ArrayList<String>();
        series = new ArrayList<String>();
        editoras = new ArrayList<String>();
        capas = new ArrayList<String>();
        numeros = new ArrayList<Integer>();
        anos = new ArrayList<String>();
        adquiridos = new ArrayList<Integer>();

        System.out.println("Encontrados: " + listaDeGibis.size() + " registros.");
        System.out.println("Array: " + listaDeGibis);
        if (listaDeGibis.size() == 0) {
            System.out.println("Entrou no IF");
            Gibi fake = new Gibi("Lista Vazia...", "");
            listaDeGibis.add(fake);
            lvGibis.setEnabled(false);
            titulos.add("Lista Vazia...");
            series.add("");
            editoras.add("");
            capas.add("");
            numeros.add(0);
            anos.add("");
            adquiridos.add(0);
        } else {
            lvGibis.setEnabled(true);
            int x = 0;


            for (Gibi i : listaDeGibis) {

                titulos.add(i.getTitulo());
                series.add(i.getSerie());
                editoras.add(i.getEditora());
                capas.add(i.getImagem());
                numeros.add(i.getNumero());
                anos.add(i.getAno());
                adquiridos.add(i.getAdquirido());
                Log.d("MainActivity", "Imagem caminho: " + i.getImagem());
                //x++;
            }

        }/*
       String[] titulos = { "Título 1", "Título 2", "Título 3" };
        String[] series = { "Série 1", "Série 2", "Série 3" };
        String[] editoras = { "Editora 1", "Editora 2", "Editora 3" };
        int[] capas = { R.drawable.xmn030301t, R.drawable.xmn030302t, R.drawable.xmn030303t };
        int[] numeros = { 1, 2, 3 };

        int[] anos= { 1, 2, 3 };

        int[] adquiridos= { 1, 2, 3 };*/



        adapterCustomizado = new AdapterCustomizado(this, titulos, series, editoras, capas, numeros, anos,adquiridos);
        lvGibis.setAdapter(adapterCustomizado);
    }
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }






}