package com.morales.parcialmovilesv4;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Karla on 05/05/2018.
 */

public class favoritos extends Fragment {

    RecyclerView rv;
    InformacionAdapter adapter;
    ArrayList<Informacion> datos;
    ArrayList<Informacion> list;
    LinearLayoutManager lManager;
    Bundle bundle;
    Iterator iterator;
    ContactosFragment Conta;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public favoritos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Favoritos.
     */
    // TODO: Rename and change types and number of parameters
    public static favoritos newInstance(String param1, String param2) {
        favoritos fragment = new favoritos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista=inflater.inflate(R.layout.favoritos, container, false);



        rv=  vista.findViewById(R.id.recycler_favoritos);


        datos = new ArrayList<>();
        list = new ArrayList<>();

        lManager= new LinearLayoutManager(getActivity());

        rv.setLayoutManager(lManager);

        bundle = getArguments();

        adapter=new InformacionAdapter(getContext(), datos){
            @Override
            public void onVerClick(View v, int pos) {

            }

            @Override
            public void numFavs(int favs) {

            }

        };



        if(bundle != null){

            int cont=0;

            list = (ArrayList<Informacion>) bundle.getSerializable("KEY");
            assert list != null;
            iterator= list.listIterator();

            while(iterator.hasNext()){
                Informacion informacion = (Informacion) iterator.next();
                datos.add(cont,informacion);
                int i=0;
                for (i = 0; i < cont; ++i) {
                    if(datos.get(i)== datos.get(cont)){
                        datos.remove(i);
                        list.remove(i);
                        break;
                    }
                }
                adapter.notifyItemInserted(cont);
                adapter.notifyItemRangeChanged(cont, datos.size());



                cont++;


            }



        }


        rv.setAdapter(adapter);

        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    }



