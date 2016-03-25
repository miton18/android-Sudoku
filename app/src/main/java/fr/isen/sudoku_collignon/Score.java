package fr.isen.sudoku_collignon;

/**
 * Created by rcd18 on 24/03/2016.
 */
public class Score {

    public int ID;
    public String name;
    public long score;
    public Integer time;

    public Score( int ID, String name, Long score, Integer time ) {
        this.ID     = ID;
        this.name   = name;
        this.score  = score;
        this.time   = time;
    }
    public Score() {

    }

    @Override
    public String toString(){
        return "ID: " + ID + " Pseudo: " + name + " Score: " + score + " Time: " + time;
    }
}
