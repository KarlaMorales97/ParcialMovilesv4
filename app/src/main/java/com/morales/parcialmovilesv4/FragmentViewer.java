package com.morales.parcialmovilesv4;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Karla on 03/05/2018.
 */

public class FragmentViewer extends Fragment{

    TextView name;
    TextView number;

//FRAGMENTO LANDSCAPE

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.seecontacts_frag, container, false);


        name = view.findViewById(R.id.name_contact);
        number = view.findViewById(R.id.number_contact);

        Bundle bundle = this.getArguments();

//VERIFICANDO SI LO QUE RECIBE NO ES NULO
        if(bundle != null){
            Informacion informacion = (Informacion) bundle.getSerializable("KEY");
            name.setText(informacion.getNombre());
            number.setText(informacion.getNumero());
        }

        return view;
    }


}
