package com.morales.parcialmovilesv4;


import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karla on 29/04/2018.
 */

public class ContactosFragment extends Fragment{


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private OnFragmentInteractionListener mListener;



    private String mParam1;
    private String mParam2;
    private ListView saveListView;
    ArrayList<Informacion> datos;
    ArrayList<Informacion> list;
    CheckBox favorito;
    Boolean check;



    private List<Informacion> LiftSaves = new ArrayList<Informacion>();

    public ContactosFragment() {

    }


    public static ContactosFragment newInstance(String param1, String param2) {
        ContactosFragment fragment = new ContactosFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.recycler, container, false);
        vista2 = inflater.inflate(R.layout.favoritos, container, false);


        rv =  vista.findViewById(R.id.recycler);
        favorito = vista2.findViewById(R.id.fav);

        datos= new ArrayList<>();
        list = new ArrayList<>();

        final GridLayoutManager gManager = new GridLayoutManager(getContext(),3);
        final RecyclerView.LayoutManager lManager = gManager;
        rv.setLayoutManager(lManager);


        final InformacionAdapter adapter = new InformacionAdapter(getContext(), ObtenerDatos()) {
            @Override
            public void onVerClick(View v, int pos) {

                 favoritos frag = new favoritos();

                 Bundle bundle = new Bundle();
                 list.add(ObtenerDatos().get(pos));
                 bundle.putSerializable("KEY", list);

                 frag.setArguments(bundle);
                 final FragmentTransaction ft = getFragmentManager().beginTransaction();
                 ft.replace(R.id.frameFav, frag);
                 ft.commit();

            }

            @Override
            public void numFavs(int favs) {

            }
        };

        rv.setAdapter(adapter);



            return vista;
    }


    private View vista;
    private View vista2;
    RecyclerView rv;
    InformacionAdapter adapter;
    TextView textView;
    ArrayList<Informacion> informacion;
    favoritos fv;
    //LinearLayoutManager lManager;

//OBTENER DATOS DEL CONTACTO
    @RequiresApi(api = Build.VERSION_CODES.M)
    List<Informacion> ObtenerDatos(){
        List<Informacion> list = new ArrayList<>();

        String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";
        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";

        Cursor cursor= getContext().getContentResolver().query(
                ContactsContract.Data.CONTENT_URI, null, selectionClause, null, sortOrder);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            list.add(new Informacion(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)), cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
        }

        return list;

    }
//FINALIZANDO

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

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





