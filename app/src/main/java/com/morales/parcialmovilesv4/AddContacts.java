package com.morales.parcialmovilesv4;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.RoundingMode;
import java.util.Calendar;

public class AddContacts extends AppCompatActivity {

    private Button button;
    ImageView imageView;
    TextView textView;
    Calendar mCurrent;
    int day, month, year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        //INSTANCIANDO ID
        textView = (TextView)findViewById(R.id.TextView_BDAY);
        mCurrent = Calendar.getInstance();
        imageView = (ImageView) findViewById(R.id.image_view);

        day = mCurrent.get(Calendar.DAY_OF_MONTH);
        month = mCurrent.get(Calendar.MONTH);
        year = mCurrent.get(Calendar.YEAR);

        month = month +1;

        textView.setText(day + "/" + month + "/" + year);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddContacts.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(Date
                                                  Picker view, int year, int month, int dayOfMonth) {
                        month = month +1;
                        textView.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, year, month, day );

                datePickerDialog.show();
            }
        });



        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);
    }
}
