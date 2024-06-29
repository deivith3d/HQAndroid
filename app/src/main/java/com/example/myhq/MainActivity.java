package com.example.myhq;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView lvGibis;
    private AdapterCustomizado adapterCustomizado;
    private List<Gibi> listaDeGibis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();

        lvGibis = findViewById(R.id.listGibis);
        carregarGibis();

        FloatingActionButton fab = findViewById(R.id.floatingActionButtonInluir);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
            intent.putExtra("acao", "inserir");
            startActivity(intent);
        });

        lvGibis.setOnItemClickListener((parent, view, position, id) -> {
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
        });

        lvGibis.setOnItemLongClickListener((parent, view, position, id) -> {
            excluir(position);
            return true;
        });
    }

    private void excluir(int posicao) {
        Gibi gb = listaDeGibis.get(posicao);
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Excluir...");
        alerta.setIcon(android.R.drawable.ic_delete);
        alerta.setMessage("Confirma a exclusão do produto " + gb.getTitulo() + " n: " + gb.getNumero() + " ?");
        alerta.setNeutralButton("Cancelar", null);
        alerta.setPositiveButton("Sim", (dialogInterface, i) -> {
            GibiDAO.excluir(MainActivity.this, gb.getId());
            carregarGibis();
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
        ArrayList<String> titulos = new ArrayList<>();
        ArrayList<String> series = new ArrayList<>();
        ArrayList<String> editoras = new ArrayList<>();
        ArrayList<String> capas = new ArrayList<>();
        ArrayList<Integer> numeros = new ArrayList<>();
        ArrayList<String> anos = new ArrayList<>();
        ArrayList<Integer> adquiridos = new ArrayList<>();

        if (listaDeGibis.size() == 0) {
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
            for (Gibi gibi : listaDeGibis) {
                titulos.add(gibi.getTitulo());
                series.add(gibi.getSerie());
                editoras.add(gibi.getEditora());
                capas.add(gibi.getImagem());
                numeros.add(gibi.getNumero());
                anos.add(gibi.getAno());
                adquiridos.add(gibi.getAdquirido());
                Log.d("MainActivity", "Imagem caminho: " + gibi.getImagem());
            }
        }

        adapterCustomizado = new AdapterCustomizado(this, titulos, series, editoras, capas, numeros, anos, adquiridos);
        lvGibis.setAdapter(adapterCustomizado);
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }
}
