package com.morales.parcialmovilesv4;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class AddContacts extends AppCompatActivity {

    private Button button;
    ImageView imageView;
    TextView textView;
    Calendar mCurrent;
    int day, month, year;
    public Button addContact;
    InformacionAdapter informacionAdapter;


    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;



    private RecyclerView recyclerView;
    private InformacionAdapter infromacionAdapter;
    private ArrayList<Informacion> informacion;
    private ArrayList<Informacion> informacion2;
    String position;
    Informacion inforContact;





    private ImageView mSetImage;
    private Button mOptionButton;
    private RelativeLayout mRlView;
    EditText name;
    public Informacion inf;

    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

//INSTANCIANDO ID

        addContact = (Button)findViewById(R.id.add_Button);
        mSetImage = (ImageView) findViewById(R.id.image_see_contact);
        name = (EditText)findViewById(R.id.EditText_Name);


        textView = (TextView)findViewById(R.id.TextView_BDAY);
        mCurrent = Calendar.getInstance();
        imageView = (ImageView) findViewById(R.id.image_see_contact);


        final Intent i = this.getIntent();
        final Bundle bundle = new Bundle();
        bundle.getSerializable("KEYY");
        position= i.getStringExtra(Intent.EXTRA_TEXT);


        day = mCurrent.get(Calendar.DAY_OF_MONTH);
        month = mCurrent.get(Calendar.MONTH);
        year = mCurrent.get(Calendar.YEAR);

        month = month +1;

        textView.setText(day + "-" + month + "-" + year);

        //IMPLEMENTANDO CALENDARIO PARA CUMPLEANIOS
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddContacts.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month +1;
                        textView.setText(dayOfMonth + "-" + month + "-" + year);
                    }
                }, year, month, day );

                datePickerDialog.show();
            }
        });


        //BOTON AGREGAR CONTACTO
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = name.getText().toString();


                inf = new Informacion(nome);
                Toast.makeText(v.getContext(),"You added " + inf.getNombre(), Toast.LENGTH_LONG ).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("contacadd", inf);
                startActivity(intent);
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("KEY_ADD", inf);
                sendIntent.putExtras(bundle);
                sendIntent.putExtra(Intent.EXTRA_TEXT,position);
                AddContacts.this.startActivity(sendIntent);*/
            }
        });


        //FUNCIONALIDAD DE BOTON ADD_IMAGE
        FloatingActionButton add_image = (FloatingActionButton) findViewById(R.id.add_image);
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NEW ACTIVITY
                showOptions();

            }
        });
        //FINALIZANDO FUNCIONALIDAD


        //FINALIZANDO FUNCIONALIDAD ADD NEW CONTACT

    }

//FUNCION DE MENU DE OPCIONES DE AGREGO DE IMAGEN
    private void showOptions() {
        final CharSequence[] option = {"Choose from gallery", "Exit"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddContacts.this);
        builder.setTitle("Choose an option");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             /*   if(option[which] == "Take a picture"){
                    openCamera();
                }else*/
            if(option[which] == "Choose from gallery"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }


    //TOMAR FOTO DESDE CAMARA, LASTIMOSAMENTE NO FUNCIONO
   private void openCamera() {
        //ALMACENAR EN MEMORIA EXTERNA
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    //CASOS SELECIONADOS, PHOTO_CODE PARA QUE IMAGENES DEL ALBUM DE CAMARA SEAN URI Y NO DETENGA LA APLICACION
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
               case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });
                   Uri path2 = data.getData();
                   mSetImage.setImageURI(path2);


                    break;
                    //GALERIA
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    mSetImage.setImageURI(path);
                    break;

            }
        }
    }

    //PERMISOS Y EXPLICACION
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(AddContacts.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddContacts.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }


    }
