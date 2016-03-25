package fr.isen.sudoku_collignon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Vector;

public class ScoreActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listeView      = null;
    private ArrayList<Score> scores = null;
    private ScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        this.scores = new ArrayList<Score>();

        ScoreService sc = new ScoreService(this);
        sc.open();

        this.scores = sc.getScore();

        sc.close();

        listeView = (ListView) findViewById( R.id.scores );
        adapter = new ScoreAdapter(this, this.scores);
        listeView.setAdapter(adapter);
        listeView.setOnItemClickListener(this) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scores, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // Complétez
        //setResult(Integer.parseInt(this.liste[position]));
        finish();
    }

    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.back :
                super.finish();
                break;
            case R.id.reset :

                ScoreService sc = new ScoreService(this);
                for(int i=0; i<this.scores.size(); i++) {
                    sc.delScore( scores.get(i).ID );
                }
                this.listeView.invalidate();
                this.listeView.refreshDrawableState();
                break;
        }
        return true;
    }
}
