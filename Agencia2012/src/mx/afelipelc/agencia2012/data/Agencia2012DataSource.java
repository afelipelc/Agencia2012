package mx.afelipelc.agencia2012.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Agencia2012DataSource {
    //campos para la conexion a la BD
    private SQLiteDatabase database;
    private Agencia2012DBHelper agenciadb;
    private String[] columnas = {Agencia2012DBHelper.IdColumna, Agencia2012DBHelper.Clave, Agencia2012DBHelper.Valor};

    public Agencia2012DataSource(Context context){
        agenciadb  = new Agencia2012DBHelper(context);
    }

    public void Open() throws SQLException {
        database = agenciadb.getWritableDatabase();
    }

    public void Close(){
        agenciadb.close();
    }

    public RegistroConfig GetRegistro(String Clave)
    {
        //Cursor cursor = database.query(Agencia2012DBHelper.Tabla, new String[] {Agencia2012DBHelper.IdColumna, Agencia2012DBHelper.Clave, Agencia2012DBHelper.Valor}, Agencia2012DBHelper.Clave, new String[]{String.valueOf(Clave)}, null,null,null);
        Cursor cursor = database.rawQuery("select * from " + Agencia2012DBHelper.Tabla + " where clave = ?", new String[]{Clave});
        //Cursor cursor = database.query(Agencia2012DBHelper.Tabla, null,null,null,null,null,null);
        Log.d("Registros en Cursor:", "Total Reg. en Cursor: " + cursor.getCount());

        if(cursor.moveToFirst()){
            Log.d("Valores Registro: ","Id: " + cursor.getInt(cursor.getColumnIndex(Agencia2012DBHelper.IdColumna)) + " | clave: " + cursor.getString(cursor.getColumnIndex(Agencia2012DBHelper.Clave)) + " | valor: " +cursor.getString(cursor.getColumnIndex(Agencia2012DBHelper.Valor)));
            RegistroConfig registro = new RegistroConfig();
            registro.setId(cursor.getInt(cursor.getColumnIndex(Agencia2012DBHelper.IdColumna)));
            registro.setClave(cursor.getString(cursor.getColumnIndex(Agencia2012DBHelper.Clave)));
            registro.setValor(cursor.getString(cursor.getColumnIndex(Agencia2012DBHelper.Valor)));
            cursor.close();
            return  registro;
        }else
        {
            Log.d("Error al cargar el registro", "No se encontró el registro con clave: " + Clave);
            return  null;
        }
    }
    
    public boolean ActualizaRegistro(RegistroConfig Registro){
        try{
        ContentValues valores = new ContentValues();
        //valores.put(Agencia2012DBHelper.Clave, Registro.getClave());
        valores.put(Agencia2012DBHelper.Valor, Registro.getValor());
        int  result = database.update(Agencia2012DBHelper.Tabla,valores,Agencia2012DBHelper.IdColumna + " = ?", new String[]{ String.valueOf(Registro.getId())});
        //database.rawQuery("update " + Agencia2012DBHelper.Tabla + " set "+ Agencia2012DBHelper.Clave + " = '"+ Registro.getValor()+ "' where clave =?", new String[]{ String.valueOf(Registro.getId())});
          Log.d("Consulta Ejecutada", "Se ejecuto la consulta de actualizacion. Resultado: " + result);
        return true;
        }catch (SQLException e)
        {
            Log.d("Error Actualizar DB", e.getMessage());
            return false;
        }
    }

}
