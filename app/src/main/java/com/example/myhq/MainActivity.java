package com.example.myhq;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView lvGibis;
   // private ArrayAdapter adapter;
   AdapterCustomizado adapterCustomizado;

    //private ArrayAdapter<AdapterCustomizado> adapter;
    private List<Gibi> listaDeGibis;

    String[] titulos = { "Título 1", "Título 2", "Título 3" };
    String[] series = { "Série 1", "Série 2", "Série 3" };
    String[] editoras = { "Editora 1", "Editora 2", "Editora 3" };
    int[] capas = { R.drawable.xmn030301t, R.drawable.xmn030302t, R.drawable.xmn030303t };
    int[] numeros = { 1, 2, 3 };

    int[] anos = {0};

    int[] adquiridos = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

                int idProduto = listaDeGibis.get(position).getId();
                //System.out.println("id::: " + listaDeProdutos.get(position).getId() + ".........");
                Intent intent = new Intent(MainActivity.this, SegundaActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("idGibi", id);
                startActivity(intent);
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

        System.out.println("Encontrados: " + listaDeGibis.size() + " registros.");
        System.out.println("Array: " + listaDeGibis);
        if (listaDeGibis.size() == 0) {
            System.out.println("Entrou no IF");
            Gibi fake = new Gibi("Lista Vazia...", "");
            listaDeGibis.add(fake);
            lvGibis.setEnabled(false);
            titulos[0] = "Lista Vazia...";
            series[0] = "";
            editoras[0] = "";
            capas[0] = 0;
            numeros[0] = 0;
            anos[0] =0;
            adquiridos[0] = 0;
        } else {
            lvGibis.setEnabled(true);
            int x=0;
            for(Gibi i : listaDeGibis) {
                titulos[x] = i.getTitulo();
                series[x] = i.getSerie();
                editoras[x] = i.getEditora();
                capas[x] = i.getImagem();
                numeros[x] =i.getNumero();
                anos[x] =i.getAno();
                adquiridos[x] = i.getAdquirido();

                x++;
            }

        }
      /*  String[] titulos = { "Título 1", "Título 2", "Título 3" };
        String[] series = { "Série 1", "Série 2", "Série 3" };
        String[] editoras = { "Editora 1", "Editora 2", "Editora 3" };
        int[] capas = { R.drawable.xmn030301t, R.drawable.xmn030302t, R.drawable.xmn030303t };
        int[] numeros = { 1, 2, 3 };

        int[] anos= { 1, 2, 3 };

        int[] adquiridos= { 1, 2, 3 };*/



        adapterCustomizado = new AdapterCustomizado(this, titulos, series, editoras, capas, numeros, anos,adquiridos);
        lvGibis.setAdapter(adapterCustomizado);
    }





}