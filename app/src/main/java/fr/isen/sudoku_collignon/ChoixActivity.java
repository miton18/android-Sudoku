package fr.isen.sudoku_collignon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChoixActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listeView = null;
    String [] liste = {"1","2","3","4","5","6","7","8","9"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);

        listeView = (ListView) findViewById( R.id.numbers );
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, this.liste);
        listeView.setAdapter(adapter);
        listeView.setOnItemClickListener(this) ;
    }

    @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choix_numeros, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // Complétez
        setResult( Integer.parseInt(this.liste[position]) );
        finish();
    }
}
