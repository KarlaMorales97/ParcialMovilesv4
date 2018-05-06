package com.morales.parcialmovilesv4;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class See_Contacts extends AppCompatActivity {

    TextView name, number, address;
    ImageView idForSaveView;
    private static int REQUEST_CALL = 1;

    Bundle bundle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see__contacts);

        name = findViewById(R.id.name_contact);
        number = findViewById(R.id.number_contact);
        address = findViewById(R.id.address_contact);
        idForSaveView=(ImageView) findViewById(R.id.image_see_contact);

        Intent callingIntent = this.getIntent();
        bundle = callingIntent.getExtras();


        Informacion informacion = (Informacion) bundle.getSerializable("KEY");


        name.setText(informacion.getNombre());
        number.setText(informacion.getNumero());
        address.setText(informacion.getDireccion());


        String intentAction = callingIntent.getAction();
        String intentType = callingIntent.getType();
    }

    public void Share(View view){

        Bitmap bitmap =getBitmapFromView(idForSaveView);
        try {
            File file = new File(this.getExternalCacheDir(),"logicchip.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.putExtra(Intent.EXTRA_TEXT, "Contact\nName : "+name.getText()+"\nPhone Number : "+number.getText());
            intent.setType("*/*");
            startActivity(Intent.createChooser(intent, "Send to"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }


    public void Call(View v){
        makePhoneCall();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(this,"Permission DENIED", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void makePhoneCall(){
        String numero = number.getText().toString();
        if(numero.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(See_Contacts.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(See_Contacts.this,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

            }else {
                String dial = "tel: " + numero;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }else {
            Toast.makeText(See_Contacts.this,"The contact dont have a phone numer", Toast.LENGTH_LONG).show();
        }
    }

}
