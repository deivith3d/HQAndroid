package com.example.myhq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SegundaActivity extends AppCompatActivity {
    private EditText etTitulo;
    private EditText etNumero;
    private EditText etSerie;
    private EditText etEditora;
    private EditText etImagem;
    private String acao;
    private Gibi gb;
    private TextView tvID;
    private Button btOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        etTitulo = findViewById(R.id.editTextTitulo);
        etNumero = findViewById(R.id.editTextNumero);
        etSerie = findViewById(R.id.editTextSerie);
        etEditora = findViewById(R.id.editTextEditora);
        etImagem = findViewById(R.id.editTextImagem);
        btOk = findViewById(R.id.buttonInserir);
        acao = getIntent().getStringExtra("acao");
        if(acao.equals("editar"))
        {
            carregarFormulario();
        }
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    public void salvar()
    {
        String titulo = etTitulo.getText().toString();
        int numero = Integer.parseInt(etNumero.getText().toString());
        String serie = etSerie.getText().toString();
        String editora = etEditora.getText().toString();
        int imagem = Integer.parseInt(etImagem.getText().toString());

        if(titulo.isEmpty()||numero==0||serie.isEmpty()||editora.isEmpty()||imagem==0)
        {
            Toast.makeText(this, "Você deve preencher ambos os campos!!", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(acao.equals("inserir")){
                gb = new Gibi();
            }
            gb.setTitulo(titulo);
            gb.setNumero(numero);
            gb.setSerie(serie);
            gb.setEditora(editora);
            gb.setImagem(imagem);
            if(acao.equals("inserir")){
                GibiDAO.inserir(this,gb);
                etTitulo.setText("");
                etNumero.setText("");
                etSerie.setText("");
                etEditora.setText("");
                etImagem.setText("");
              }
            else
            {
                GibiDAO.editar(this,gb);
                finish();
            }
        }
    }

    public void carregarFormulario()
    {
        int id = getIntent().getIntExtra("id",0);
        tvID = findViewById(R.id.textViewID);
        gb = GibiDAO.getGibiById(this,id);
        tvID.setText(String.valueOf(id));
        etTitulo.setText(gb.getTitulo());
        etNumero.setText(gb.getNumero());
        etSerie.setText(gb.getSerie());
        etEditora.setText(gb.getEditora());
        etImagem.setText(gb.getImagem());
    }
}