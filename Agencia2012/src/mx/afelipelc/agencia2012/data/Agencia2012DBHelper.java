package mx.afelipelc.agencia2012.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Agencia2012DBHelper extends SQLiteOpenHelper{
    public static  final String Tabla="configuracion";
    public static  final String IdColumna="id";
    public static  final String Clave="clave";
    public static  final String Valor="valor";
    public static  final String NombreBD="agencia2012.db";
    private static final int VersionBD = 1;

    //Formar la consulta SQL para crear la BD
    private static final String crear_bd = "create table "
            + Tabla + "( " + IdColumna
            + " integer primary key autoincrement, " + Clave
            + " text not null, " + Valor
            + " text not null);";

    private static final  String urlservices= "insert into " + Tabla
            + "(" + Clave + ", " + Valor + ") values('urlservicios','http://172.16.239.237:8080/webservices/')";

    public Agencia2012DBHelper(Context context) {
        super(context, NombreBD, null, VersionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(crear_bd);
        //insertar el reg de la url de los servicios
        database.execSQL(urlservices);

//        ContentValues valor = new ContentValues();
//        valor.put(Clave,"urlservicios");
//        valor.put(Valor,"http://172.16.239.237:8080/webservices/");
//        database.insert(Tabla, null, valor);
        Log.d("Registro inicial","Se registro la url de servicios web");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Agencia2012DBHelper.class.getName(),
                "Actualizando la BD de la versión " + oldVersion + " a "
                        + newVersion + ", La cual puede eliminar todos los datos previamente almacenados.");

        db.execSQL("DROP TABLE IF EXISTS" + Tabla);
        onCreate(db);
    }

}
