package com.example.registrobd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {

    public static  final String NOMBRE_BD="datos.db";
    public static final int VERSION_BD=1;

    String tabla_articulo="CREATE TABLE persona("
            + "codigo INTEGER primary key AUTOINCREMENT, "
            + "nombre text not null, "
            + "apellido text not null, "
            + "dni text not null)";

    public Conexion(Context context){
        super(context,NOMBRE_BD, null,VERSION_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabla_articulo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
