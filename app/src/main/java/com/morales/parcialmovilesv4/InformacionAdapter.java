package com.morales.parcialmovilesv4;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karla on 29/04/2018.
 */

public class InformacionAdapter extends RecyclerView.Adapter<InformacionAdapter.InformacionViewHolder>{

    private Context contexto;
    private LayoutInflater inflater;
    private List<Informacion> informacion;



    public InformacionAdapter(Context contexto, List<Informacion> informacion) {
        this.contexto = contexto;
        this.informacion = informacion;
    }

    @Override
    public InformacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(contexto);
        View view = inflater.inflate(R.layout.contactos_card_view,parent,false);
        InformacionViewHolder informacionViewHolder = new InformacionViewHolder(view);
        return informacionViewHolder;
    }

    @Override
    public void onBindViewHolder(final InformacionViewHolder holder, final int position) {
        holder.name.setText(informacion.get(position).getNombre());
        holder.img.setImageResource(informacion.get(position).getImg());


       /* holder.buttonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Pelicula: "+ series.get(position).getName() + " #Pelis: " + series.get(position).getCaps() + " Descripcion: " + series.get(position).getDesc(), Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return informacion.size();
    }

    public static class InformacionViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        TextView name;
        ImageView img;
        Button buttonVer;

        public InformacionViewHolder(View itemView){
            super(itemView);
            card = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);
//            buttonVer = itemView.findViewById(R.id.ButtonVer);

        }
    }

    public InformacionAdapter(List<Informacion> informacion){ this.informacion = informacion;}

}
