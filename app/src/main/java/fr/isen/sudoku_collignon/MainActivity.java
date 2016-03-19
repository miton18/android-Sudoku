package fr.isen.sudoku_collignon;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    /**
     * Au click sur Play, démarre l'intent
     * @param v
     */
    public void play( View v ) {

        startActivity( new Intent(this, JeuActivity.class) );
    }

    /**
     * Au click sur About, démarre l'intent
     * @param v
     */
    public void about( View v ) {

        startActivity( new Intent(this, AboutActivity.class) );
    }

    /**
     *  Implémente le menu
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.quit:
                finish();
                break;
        }
        return true;
    }
}
