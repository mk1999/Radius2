package com.example.sarav.radius;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class Adapter extends ArrayAdapter<listview> {

    List<listview> adList;
    Context context;
    int resource;
    ProgressDialog p;

    public Adapter(Context context, int resource, List<listview> adList) {
        super(context, resource, adList);
        this.context = context;
        this.resource = resource;
        this.adList = adList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(resource, null, false);
        p = new ProgressDialog(getContext());
        TextView name = view.findViewById(R.id.text);
        TextView age = view.findViewById(R.id.text1);
        ImageView img = view.findViewById(R.id.img);

        listview ad1 = adList.get(position);
        name.setText(ad1.getName());
        age.setText(ad1.getAge());
       
        Glide.with(getContext())
                .load(ad1.getPic())
                .into(img);

        return view;
    }

}
