package com.example.registrobd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText nombre, apellido, dni;
    ListView lista;
    SQLiteDatabase sqLiteDatabase;
    Conexion conexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("REGISTRO DE PERSONAL");
        nombre = findViewById(R.id.txtnombre);
        apellido = findViewById(R.id.txtapellido);
        dni = findViewById(R.id.txtdni);
        lista = findViewById(R.id.lvdata);

        conexion =new  Conexion(this);
    }
    

    public void Registrar(View view){
        try {
            String obnombre = nombre.getText().toString();
            String obapellido = apellido.getText().toString();
            int obdni = Integer.parseInt(dni.getText().toString());

            if (!obnombre.isEmpty() || !obapellido.isEmpty() || obdni != 0){
                if (conexion!= null){
                    sqLiteDatabase = conexion.getWritableDatabase();
                    ContentValues valores = new ContentValues();
                    valores.put("nombre",obnombre);
                    valores.put("apellido",obapellido);
                    valores.put("dni",obdni);

                    long insertado = sqLiteDatabase.insert("persona",null, valores);
                    if (insertado !=-1){
                        Toast.makeText(this, "Se registro exitosamente", Toast.LENGTH_SHORT).show();
                        nombre.setText("");
                        apellido.setText("");
                        dni.setText("");
                        nombre.requestFocus();

                    }else {
                        Toast.makeText(this, "Ocurrio un erro", Toast.LENGTH_SHORT).show();
                    }
                    sqLiteDatabase.close();
                }
            }else {
                Toast.makeText(this, "Campos vacios", Toast.LENGTH_SHORT).show();
            }
           
        }catch (Exception e){
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
        }
    }

    public void Listar(View view){
        try {
            if (conexion!= null){
                sqLiteDatabase = conexion.getReadableDatabase();
                Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM persona",null);
                int cantidad = cursor.getCount();
                String [] arreglo  = new String[cantidad];
                int i = 0;
                if(cursor.moveToFirst()){
                    do{
                        String linea = cursor.getInt(0)+" "+cursor.getString(1)+" "+
                                cursor.getString(2)+" "+ cursor.getString(3);
                        arreglo[i] = linea;
                        i++;
                    }while (cursor.moveToNext());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arreglo);
                lista.setAdapter(adapter);
            }
        }catch (Exception e){
            Toast.makeText(this, "Error al listar", Toast.LENGTH_SHORT).show();
        }
    }

}
