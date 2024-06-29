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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class SegundaActivity extends AppCompatActivity {
    private Spinner spTitulo;
    private Spinner spSerie;
    private Spinner spEditora;
    private EditText etNumero;
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
    private String[] titulos;
    private String[] series;
    private String[] editoras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        titulos = getResources().getStringArray(R.array.titulos);
        spTitulo = findViewById(R.id.spinnerTitulo);
        ArrayAdapter<String> adapTitulos = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1,
                titulos
        );
        adapTitulos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTitulo.setAdapter(adapTitulos);

        spTitulo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Aqui você pode lidar com a seleção do spinner, se necessário
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        series = getResources().getStringArray(R.array.series);
        spSerie = findViewById(R.id.spinnerSerie);
        ArrayAdapter<String> adapSeries = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1,
                series
        );
        adapSeries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSerie.setAdapter(adapSeries);
        spSerie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Aqui você pode lidar com a seleção do spinner, se necessário
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editoras = getResources().getStringArray(R.array.editoras);
        spEditora = findViewById(R.id.spinnerEditora);
        ArrayAdapter<String> adapEditora = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                android.R.id.text1,
                editoras
        );
        adapEditora.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEditora.setAdapter(adapEditora);
        spEditora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Aqui você pode lidar com a seleção do spinner, se necessário
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etNumero = findViewById(R.id.editTextNumero);
        etImagem = findViewById(R.id.editTextImagem);
        etAno = findViewById(R.id.editTextAno);
        cbAdquirido = findViewById(R.id.checkboxAdquirido);
        btOk = findViewById(R.id.buttonInserir);
        btCarregaImagem = findViewById(R.id.buttonCarregarCapa);
        ivCarrega = findViewById(R.id.imageViewNovaCapa);
        imagePath = "/storage/emulated/0/Download/generica.jpg";
        ivCarrega.setImageResource(R.drawable.generica);
        gb = new Gibi();

        acao = getIntent().getStringExtra("acao");
        if (acao.equals("editar")) {
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
                if (cursor != null) {
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
                        etImagem.setText(imagePath);
                    }
                }
            }
        }
    }

    public void salvar() {
        Toast.makeText(this, "Entrou no salvar!!", Toast.LENGTH_LONG).show();
        String titulo = spTitulo.getSelectedItem().toString();
        int numero = 0;
        try {
            numero = Integer.parseInt(etNumero.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String serie = spSerie.getSelectedItem().toString();
        String editora = spEditora.getSelectedItem().toString();
        String imagem = etImagem.getText().toString();
        String ano = etAno.getText().toString();
        int adquirido = cbAdquirido.isChecked() ? 1 : 0;

        if (titulo.isEmpty() || numero == 0 || serie.isEmpty() || editora.isEmpty()) {
            Toast.makeText(this, "Você deve preencher todos os campos obrigatórios!!", Toast.LENGTH_LONG).show();
        } else {
            if (acao.equals("inserir")) {
                gb = new Gibi();
            }
            gb.setTitulo(titulo);
            gb.setNumero(numero);
            gb.setSerie(serie);
            gb.setEditora(editora);
            gb.setImagem(imagePath);
            gb.setAno(ano);
            gb.setAdquirido(adquirido);

            if (acao.equals("inserir")) {
                GibiDAO.inserir(this, gb);
                limparCampos();
            } else {
                GibiDAO.editar(this, gb);
                finish();
            }
        }
    }

    private void limparCampos() {
        etNumero.setText("");
        etImagem.setText("");
        ivCarrega.setImageResource(R.drawable.generica);
        etAno.setText("");
        cbAdquirido.setChecked(false);
    }

    public void carregarFormulario() throws IOException {
        int id = getIntent().getIntExtra("idGibi", 0);
        Log.e("SegundaActivity", "O ID é: " + id);
        tvID = findViewById(R.id.textViewID);
        gb = GibiDAO.getGibiById(this, id);
        if (gb == null) {
            Log.e("SegundaActivity", "Objeto Gibi está null");
            return;
        }
        tvID.setText(String.valueOf(id));

        int index = findIndex(titulos, gb.getTitulo());
        if (index != -1) {
            spTitulo.setSelection(index);
        } else {
            Log.e("SegundaActivity", "Título não encontrado: " + gb.getTitulo());
        }

        int index2 = findIndex(series, gb.getSerie());
        if (index2 != -1) {
            spSerie.setSelection(index2);
        } else {
            Log.e("SegundaActivity", "Série não encontrada: " + gb.getSerie());
        }

        int index3 = findIndex(editoras, gb.getEditora());
        if (index3 != -1) {
            spEditora.setSelection(index3);
        } else {
            Log.e("SegundaActivity", "Editora não encontrada: " + gb.getEditora());
        }

        etNumero.setText(String.valueOf(gb.getNumero()));
        etImagem.setText(gb.getImagem());

        Bitmap bitmap = BitmapFactory.decodeFile(gb.getImagem());
        ivCarrega.setImageBitmap(bitmap);

        etAno.setText(gb.getAno());
        cbAdquirido.setChecked(gb.getAdquirido() == 1);
    }

    private int findIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}
