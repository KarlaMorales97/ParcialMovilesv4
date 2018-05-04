package com.morales.parcialmovilesv4;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class See_Contacts extends AppCompatActivity {

    TextView name, number;

    Bundle bundle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see__contacts);

        name = findViewById(R.id.name_contact);
        number = findViewById(R.id.number_contact);

        Intent callingIntent = this.getIntent();
        bundle = callingIntent.getExtras();


        Informacion informacion = (Informacion) bundle.getSerializable("KEY");


        name.setText(informacion.getNombre());
        number.setText(informacion.getNumero());

        String intentAction = callingIntent.getAction();
        String intentType = callingIntent.getType();
    }

}
