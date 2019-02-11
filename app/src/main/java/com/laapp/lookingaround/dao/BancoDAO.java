package com.laapp.lookingaround.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.laapp.lookingaround.model.Banco;

import java.util.ArrayList;
import java.util.List;

public class BancoDAO extends SQLiteOpenHelper {


    public BancoDAO(Context context) {
        super(context, "Banco", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Bancos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Bancos;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Banco banco) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("nome", banco.getNome());
        dados.put("endereco", banco.getEndereco());
        dados.put("nota", banco.getNota());

        db.insert("Bancos", null, dados);
    }

    public List<Banco> buscaBancos() {
        String sql = "SELECT * FROM Bancos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Banco> bancos = new ArrayList<Banco>();
        while (c.moveToNext()){
            Banco banco = new Banco();
            banco.setId(c.getLong(c.getColumnIndex("id")));
            banco.setNome(c.getString(c.getColumnIndex("nome")));
            banco.setEndereco(c.getString(c.getColumnIndex("endereco")));
            banco.setNota(c.getDouble(c.getColumnIndex("nota")));

            bancos.add(banco);
        }
        c.close();

        return bancos;
    }

}
