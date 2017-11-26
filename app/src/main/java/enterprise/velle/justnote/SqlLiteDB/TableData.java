package enterprise.velle.justnote.SqlLiteDB;


/**
 * <p>Created by</p></br>
 * <h1>Emir Veledar</h1></br>
 * <p>29.10.2017. </p></br>
 * <p>Tables and columns</p></br>
 *
 *
 * <h2>Email for contact -> -> -> emir.veledar@edu.fit.ba <- <- <- </h2></br>
 *
 */
public abstract class TableData {

    public static final String DATABASE_NAME = "enterprisevelle_simplenote_db.db";
    // menu table
    public static final String TABLE_MENU_NAME = "navigation_drawer_menu";

    //coulumns of "TABLE_MENU_NAME"
    public static final String TABLE_MENU_COLUMN_ID = "menu_id";
    public static final String TABLE_MENU_COLUMN_MENU_NAME = "menu_name";
    public static final String TABLE_MENU_COLUMN_IS_USER_CREATED = "menu_is_user_created";
    public static final String TABLE_MENU_COLUMD_MENU_ICON = "menu_icon";
    public static final String TABLE_MENU_COLUMD_MENU_POST_DATE = "menu_post_date";

    // menu notes
    public static final String TABLE_NOTE_NAME = "notes_table";

    // columns of "TABLE_NOTE_NAME"
    public static final String TABLE_NOTE_COLMN_NOTE_ID = "note_id";
    public static final String TABLE_NOTE_COLUMN_NOTE_TITLE = "note_title";
    public static final String TABLE_NOTE_COLUMN_NOTE_CONTENT = "note_content";
    public static final String TABLE_NOTE_COLUMN_NOTE_DATE = "note_date";
    public static final String TABLE_NOTE_COLUMN_NOTE_CATEGORY = "note_category";
    public static final String TABLE_NOTE_COLUMN_NOTE_RESTORE_CATEGORY = "restore_category";
    public static final String TABLE_MENU_COLUMN_MENU_PRIORITY = "priority";

    


}
