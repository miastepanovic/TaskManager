package ra129_2014.com.example.student.taskmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ScrollView;

import java.io.Serializable;
import java.util.Date;

import static android.R.attr.name;
import static ra129_2014.com.example.student.taskmanager.R.id.checkbox;
import static ra129_2014.com.example.student.taskmanager.R.id.podsetnik;
import static ra129_2014.com.example.student.taskmanager.R.id.simpleTimePicker;


public class ActivityAdd extends AppCompatActivity {

    Button dodaj;
    Button otkazi;
    Button crveno;
    Button zuto;
    Button zeleno;

    EditText tekst1;
    EditText tekst2;


    TimePicker timePicker;
    DatePicker datePicker;

    CheckBox check;
    CheckedTextView check_tekst;

    int dan;
    int mesec;
    int godina;
    int sati;
    int minuti;
    int flag;

    String vreme;
    String datum;
    private String TAG = "iz ActivityAdd";
    boolean editing;

    MyAdapter adapter = new MyAdapter(this);
    Task tsk;

    private String pickedTime() {

        if (Build.VERSION.SDK_INT >= 23)
            sati = timePicker.getHour();
        else
            sati = timePicker.getCurrentHour();

        if (Build.VERSION.SDK_INT >= 23)
            minuti = timePicker.getMinute();
        else
            minuti = timePicker.getCurrentMinute();


        if (sati < 10 && minuti < 10) {
            vreme = "0" + Integer.toString(sati) + ":" + "0" + Integer.toString(minuti) + "AM";
        } else if (sati < 10) {
            vreme = "0" + Integer.toString(sati) + ":" + Integer.toString(minuti) + "AM";
        } else if (sati < 10) {
            vreme = Integer.toString(sati) + ":" + "0" + Integer.toString(minuti) + "AM";
        } else {
            vreme = Integer.toString(sati) + ":" + Integer.toString(minuti) + "PM";
        }

        return vreme;

    }

    private String pickedDate() {

        dan = datePicker.getDayOfMonth();
        mesec = datePicker.getMonth() + 1;
        godina = datePicker.getYear();

        datum = Integer.toString(dan) + "." + Integer.toString(mesec) + "." + Integer.toString(godina);

        return datum;
    }

    private int prioritet() {
        return flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dodaj = (Button) (findViewById(R.id.b3));
        otkazi = (Button) (findViewById(R.id.b4));
        crveno = (Button) (findViewById(R.id.b5));
        zuto = (Button) (findViewById(R.id.b6));
        zeleno = (Button) (findViewById(R.id.b7));

        tekst1 = (EditText) (findViewById(R.id.e1));
        tekst2 = (EditText) (findViewById(R.id.e2));

        datePicker = (DatePicker) (findViewById(R.id.simpleDatePicker));
        timePicker = (TimePicker) (findViewById(simpleTimePicker));

        check = (CheckBox) (findViewById(R.id.check1));

        Intent in2 = getIntent();

        if (in2.hasExtra("long_klik")) {
            tsk = (Task) in2.getSerializableExtra("long_klik");
            editing = true;
            dodaj.setText(R.string.sacuvaj);
            otkazi.setText(R.string.obrisi);
            tekst1.setText(tsk.getNaziv());
            tekst2.setText(tsk.getOpis());
            vreme = tsk.getVreme();
            datum = tsk.getDatum();

            if (tsk.isPodsetnik()) {
                check.setChecked(true);
            } else {
                check.setChecked(false);
            }


            switch (tsk.getPrioritet()) {
                case 1:
                    crveno.setEnabled(false);
                    zuto.setEnabled(true);
                    zuto.setEnabled(true);
                    break;
                case 2:
                    crveno.setEnabled(true);
                    zuto.setEnabled(false);
                    zeleno.setEnabled(true);
                    break;
                case 3:
                    crveno.setEnabled(true);
                    zuto.setEnabled(true);
                    zeleno.setEnabled(false);
                    break;
            }
        } else {
            editing = false;
        }

        dodaj.setEnabled(false);


        otkazi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in3 = new Intent(ActivityAdd.this, MainActivity.class);
                startActivity(in3);
                finish();


            }
        });

        crveno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dodaj.setEnabled(true);
                crveno.setEnabled(false);
                zuto.setEnabled(true);
                zeleno.setEnabled(true);
                flag = 1;

            }
        });

        zuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dodaj.setEnabled(true);
                zuto.setEnabled(false);
                crveno.setEnabled(true);
                zeleno.setEnabled(true);
                flag = 2;

            }

        });

        zeleno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dodaj.setEnabled(true);
                zeleno.setEnabled(false);
                crveno.setEnabled(true);
                zuto.setEnabled(true);
                flag = 3;

            }

        });


        dodaj.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String s1 = tekst1.getText().toString().trim();
                String s2 = tekst2.getText().toString().trim();

                if (tekst1.getText().toString().equals("")) {
                    tekst1.setError("Unesite ime zadatka");

                } else if ((tekst2.getText().toString().equals(""))) {
                    tekst2.setError("Unesite opis zadatka");
                } else {
                    if (!editing) {
                        Log.d(TAG, "dosao do dodavanja");
                        tsk = new Task(tekst1.getText().toString(), tekst2.getText().toString(), pickedTime(), pickedDate(), prioritet(), false, check.isChecked());
                        Intent in2 = new Intent();
                        in2.putExtra("task", tsk);
                        setResult(Activity.RESULT_OK, in2);
                        finish();
                    } else {
                        finish();
                    }
                }
            }

        });
    }
}


