package com.example.myhq;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterCustomizado extends BaseAdapter {
    Context context;
    List<String> lTitulos;
    List<String> lSeries;
    List<String> lEditoras;
    List<String> lCapas;
    List<Integer> lNumeros;
    List<String> lAnos;
    List<Integer> lAdquiridos;
    LayoutInflater inflater;

    public AdapterCustomizado(Context context, List<String> titulos, List<String> series, List<String> editoras, List<String> capas, List<Integer> numeros, List<String> anos, List<Integer> adquiridos) {
        this.context = context;
        this.lTitulos = titulos;
        this.lSeries = series;
        this.lEditoras = editoras;
        this.lCapas = capas;
        this.lNumeros = numeros;
        this.lAnos = anos;
        this.lAdquiridos = adquiridos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lTitulos.size();
    }

    @Override
    public Object getItem(int position) {
        return lTitulos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_list_view_customizada, parent, false);
        }

        TextView tvTitulo = convertView.findViewById(R.id.textViewTitulo);
        TextView tvNumero = convertView.findViewById(R.id.textViewNumero);
        TextView tvSerie = convertView.findViewById(R.id.textViewSerie);
        TextView tvEditora = convertView.findViewById(R.id.textViewEditora);
        ImageView capaImg = convertView.findViewById(R.id.imageViewCapa);
        TextView tvAno = convertView.findViewById(R.id.textViewAno);
        CheckBox cbAdquirido = convertView.findViewById(R.id.checkBoxAdquirido);

        tvTitulo.setText(lTitulos.get(position));
        tvNumero.setText(String.valueOf(lNumeros.get(position)));
        tvSerie.setText(lSeries.get(position));
        tvEditora.setText(lEditoras.get(position));

        String caminhoImagem = lCapas.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(caminhoImagem);
        if (bitmap != null) {
            capaImg.setImageBitmap(bitmap);
        } else {
            capaImg.setImageResource(R.drawable.generica);
            Log.e("AdapterCustomizado", "Falha ao carregar a imagem no caminho: " + caminhoImagem);
        }

        tvAno.setText(String.valueOf(lAnos.get(position)));
        cbAdquirido.setChecked(lAdquiridos.get(position) == 1);

        if (lAdquiridos.get(position) == 1) {
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.YELLOW);
        }

        return convertView;
    }
}
