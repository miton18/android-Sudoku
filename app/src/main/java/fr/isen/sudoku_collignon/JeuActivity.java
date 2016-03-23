package fr.isen.sudoku_collignon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class JeuActivity extends AppCompatActivity {

    private GridView    gridView;
    private Intent      intent;
    private int         x=0, y=0;
    private final int   CHOIX_NUM_FENETRE   = 0;
    private boolean     hasMoved            = false;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_jeu );

        intent      = new Intent( this, ChoixActivity.class );
        gridView    = (GridView) findViewById( R.id.view );

        gridView.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch( View v, MotionEvent event ) {

                if( event.getAction() == MotionEvent.ACTION_DOWN ) {

                    // Stock la position lors de l'appuie
                    x = gridView.getXFromMatrix((int) event.getX());
                    y = gridView.getYFromMatrix((int) event.getY());
                    hasMoved = false;

                    return false;
                }
                if( event.getAction() == MotionEvent.ACTION_UP ) {

                    if ( x < 9 && y < 9 && !gridView.isFix(y, x) && !gridView.isFixInitial(y, x) && !hasMoved ) {

                        // Si c'est l'une des case, qui est vide, et qui n'est pas une définie au départ
                        startActivityForResult( intent, CHOIX_NUM_FENETRE );
                    }
                    else {
                        return false;
                    }
                }
                if( event.getAction() == MotionEvent.ACTION_MOVE ) {

                    hasMoved = true;
                    if ( x < 9 && y < 9 && gridView.isFix(y, x) && !gridView.isFixInitial(y, x) ) {

                        // Si c'est l'une des case, qui est déja remplie, et qui n'est pas remplie au départ
                        gridView.unSet( y, x );
                        gridView.invalidate();
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate( R.menu.menu_jeu, menu );
        return true;
    }

    @Override
    public void onActivityResult( int request, int result, Intent intent ) {

        // vÃ©rifier si la case n'est pas fixe, on lui affecte le numÃ©ro result
        if( !this.gridView.isFix(this.y, this.x) ) {
            this.gridView.set( this.y, this.x, result );
            this.gridView.invalidate();
        }
    }

    /**
     * Check si gagné
     * @param v
     */
    public void valider( View v ) {

        this.gridView.invalidate();

        if( this.gridView.isGridFull() ) {
            boolean resultat = gridView.isValid() ;

            if( resultat ) {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.win, Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.loose, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.fill, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.back :
                super.finish();
                break;
        }
        return true;
    }
}
