package com.morales.parcialmovilesv4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Karla on 29/04/2018.
 */

public class InformacionAdapter extends RecyclerView.Adapter<InformacionAdapter.InformacionViewHolder>{

    private AdapterView.OnItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        mListener = listener;
    }

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
        InformacionViewHolder informacionViewHolder = new InformacionViewHolder(view, mListener);
        return informacionViewHolder;
    }

    @Override
    public void onBindViewHolder(final InformacionViewHolder holder, final int position) {
        holder.name.setText(informacion.get(position).getNombre());
        holder.img.setImageResource(informacion.get(position).getImg());


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contexto, See_Contacts.class);
                contexto.startActivity(intent);
            }
        });

        //NEW ACTIVITY SEE CONTACTS
        /*Intent i = new Intent(this.contexto, AddContacts.class);
        this.contexto.startActivity(i); */


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

        public InformacionViewHolder(View itemView, final AdapterView.OnItemClickListener listener){
            super(itemView);
            card = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);
//            buttonVer = itemView.findViewById(R.id.ButtonVer);


        }
    }

    public InformacionAdapter(List<Informacion> informacion){ this.informacion = informacion;}

}
