package com.morales.parcialmovilesv4;


import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

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
    ArrayList<Informacion> add;
    CheckBox favorito;
    Boolean check;
    ImageView delete;
    Button addContact;
    Informacion informacionAdd = null;
    String position;
    private InformacionAdapter adapter;

    private List<Informacion> LiftSaves = new ArrayList<Informacion>();

    //CONTSRUCTOR VACIO
    public ContactosFragment() {

    }

//INSTANCIANDO

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
        delete = vista.findViewById(R.id.image_delete);
        addContact = vista.findViewById(R.id.add_Button);

        datos= new ArrayList<>();
        list = new ArrayList<>();
        add = new ArrayList<>();
        informacion = new ArrayList<>();
        informacionAdd = new Informacion();


    //HACIENDO UN GRIDLAYOUT SOBRE EL CARDVIEW, ESPECIFICANDO QUE EL RECYCLER VIEW TENDRA 3 COLUMNAS DE CARDVIEW
        final GridLayoutManager gManager = new GridLayoutManager(getContext(),3);
        final RecyclerView.LayoutManager lManager = gManager;
        rv.setLayoutManager(lManager);



    //OBTENIENDO DATOS Y MANDARLOS AL ADAPTADOR

         adapter = new InformacionAdapter(getContext(), ObtenerDatos()) {

            @Override
            public void onVerClick(View v, int pos) {

                int value = 1;
            if(validar(value)) {
                Toast.makeText(getContext(), "QUE ONDIS", Toast.LENGTH_LONG).show();
                favoritos frag = new favoritos();

                Bundle bundle = new Bundle();
                list.add(ObtenerDatos().get(pos));
                bundle.putSerializable("KEY", list);

                frag.setArguments(bundle);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameFav, frag);
                ft.commit();
            }
            else if(!validar(value)){
                Toast.makeText(getContext(), "TATA", Toast.LENGTH_LONG).show();
            }


            }

            @Override
            public void numFavs(int position) {


            }

            @Override
            public void delete(int position) {
                datos.remove(ObtenerDatos().get(position));
                mAdapter.notifyItemRemoved(position);

            }
            //---


        };

        rv.setAdapter(adapter);



            return vista;
    }


    private View vista;
    private View vista2;
    RecyclerView rv;
    InformacionAdapter adapterr;
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


    @Override
    public void onResume(){
        super.onResume();
        informacionAdd = new Informacion();
        Toast.makeText(getContext(), "ESTOY EN ONRESUME", Toast.LENGTH_LONG).show();
        position = null;
        informacionAdd = null;
        Intent intent = getActivity().getIntent();
            Toast.makeText(getContext(), "NO ES NULL", Toast.LENGTH_LONG).show();
            Add();


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


    public void Add(){
        ContactosFragment cf = new ContactosFragment();

        informacionAdd = new Informacion();
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        informacionAdd = (Informacion) bundle.getSerializable("KEY_ADD");
        position = intent.getStringExtra(Intent.EXTRA_TEXT);



      if (add.get(parseInt(position)) != informacionAdd) {
            add.set(parseInt(position), informacionAdd);
            add.set(parseInt(position), informacionAdd);


        }
        adapter.notifyItemInserted(parseInt(position));

    }
}





