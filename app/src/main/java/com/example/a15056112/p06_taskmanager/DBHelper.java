package com.example.a15056112.p06_taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by 15056112 on 25/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASKNAME = "taskname";
    private static final String COLUMN_DESCRIPTION= "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTaskTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TASKNAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT )";
        db.execSQL(createTaskTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();

        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_TASKNAME + ", "  + COLUMN_DESCRIPTION + " FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String taskName = cursor.getString(1);
                String description = cursor.getString(2);
                Task allTasks = new Task(id, taskName, description);
                tasks.add(allTasks);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public long insertTasks(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKNAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        return result;
    }

    public int updateTasks(Task data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKNAME, data.getName());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TASK, values, condition, args);
        db.close();
        return result;
    }

    public int deleteTasks(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String .valueOf(id)};
        int result = db.delete(TABLE_TASK, condition, args);
        db.close();
        return result;
    }
}
