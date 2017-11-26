package enterprise.velle.justnote.SqlLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import enterprise.velle.justnote.CustomItems.NavigationItem;
import enterprise.velle.justnote.CustomItems.NoteItem;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>ADatabase adapter.</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public class SqlLiteDatabaseAdapter extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_TABLE_MENU =
            "CREATE TABLE " + TableData.TABLE_MENU_NAME + " ( " +
                    TableData.TABLE_MENU_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", "+
                    TableData.TABLE_MENU_COLUMN_MENU_NAME + " TEXT , " +
                    TableData.TABLE_MENU_COLUMN_MENU_PRIORITY +" INTEGER ," +
                    TableData.TABLE_MENU_COLUMD_MENU_ICON + " INTEGER ) ";// +
                    //TableData.TABLE_MENU_COLUMN_IS_USER_CREATED + " INTEGER NOT NULL" + " )";
    public static final String CREATE_TABLE_NOTE =
                            "CREATE TABLE " + TableData.TABLE_NOTE_NAME + " ( " +
                                    TableData.TABLE_NOTE_COLMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                                    TableData.TABLE_NOTE_COLUMN_NOTE_TITLE + " TEXT , " +
                                    TableData.TABLE_NOTE_COLUMN_NOTE_CONTENT + " TEXT , " +
                                    TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY + " TEXT , " +
                                    TableData.TABLE_NOTE_COLUMN_NOTE_DATE +" TEXT ," +
                                    TableData.TABLE_NOTE_COLUMN_NOTE_RESTORE_CATEGORY + " TEXT " + ")";

    public SqlLiteDatabaseAdapter(Context context){
        super(context, TableData.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("DATABASE OPERATION", "Database Created");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MENU);
        sqLiteDatabase.execSQL(CREATE_TABLE_NOTE);
        Log.d("DATABASE OPERATION", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        return;
    }

    public long putMenuItemIntoDb(SqlLiteDatabaseAdapter dbAdapter, String name, int icon_drawable, boolean isUserCreated, int priority){

        SQLiteDatabase dp = dbAdapter.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableData.TABLE_MENU_COLUMN_MENU_NAME, name);
        contentValues.put(TableData.TABLE_MENU_COLUMD_MENU_ICON, icon_drawable);
        contentValues.put(TableData.TABLE_MENU_COLUMN_MENU_PRIORITY, priority);
        //contentValues.put(TableData.TABLE_MENU_COLUMN_IS_USER_CREATED, isUserCreated);

        long id = dp.insert(TableData.TABLE_MENU_NAME, null, contentValues);

        Log.d("DATABASE OPERATION", "Row Menu Inserted");

        return id;
    }

    public ArrayList<NavigationItem> getAllMenuItems(SqlLiteDatabaseAdapter dbAdapter){
        ArrayList<NavigationItem> arrayList = new ArrayList<>();
        SQLiteDatabase db = dbAdapter.getReadableDatabase();
        String [] columns = {
                TableData.TABLE_MENU_COLUMN_ID,
                TableData.TABLE_MENU_COLUMN_MENU_NAME,
                TableData.TABLE_MENU_COLUMD_MENU_ICON
        };
        String sort = TableData.TABLE_MENU_COLUMN_MENU_PRIORITY;
        Cursor cursor = db.query(TableData.TABLE_MENU_NAME, columns, null, null, null, null, sort);
        cursor.moveToFirst();
        do {
            arrayList.add(new NavigationItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_MENU_COLUMN_MENU_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(TableData.TABLE_MENU_COLUMD_MENU_ICON)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(TableData.TABLE_MENU_COLUMN_ID))
            ));
        }while(cursor.moveToNext());
        db.close();
        cursor.close();
        return arrayList;
    }

    public int updateNoteItem(SqlLiteDatabaseAdapter dbAdapter, int id, String title, String content, String category){
        SQLiteDatabase database = dbAdapter.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableData.TABLE_NOTE_COLUMN_NOTE_TITLE, title);
        values.put(TableData.TABLE_NOTE_COLUMN_NOTE_CONTENT, content);
        values.put(TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY, category);

        String where = TableData.TABLE_NOTE_COLMN_NOTE_ID + " = ?";
        String [] whereArgs = {String.valueOf(id)};

        int ret_id = database.update(TableData.TABLE_NOTE_NAME, values, where, whereArgs);

        return ret_id;
    }

    public boolean deleteMenuItem(SqlLiteDatabaseAdapter dbAdapter, int id){
        SQLiteDatabase database = dbAdapter.getWritableDatabase();
        String where = TableData.TABLE_MENU_COLUMN_ID + " = ? ";
        String [] whereArgs = {String.valueOf(id)};
        database.delete(TableData.TABLE_MENU_NAME, where, whereArgs);
        database.close();
        return  true;
    }
    public ArrayList<NavigationItem> getMenusForSpinner(SqlLiteDatabaseAdapter dbAdapter){
        ArrayList<NavigationItem> items = new ArrayList<>();
        SQLiteDatabase db = dbAdapter.getReadableDatabase();
        String [] columns = {
                TableData.TABLE_MENU_COLUMN_ID,
                TableData.TABLE_MENU_COLUMN_MENU_NAME,
                TableData.TABLE_MENU_COLUMD_MENU_ICON
        };
        String selection =
                TableData.TABLE_MENU_COLUMN_MENU_NAME + " != ? AND " +
                TableData.TABLE_MENU_COLUMN_MENU_NAME + " != ? AND " +
                TableData.TABLE_MENU_COLUMN_MENU_NAME + " != ? AND " +
                TableData.TABLE_MENU_COLUMN_MENU_NAME + " != ? AND " +
                TableData.TABLE_MENU_COLUMN_MENU_NAME + " != ?";

        String[] selectionArguments = {
                "All notes",
                "Add new note label",
                "Trash",
                "Settings",
                "About App"
        };
        Cursor cursor = db.query(TableData.TABLE_MENU_NAME, columns, selection, selectionArguments, null, null, null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()){
            items.add(new NavigationItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_MENU_COLUMN_MENU_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(TableData.TABLE_MENU_COLUMD_MENU_ICON)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(TableData.TABLE_MENU_COLUMN_ID))
            ));
        }
        cursor.close();
        db.close();
        return items;
    }
    public void emptyTrashDb(SqlLiteDatabaseAdapter dbAdapter){
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        String where = TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY + " = ?";
        String [] whereArg = {"Trash"};
        db.delete(TableData.TABLE_NOTE_NAME, where, whereArg);
    }

    public long putNoteInDb(SqlLiteDatabaseAdapter dbAdapter, String noteTitle, String noteContent, String noteCategory){
        SQLiteDatabase database = dbAdapter.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TableData.TABLE_NOTE_COLUMN_NOTE_TITLE, noteTitle);
        values.put(TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY, noteCategory);
        values.put(TableData.TABLE_NOTE_COLUMN_NOTE_CONTENT, noteContent);
        values.put(TableData.TABLE_NOTE_COLUMN_NOTE_RESTORE_CATEGORY, "NULL");
        //values.put(TableData.TABLE_NOTE_COLUMN_NOTE_DATE, new Date().toString());

        long id = database.insert(TableData.TABLE_NOTE_NAME, null, values);
        database.close();
        values.clear();
        Log.d("putNoteInDb", "Note inserted");
        return  id;
    }

    public long deleteNoteFromDb(SqlLiteDatabaseAdapter dbAdapter, int ID, String previosCategory){
        SQLiteDatabase database = dbAdapter.getWritableDatabase();
        String where = TableData.TABLE_NOTE_COLMN_NOTE_ID + " = ?";
        String [] whereArgs = { String.valueOf(ID) };
        ContentValues values = new ContentValues();
        values.put(TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY, "Trash");
        values.put(TableData.TABLE_NOTE_COLUMN_NOTE_RESTORE_CATEGORY, previosCategory);
        long id = database.update(TableData.TABLE_NOTE_NAME, values, where, whereArgs);

        database.close();
        return id;
    }
    public ArrayList<NoteItem> getAllNotesForCategory(SqlLiteDatabaseAdapter dbAdapter, String category){
        ArrayList<NoteItem> notes = new ArrayList<>();

        SQLiteDatabase database = dbAdapter.getReadableDatabase();

        String [] coulumn = {
                TableData.TABLE_NOTE_COLMN_NOTE_ID,
                TableData.TABLE_NOTE_COLUMN_NOTE_TITLE,
                TableData.TABLE_NOTE_COLUMN_NOTE_CONTENT,
                TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY,
                TableData.TABLE_NOTE_COLUMN_NOTE_DATE
        };

        Cursor cursor = null;
        if(category != null){
            String selection = TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY + " = ?";
            String [] selectinArgs = {
                    category
            };

            String order = TableData.TABLE_NOTE_COLMN_NOTE_ID + " DESC";
            cursor = database.query(TableData.TABLE_NOTE_NAME, coulumn, selection, selectinArgs, null, null, order);
        } else {
            String selection = TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY + " != ?";
            String [] selectinArgs = {
                    "Trash"
            };

            String order = TableData.TABLE_NOTE_COLMN_NOTE_ID + " DESC";
            cursor = database.query(TableData.TABLE_NOTE_NAME, coulumn, selection, selectinArgs, null, null, order);
        }

        if(cursor == null || cursor.getCount() <= 0) return null;
        cursor.moveToFirst();
        do {
            notes.add(new NoteItem(
                    cursor.getInt(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLMN_NOTE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLUMN_NOTE_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLUMN_NOTE_CONTENT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY))
            ));

        }while (cursor.moveToNext());

        database.close();
        cursor.close();
        if(notes.size() == 0) return null;
        return notes;
    }

    public int deleteNoteForeverFromDb(SqlLiteDatabaseAdapter dbAdapter, int ID){
        SQLiteDatabase db = dbAdapter.getWritableDatabase();
        String where = TableData.TABLE_NOTE_COLMN_NOTE_ID + " = ?";
        String [] whereArgs = {String.valueOf(ID)};
        int _ID = db.delete(TableData.TABLE_NOTE_NAME, where, whereArgs);
        db.close();
        return _ID;
    }
    public NoteItem getNoteItem(SqlLiteDatabaseAdapter dbAdapter, int ID){
        SQLiteDatabase database = dbAdapter.getReadableDatabase();

        String [] columns = {
                TableData.TABLE_NOTE_COLMN_NOTE_ID,
                TableData.TABLE_NOTE_COLUMN_NOTE_TITLE,
                TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY,
                TableData.TABLE_NOTE_COLUMN_NOTE_CONTENT,
                TableData.TABLE_NOTE_COLUMN_NOTE_DATE
        };

        String selection = TableData.TABLE_NOTE_COLMN_NOTE_ID + " = ?";
        String [] selectionArgs = {
                String.valueOf(ID)
        };

        Cursor cursor = database.query(TableData.TABLE_NOTE_NAME, columns, selection, selectionArgs, null, null, null);
        if(cursor.getCount() <= 0) return null;
        cursor.moveToFirst();
        NoteItem noteItem = new NoteItem(
                cursor.getInt(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLMN_NOTE_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLUMN_NOTE_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLUMN_NOTE_CONTENT)),
                cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY)));
        cursor.close();
        database.close();
        return noteItem;
    }

    public boolean restoreNote(SqlLiteDatabaseAdapter dbAdapter, int ID){
        SQLiteDatabase readDb = dbAdapter.getReadableDatabase();
        String [] columns = {TableData.TABLE_NOTE_COLUMN_NOTE_RESTORE_CATEGORY };
        String readWhere = TableData.TABLE_NOTE_COLMN_NOTE_ID + " = ?";
        String []readWhereArgs = {String.valueOf(ID)};
        Cursor cursor = readDb.query(TableData.TABLE_NOTE_NAME, columns,readWhere, readWhereArgs, null, null, null);
        cursor.moveToFirst();
        String restoreCategory = null;
        do {
            if(cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLUMN_NOTE_RESTORE_CATEGORY)).equals("NULL"))
                return false;
            restoreCategory = cursor.getString(cursor.getColumnIndexOrThrow(TableData.TABLE_NOTE_COLUMN_NOTE_RESTORE_CATEGORY));
        }while (cursor.moveToNext());
        readDb.close();
        if(restoreCategory != null){
            SQLiteDatabase writedb = dbAdapter.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableData.TABLE_NOTE_COLUMN_NOTE_CATEGORY, restoreCategory);
            contentValues.put(TableData.TABLE_NOTE_COLUMN_NOTE_RESTORE_CATEGORY, "NULL");
            String writeWhere = TableData.TABLE_NOTE_COLMN_NOTE_ID + " = ?";
            String[] writeWhereArgs = {String.valueOf(ID)};
            writedb.update(TableData.TABLE_NOTE_NAME, contentValues, writeWhere, writeWhereArgs);
            writedb.close();
            return true;
        }
        return false;
    }
}
