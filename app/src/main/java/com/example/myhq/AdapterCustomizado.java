package com.example.myhq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterCustomizado extends BaseAdapter {
    Context context;
    String lTitulos[];
    String lSeries[];
    String lEditoras[];
    int lCapas[];
    int lNumeros[];
    LayoutInflater inflater;

    public AdapterCustomizado(Context context, String[] titulos, String[] series, String[] editoras, int[] capas, int[] numeros) {
        this.context = context;
        this.lTitulos = titulos;
        this.lSeries = series;
        this.lEditoras = editoras;
        this.lCapas = capas;
        this.lNumeros = numeros;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lTitulos.length;
    }

    @Override
    public Object getItem(int position) {
        return lTitulos[position];
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

        tvTitulo.setText(lTitulos[position]);
        tvNumero.setText(String.valueOf(lNumeros[position]));
        tvSerie.setText(lSeries[position]);
        tvEditora.setText(lEditoras[position]);
        capaImg.setImageResource(lCapas[position]);

        return convertView;
    }
}
