package com.morales.parcialmovilesv4;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

import static android.app.PendingIntent.getActivity;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(final InformacionViewHolder holder, final int position) {

        if (contexto.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            holder.name.setText(informacion.get(position).getNombre());
            holder.img.setImageResource(informacion.get(position).getImg());
            final Bundle bundle = new Bundle();
            bundle.putSerializable("KEY", informacion.get(position));

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(contexto, See_Contacts.class);
                    intent.putExtras(bundle);
                    contexto.startActivity(intent);
                }
            });
        }
        else if(contexto.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){


            FragmentViewer frag = new FragmentViewer();
            Bundle bundle = new Bundle();
            bundle.putSerializable("KEY",informacion.get(position));
            frag.setArguments(bundle);

            FragmentManager fragmentManager = frag.getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.viewer, frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        }

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
