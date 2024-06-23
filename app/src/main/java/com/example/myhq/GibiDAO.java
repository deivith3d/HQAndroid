package com.example.myhq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GibiDAO {

    public static void inserir(Context context, Gibi gibi){
        //Classe que armazena os dados no banco
        ContentValues values = new ContentValues();
        values.put("titulo",gibi.getTitulo());
        values.put("numero",gibi.getNumero());
        values.put("serie",gibi.getSerie());
        values.put("editora",gibi.getEditora());
        values.put("imagem",gibi.getImagem());
        values.put("ano",gibi.getAno());
        values.put("adquirido",gibi.getAdquirido());

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.insert("gibis",null,values);
    }

    //Editar um Produto
    public static void editar(Context context,Gibi gibi){
        // System.out.println("Entrou no Editar DAO");
        ContentValues values = new ContentValues();
        values.put("titulo",gibi.getTitulo());
        values.put("numero",gibi.getNumero());
        values.put("serie",gibi.getSerie());
        values.put("editora",gibi.getEditora());
        values.put("imagem",gibi.getImagem());
        values.put("ano",gibi.getAno());
        values.put("adquirido",gibi.getAdquirido());

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        // atualiza de acordo com o id do prodduto
        db.update("gibis",values," id = " + gibi.getId(),null);

    }

    //Excluir um Produto
    public static void excluir(Context context,int idGibi){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        //Deleta de acordo com o id do produto
        db.delete("gibis"," id = " + idGibi,null);
    }

    //Preencher uma ArrayList com os Produtos do banco
    public static List<Gibi> getGibis(Context context){
        List<Gibi> lista = new ArrayList<>(); // Lista de Objetos do tipo Gibi
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        //Classe que percorre os registros do banco
        Cursor cursor = db.rawQuery("SELECT * FROM gibis ORDER BY titulo",null);
        //Preenche a lista de Produtos a partir do banco
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                Gibi gb = new Gibi();
                gb.setId(cursor.getInt(0));
                gb.setTitulo(cursor.getString(1));
                gb.setNumero(cursor.getInt(2));
                gb.setSerie(cursor.getString(3));
                gb.setEditora(cursor.getString(4));
                gb.setImagem(cursor.getString(5));
                gb.setAno(cursor.getString(6));
                gb.setAdquirido(cursor.getInt(7));
                lista.add(gb); //adiciona o objeto na Lista de Objetos
            }while(cursor.moveToNext());
        }
        return lista; //retorna a lista com os objetos encontrados no banco
    }

    //Buscar um produto do banco pelo seu ID
    public static Gibi getGibiById(Context context, int idGibi){
        Gibi gb = new Gibi();
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM gibis WHERE id = "+idGibi,null);
        int e = cursor.getCount();
        Log.d("SecondaryActivity","Cursor Contador: "+e);
        if(cursor.getCount()>0){
            cursor.moveToFirst();

            gb.setId(cursor.getInt(0));
            gb.setTitulo(cursor.getString(1));
            gb.setNumero(cursor.getInt(2));
            gb.setSerie(cursor.getString(3));
            gb.setEditora(cursor.getString(4));
            gb.setImagem(cursor.getString(5));
            gb.setAno(cursor.getString(6));
            gb.setAdquirido(cursor.getInt(7));

        }
        else
        {
            return null;
        }
        return gb;
    }




}
