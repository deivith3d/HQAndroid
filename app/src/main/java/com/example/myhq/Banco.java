package com.example.myhq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Banco extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "MeuBancoHQ";


    public Banco(@Nullable Context context) {
        super(context, NOME_BANCO, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS gibis( "+
                " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , "+
                " titulo TEXT NOT NULL, " +
                " numero INTEGER , " +
                " editora TEXT , " +
                " serie TEXT , " +
                " imagem INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
