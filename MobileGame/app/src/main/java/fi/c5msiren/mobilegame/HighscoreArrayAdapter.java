package fi.c5msiren.mobilegame;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HighscoreArrayAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final int[] score;
    private final Integer[] imageId;

    public HighscoreArrayAdapter(Activity context,
                      String[] name, int[] score, Integer[] imageId) {
        super(context, R.layout.list_single, name);
        this.context = context;
        this.name = name;
        this.score = score;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtName = (TextView) rowView.findViewById(R.id.name);
        TextView txtScore = (TextView) rowView.findViewById(R.id.score);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtName.setTextColor(Color.WHITE);
        txtName.setText(name[position]);
        txtScore.setTextColor(Color.WHITE);
        txtScore.setTextSize(18);
        txtScore.setText(Integer.toString(score[position]));

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
