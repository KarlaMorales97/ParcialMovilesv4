package com.morales.parcialmovilesv4;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.app.PendingIntent.getActivity;

/**
 * Created by Karla on 29/04/2018.
 */

public abstract class InformacionAdapter extends RecyclerView.Adapter<InformacionAdapter.InformacionViewHolder> {

    private AdapterView.OnItemClickListener mListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
    }

    private Context contexto;
    private LayoutInflater inflater;
    private List<Informacion> informacion;
    private static boolean fav = false;
    InformacionAdapter inforAdap;
    int favs= 0;


    public InformacionAdapter(Context contexto, List<Informacion> informacion) {
        this.contexto = contexto;
        this.informacion = informacion;
    }

    @Override
    public InformacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(contexto);
        View view = inflater.inflate(R.layout.contactos_card_view, parent, false);
        InformacionViewHolder informacionViewHolder = new InformacionViewHolder(view, mListener);
        return informacionViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(final InformacionViewHolder holder, final int position) {

        if (contexto.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            holder.name.setText(informacion.get(position).getNombre());
//            holder.img.setImageResource(informacion.get(position).getImg());
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

            //VERIFICAR SI ESTA CHECKEADO O NO
            holder.favorito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean fav) {

                    holder.favorito.setChecked(true);
                    informacion.get(position).setFav(fav);
                    onVerClick(buttonView,position);
                    numFavs(favs);

                }
            });

            //BOTON PARA INICIAR FAVORITOS



            holder.favorito.setOnClickListener(new View.OnClickListener() {

                int value;

                @Override
                public void onClick(View v) {
                    if(validar(value)) {
                        Toast.makeText(v.getContext(), "You added " + informacion.get(position).getNombre() + " successfully", Toast.LENGTH_LONG).show();
                        value = 1;
                    }
                    else{

                        value = 0;
                    }
                }
            });
            //FINALIZA METODO DE BOTON FAVORITOS

        } else if (contexto.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            ContactosFragment contactosFragment;

            FragmentViewer frag = new FragmentViewer();
            Bundle bundle = new Bundle();
            bundle.putSerializable("KEY", informacion.get(position));
            frag.setArguments(bundle);



            FragmentManager fragmentManager = frag.getChildFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.viewer, frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


        }

    }


    @Override
    public int getItemCount() {
        return informacion.size();
    }

    public static class InformacionViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView name;
        ImageView img;
        Button buttonVer;
        CheckBox favorito;
        RecyclerView rv;


        public InformacionViewHolder(View itemView, final AdapterView.OnItemClickListener listener) {
            super(itemView);
            card = itemView.findViewById(R.id.card_view);
            name = itemView.findViewById(R.id.name);
  //          img = itemView.findViewById(R.id.img);
//            fav = itemView.findViewById(R.id.ButtonVer);
            favorito = itemView.findViewById(R.id.fav);
            rv = itemView.findViewById(R.id.recycler);


        }
    }

    public InformacionAdapter(List<Informacion> informacion) {
        this.informacion = informacion;
    }


    public boolean validar(int estado){
        int act=1;
        boolean flag;
        int desactivado;
        if(act!=estado){
            act=1;
            return true;
        }
        else{
            return false;
        }
    }


    public abstract void onVerClick(View v,int pos);
    public abstract void numFavs(int favs);


}