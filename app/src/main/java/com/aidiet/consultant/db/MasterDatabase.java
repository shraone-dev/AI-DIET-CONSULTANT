package com.aidiet.consultant.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.aidiet.consultant.BMIApp;

public class MasterDatabase {
    private static MasterDatabase _instance;

    private DatabaseHelper _databaseHelper;
    private SQLiteDatabase _sqliteDatabase;

    private static final String DATABASE_NAME = "diet.sql";
    private static final int DATABSE_VERSION = 1;
    private String _basePath = Environment.getDataDirectory().getPath() + File.separator + "data" + File.separator + BMIApp.appContext.getPackageName() + File.separator;
    private String _databasePath = _basePath + "databases" + File.separator;

    /**
     * Private constructor.
     */
    private MasterDatabase() {
        try {
            _databasePath = BMIApp.appContext.getDatabasePath(DATABASE_NAME).getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            _databasePath = _basePath + "databases" + File.separator;
        }
        _initializeDataBase();
        _databaseHelper = new DatabaseHelper(BMIApp.appContext, DATABASE_NAME, null, DATABSE_VERSION);
        _openDataWritabaleBase();
    }

    /**
     * Method to get the initialized object of type {@link MasterDatabase}
     *
     * @return the initialized object of type {@link MasterDatabase}
     */
    public static MasterDatabase getInstance() {
        if (_instance == null) {
            _instance = new MasterDatabase();
        }
        return _instance;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    private void _initializeDataBase() {
        boolean dbExist = _isDataBaseExists();
        if (dbExist) {
            /**
             * Do nothing - database already exist
             */
        } else {
            /**
             * By calling this method an empty database will be created into the default system path
             * of your application.
             */
            _databaseHelper = new DatabaseHelper(BMIApp.appContext, DATABASE_NAME, null, DATABSE_VERSION);
            _databaseHelper.getReadableDatabase();
            _databaseHelper.close();
            try {
                _copyDataBase();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the
     * application.
     *
     * @return true if database exists, false otherwise
     */
    private boolean _isDataBaseExists() {
        boolean exists = false;
        try {
            File file = new File(_databasePath);
            if (file != null && file.exists() && file.length() > 0) {
                exists = true;
            }
        } catch (Exception e) {
            /**
             * Database does't exist yet.
             */
            exists = false;
        }
        return exists;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     */
    private void _copyDataBase() throws IOException {
        /**
         * Open your local db as the input stream
         */
        InputStream inputStream = BMIApp.appContext.getAssets().open(DATABASE_NAME);

        /**
         * Open the empty db as the output stream
         */
        OutputStream outputStream = new FileOutputStream(_databasePath);

        /**
         * transfer bytes from the input file to the output file
         */
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        /**
         * Close the streams
         */
        outputStream.flush();
        outputStream.close();
        inputStream.close();

    }

    /**
     * This method is used to open database is write mode.
     *
     * @throws SQLException if some error occurred while opening the database
     */
    private void _openDataWritabaleBase() throws SQLException {
        _sqliteDatabase = _databaseHelper.getWritableDatabase();
    }

    /**
     * This method is used to close the database.
     */
    public synchronized void close() {
        if (_sqliteDatabase != null) {
            _sqliteDatabase.close();
        }
    }

    /**
     * This class extends the Android's {@link SQLiteOpenHelper} and is mainly used for interacting
     * with {@link SQLiteDatabase}. Opening the database (read/write mode), creating database if it
     * does not exists, handling the database version changes.
     *
     * @author Sayyad.abid
     */
    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL("create table user(id integer primary key, uname text, upass text, email text)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS user");
            onCreate(db);
        }
    }

    /**
     * Method to get records from database.
     *
     * @param table         the table name
     * @param columns       the array of column names to be retrieved
     * @param selection     the selection(where clause)
     * @param selectionArgs the selection arguments (conditions used in where clause)
     * @param groupBy       the group by clause
     * @param having        the having clause
     * @param orderBy       the sort order
     * @return the Cursor for matching records, null otherwise
     */
    public Cursor getRecords(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = null;
        cursor = _sqliteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        return cursor;
    }

    /**
     * Method to add records to database
     *
     * @param table          the table name
     * @param nullColumnHack the null column hack
     * @param values         the Content values
     * @return the no of rows affected in table
     */
    public int add(String table, String nullColumnHack, ContentValues values) {
        return (int) _sqliteDatabase.insert(table, nullColumnHack, values);
    }

    /**
     * Method to update records to database
     *
     * @param table       the table name
     * @param values      the Content values
     * @param whereClause the where clause
     * @param whereArgs   the where clause conditions
     * @return the no of rows affected in table
     */
    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        int res = (int) _sqliteDatabase.update(table, values, whereClause, whereArgs);

        return res;
    }

    /**
     * Method to delete records from database.
     *
     * @param table       the table name
     * @param whereClause the where clause
     * @param whereArgs   the where clause conditions
     * @return the no of rows affected in table
     */
    public int delete(String table, String whereClause, String[] whereArgs) {
        return (int) _sqliteDatabase.delete(table, whereClause, whereArgs);
    }
}