package com.example.proyecto2.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto2.Model.PlaceModel;
import com.example.proyecto2.R;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyRow>{


    private List<PlaceModel> userModelList;

    public PlaceAdapter(List<PlaceModel> gamesModelList) {  // Constructor
        this.userModelList = gamesModelList;
    }

    @Override
    public MyRow onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prototipe_cell, parent, false);
        MyRow viewHolder = new MyRow(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRow holder, int position) {  // Pinta celda
        String receivedTitle = userModelList.get(position).getTitle();  // Obtengo el String que quiero pintar en la celda
        holder.title.setText(receivedTitle);
        String receivedObs = userModelList.get(position).getObservation();
        holder.observation.setText(receivedObs);
        String receivedImage = userModelList.get(position).getImagen();
        Bitmap photo = StringToBitMap(receivedImage);
        holder.imagen.setImageBitmap(photo);

    }

    @Override
    public int getItemCount() {  // Tamaño de la Lista (RecyclerView)
        return userModelList.size();
    }

    static class MyRow extends RecyclerView.ViewHolder {  // Crea mi vista celda, la cual tiene un TextView
        private TextView title, observation, latitude, longitude;
        private ImageView imagen;

        MyRow(View v) {
            super(v);
            title = v.findViewById(R.id.tvTitle);
            observation = v.findViewById(R.id.tvObs);
            imagen = v.findViewById(R.id.ivPlace);
            latitude = v.findViewById(R.id.tvLatitude);
            longitude = v.findViewById(R.id.tvLongitude);

        }
    }

    public void loadData (ArrayList<PlaceModel> myModelArrayList ) {
        userModelList = myModelArrayList;
        notifyDataSetChanged();

    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


}
