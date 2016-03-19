package fr.isen.sudoku_collignon;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class GridService extends AsyncTask<GridView, Void, String> {

    URL             url             = null;
    URLConnection   urlConnection   = null;
    String          defaultGrid     = "000105000140000670080002400063070010900000003010090520007200080026000035000409000";
    InputStream     in;
    GridView        gv;

    /**
     * Constructor
     */
    public GridService() {

        try {
            this.url = new URL("http://sudoku.rcdinfo.fr");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(GridView... gvs) {

        // Récupère la première grille, et la stock
        this.gv = gvs[0];
        return this.getGrid();
    }

    @Override
    protected void onPostExecute(String result) {

        this.gv.set( result );
        this.gv.refreshDrawableState();
        this.gv.invalidate();
    }

    /**
     * Ouvre la connexion au serveur et récupère la chaine de caractère (Grille)
     * @return String : chaine de caractère
     */
    public String getGrid() {

        try {

            urlConnection   = this.url.openConnection();
            in              = new BufferedInputStream( urlConnection.getInputStream() );
            byte[] contents = new byte[2048];
            int bytesRead   = 0;
            String response = "";

            while( (bytesRead = in.read(contents)) != -1 ) {

                response = new String( contents, 0, bytesRead );
            }

            JSONObject res = new JSONObject(response);
            this.in.close();
            return res.getString("grid");
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
        // Si erreur, on prend la grille locale
        return defaultGrid;
    }
}
