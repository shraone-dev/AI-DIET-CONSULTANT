package com.aidiet.consultant.db;

import android.content.ContentValues;
import android.database.Cursor;

public class UserDB {
    private static UserDB _instance;
    private final String TABLE_USER = "user";
    private final String COLUMN_NAME = "uname";
    private final String COLUMN_PASSWORD = "upass";
    private final String COLUMN_EMAIL = "email";

    private UserDB() {}

    public static UserDB getInstance() {
        if (_instance == null) {
            _instance = new UserDB();
        }
        return _instance;
    }

    public int add(User model) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, model.getUname());
        contentValues.put(COLUMN_PASSWORD, model.getUpass());
        contentValues.put(COLUMN_EMAIL, model.getEmail());
        String whereClause = COLUMN_EMAIL + "=?";
        String[] whereArgs = {model.getEmail()};
        int res = MasterDatabase.getInstance().update(TABLE_USER, contentValues, whereClause, whereArgs);
        if (res <= 0) {
            res = MasterDatabase.getInstance().add(TABLE_USER, null, contentValues);
        }
        return res;
    }

    public User getAllDetailsOfUser(String email, String pass) {
        String[] columns = {COLUMN_NAME, COLUMN_PASSWORD, COLUMN_EMAIL};
        String where = COLUMN_EMAIL + "='" + email + "' and " + COLUMN_PASSWORD + "='" + pass + "'";
        Cursor cursor = MasterDatabase.getInstance().getRecords(TABLE_USER, columns, where, null, null, null, null);
        User user = null;
        if (cursor != null && cursor.getCount() > 0) {
            try {
                if (cursor.moveToNext()) {
                    String uname = cursor.getString(0);
                    String upass = cursor.getString(1);
                    String uemail = cursor.getString(2);
                    user = new User(uname, upass, uemail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        return user;
    }

}
