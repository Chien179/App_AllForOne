package com.android.app_allforone.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.app_allforone.R;
import com.android.app_allforone.models.Movie;
import com.android.app_allforone.utils.DownloadImageTask;

import java.util.ArrayList;

public class MoreMovieAdapter extends ArrayAdapter<Movie> {
    Activity context;
    int resource;
    ArrayList<Movie> objects;

    public MoreMovieAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Movie> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = this.context.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(this.resource, null);

        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtDuration = (TextView) view.findViewById(R.id.txtDuration);
        TextView txtNation = (TextView) view.findViewById(R.id.txtNation);
        TextView txtCate = (TextView) view.findViewById(R.id.txtCate);
        ImageView imageMovie = (ImageView) view.findViewById(R.id.imageMovie);
        Button btnPlay = (Button) view.findViewById(R.id.btnPlay);

        final Movie movie = this.objects.get(position);
        txtName.setText(movie.getName());
        txtNation.setText(movie.getNation());
        txtDuration.setText(Integer.toString(movie.getDuration()));
        new DownloadImageTask(imageMovie).execute(movie.getImage());
        String cat = "";
        for (String cate : movie.getCategory()){
            cat = cate + ", ";
        }
        txtCate.setText(cat);

        return view;
    }
}
