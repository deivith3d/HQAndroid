package com.example.myhq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class SegundaActivity extends AppCompatActivity {
    private EditText etTitulo;
    private EditText etNumero;
    private EditText etSerie;
    private EditText etEditora;
    private EditText etImagem;
    private EditText etAno;
    private CheckBox cbAdquirido;
    private String acao;
    private Gibi gb;
    private TextView tvID;
    private Button btOk;

    private Button btCarregaImagem;

    private ImageView ivCarrega;
    private static final int PICK_IMAGE = 100;
    private int currentImageIndex = -1;
    private String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        etTitulo = findViewById(R.id.editTextTitulo);
        etNumero = findViewById(R.id.editTextNumero);
        etSerie = findViewById(R.id.editTextSerie);
        etEditora = findViewById(R.id.editTextEditora);
        etImagem = findViewById(R.id.editTextImagem);
        etAno = findViewById(R.id.editTextAno);
        cbAdquirido = findViewById(R.id.checkboxAdquirido);
        btOk = findViewById(R.id.buttonInserir);
        btCarregaImagem = findViewById(R.id.buttonCarregarCapa);
        ivCarrega =findViewById(R.id.imageViewNovaCapa);
        imagePath = "/storage/emulated/0/Download/generica.jpg";
        ivCarrega.setImageResource(R.drawable.generica);
        gb = new Gibi();
       // Bitmap bitmap = null;
      //  try {
      //      bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(imagePath));
      //  } catch (IOException e) {
      //      throw new RuntimeException(e);
      //  }
       // ivCarrega.setImageBitmap(bitmap);
        acao = getIntent().getStringExtra("acao");
        if(acao.equals("editar"))
        {
            try {
                carregarFormulario();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 salvar();
            }
        });

      //  btCarregaImagem = findViewById(getResources().getIdentifier("btnChoosePhoto" + (1), "id", getPackageName()));
        btCarregaImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImageIndex = 0;
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data.getData() != null) {
                Uri selectedImageUri = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (currentImageIndex != -1) {
                    ivCarrega.setImageBitmap(bitmap);
                    currentImageIndex = -1;
                   // TextView imagePathTextView = findViewById(R.id.textView);
                    ///imagePathTextView.setText(imagePath);
                    ///imagePathTextView.setVisibility(View.VISIBLE); // torna o TextView visível
                    etImagem.setText(imagePath);
                    //etImagem
                }
            }
        }
    }

    public void salvar()
    {
        Toast.makeText(this, "Entrou no salvar!!", Toast.LENGTH_LONG).show();
        String titulo = etTitulo.getText().toString();
        int numero = 0;
        try {
            numero = Integer.parseInt(etNumero.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        };
        String serie = etSerie.getText().toString();
        String editora = etEditora.getText().toString();
        String imagem = etImagem.getText().toString();
        String imagePath = etImagem.getText().toString();
        String ano = etAno.getText().toString();
        int adquirido =0;
        if(cbAdquirido.isChecked())
            adquirido = 1;
        else
            adquirido = 0;

        if(titulo.isEmpty()||numero==0||serie.isEmpty()||editora.isEmpty())//||imagem==0)
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
            gb.setSerie(serie+" Série");
            gb.setEditora(editora);
            gb.setImagem(imagePath); /////****************
            gb.setAno(ano);
            gb.setAdquirido(adquirido);
            //Toast.makeText(this, "Chegou aqui!!", Toast.LENGTH_LONG).show();
            if(acao.equals("inserir")){
                GibiDAO.inserir(this,gb);
                etTitulo.setText("");
                etNumero.setText("");
                etSerie.setText("");
                etEditora.setText("");
                etImagem.setText("");
                //imagePath = "/storage/emulated/0/Download/generica.jpg";
                ivCarrega.setImageResource(R.drawable.generica);
                etAno.setText("");
                cbAdquirido.setChecked(false);
              }
            else
            {
                GibiDAO.editar(this,gb);
                finish();
            }
        }
    }

    public void carregarFormulario() throws IOException {

        int id = getIntent().getIntExtra("idGibi",0);
        Log.e("SegundaActivity", "O ID é: "+id);
        tvID = findViewById(R.id.textViewID);
        gb = new Gibi();
        gb = GibiDAO.getGibiById(this,id);
        if (gb == null) {
            // Lide com o caso de null, talvez registre um erro ou inicialize o objeto
            Log.e("SegundaActivity", "Objeto Gibi está null");
            return;
        }
        tvID.setText(String.valueOf(id));
        etTitulo.setText(gb.getTitulo());
        etNumero.setText(String.valueOf(gb.getNumero()));
        etSerie.setText(gb.getSerie());
        etEditora.setText(gb.getEditora());
        etImagem.setText(gb.getImagem());
        //ivCarrega.setImageResource(R.drawable.generica);
        String imagePath = gb.getImagem();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
       // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(imagePath));
        ivCarrega.setImageBitmap(bitmap);
        etAno.setText(gb.getAno());
        if(gb.getAdquirido()==1)
            cbAdquirido.setChecked(true);
        else
            cbAdquirido.setChecked(false);

    }
}