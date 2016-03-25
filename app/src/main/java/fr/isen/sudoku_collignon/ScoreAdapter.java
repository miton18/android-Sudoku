package fr.isen.sudoku_collignon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rcd18 on 24/03/2016.
 */
public class ScoreAdapter extends ArrayAdapter<Score> {


    public ScoreAdapter(Context context, ArrayList<Score> scores) {
        super(context, 0, scores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Score score = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_score, parent, false);
        }
        // Lookup view for data population
        TextView Name  = (TextView) convertView.findViewById(R.id.name);
        TextView Score = (TextView) convertView.findViewById(R.id.score);
        // Populate the data into the template view using the data object
        Name.setText( score.name );
        Score.setText( Long.toString(score.score) );
        // Return the completed view to render on screen
        return convertView;
    }
}
