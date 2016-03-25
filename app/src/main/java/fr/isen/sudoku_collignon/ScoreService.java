package fr.isen.sudoku_collignon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by rcd18 on 24/03/2016.
 */
public class ScoreService {

    private SQLiteDatabase bdd;
    private HelperDB helperDB;

    private static final int    VERSION_BDD     = 1;
    private static final String NOM_BDD         = "scores.db";
    private static final String TABLE_SCORES    = "table_scores";
    private static final String COL_ID          = "ID";
    private static final String COL_NAME        = "Name";
    private static final String COL_SCORE       = "Score";
    private static final String COL_TIME        = "Time";

    public ScoreService(Context c) {
        helperDB = new HelperDB(c, "score.db", null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = helperDB.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    /**
     * Add a score to db
     * @param name
     * @param score
     */
    public void addScore( String name, Long score ) {

        this.open();
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NAME, name);
        values.put(COL_SCORE, score);
        values.put(COL_TIME, System.currentTimeMillis()/1000 );
        //on insère l'objet dans la BDD via le ContentValues
        Long result =  bdd.insert(TABLE_SCORES, null, values);

        Log.i("DB add", Long.toString(result));
        this.close();
    }

    /**
     * Supprime un score
     * @param id
     */
    public void delScore(Integer id) {

        this.open();
        int result = bdd.delete(TABLE_SCORES, COL_ID + " = " + id, null);
        Log.i( "DB del", Integer.toString(result) );
        this.close();
    }

    /**
     * Load all scores
     * @return
     */
    public ArrayList<Score> getScore() {

        this.open();
        ArrayList<Score> scores = new ArrayList<Score>();
        Cursor c = bdd.query(TABLE_SCORES, new String[]{COL_ID, COL_NAME, COL_SCORE, COL_TIME}, null, null, null, null, null);
        c.moveToFirst();

        for(int i=0; i< c.getCount(); i++) {

            scores.add( this.cursorToScore(c) );
            c.moveToNext();

        }

        this.close();
        return scores;
    }

    /**
     * Load all score for an user
     * @param name
     * @return
     */
    public ArrayList<Score> getScrore( String name) {

        this.open();
        ArrayList<Score> scores = new ArrayList<Score>();
        Cursor c = bdd.query(TABLE_SCORES, new String[] {COL_ID, COL_NAME, COL_SCORE, COL_TIME}, COL_NAME + " LIKE \"" + name +"\"", null, null, null, null);
        c.moveToFirst();
        do {

            scores.add( this.cursorToScore(c) );
            Log.i("DB load score", this.cursorToScore(c).toString() );
            c.moveToNext();

        }while ( !c.isLast() );
        this.close();
        return scores;
    }

    private Score cursorToScore(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //On créé un livre
        Score score = new Score( c.getInt(0), c.getString(1), c.getLong(2), c.getInt(3) );
        //On retourne le livre

        Log.i("DB parse", score.toString() );

        return score;
    }

}
