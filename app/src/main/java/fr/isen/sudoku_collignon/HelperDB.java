package fr.isen.sudoku_collignon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rcd18 on 24/03/2016.
 */
public class HelperDB extends SQLiteOpenHelper {

    private static final String TABLE_SCORES = "table_scores";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_SCORE = "Score";
    private static final String COL_TIME = "Time";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_SCORES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT NOT NULL, "
            + COL_SCORE + " INTEGER NOT NULL, " + COL_TIME + " INTEGER" + ");";

    public HelperDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_SCORES + ";");
        onCreate(db);
    }
}
