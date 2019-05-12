package id.ac.umn.budimanputrajaya;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CredentialDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static String DB_NAME = "Credential.db";
    private static String DB_PATH;
    private static final String TABLE_USER = "Users";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private SQLiteDatabase mDatabase;
    private Context mContext;

    CredentialDbHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        mContext = context;
        openDatabase();
    }

//    START CONFIGURE DATABASE
    private void openDatabase(){
        String path = DB_PATH+DB_NAME;
        if(mDatabase == null){
            createDatabase();
            mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    private void createDatabase(){
        boolean isDBExist = checkDataBase();
        if(!isDBExist){
            this.getReadableDatabase();
            copyDatabase();
        }
        else{
            Log.i(this.getClass().toString(), "Credential database already exist!");
        }
    }

    private boolean checkDataBase(){
        return mContext.getDatabasePath(DB_NAME).exists();
    }

    private void copyDatabase(){
        try{
            InputStream externalDBStream = mContext.getAssets().open(DB_NAME);
            OutputStream localDBStream = new FileOutputStream(mContext.getDatabasePath(DB_NAME));
            byte[] buffer = new byte[externalDBStream.available()];

            externalDBStream.read(buffer);
            localDBStream.write(buffer);
            localDBStream.flush();
            localDBStream.close();
            externalDBStream.close();
        }
        catch(IOException e){
            Log.e(this.getClass().toString(), "Copying database error");
        }
    }
//    END CONFIGURE DATABASE

    boolean isUserExist(String username, String password){
        try{
            Cursor cursor = mDatabase.query(
                    TABLE_USER, null,
                    COLUMN_USER_NAME+" = ? AND "+COLUMN_USER_PASSWORD+" = ?",
                    new String[] {username, password},
                    null, null, null, null
            );

            if(cursor == null) return false;
            int count = cursor.getCount();
            cursor.close();

            return count > 0;
        }
        catch(Exception e){
            Log.e(this.getClass().toString(), "Failed Check Data!");
        }

        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if(mDatabase != null)
            mDatabase.close();
        super.close();
    }
}
