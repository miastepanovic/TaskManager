package ra129_2014.com.example.student.taskmanager;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mia on 4/17/2017.
 */

public class MyAdapter extends BaseAdapter {

    private ArrayList<Task> tasks;
    private Context myContext;
    private Calendar c;
    private SimpleDateFormat sdf1;
    private SimpleDateFormat sdf2;
    private Date odabrano = new Date();
    private Date trenutno = new Date();
    private String current;
    private String TAG="iz adaptera";
    private int day;
    private Date date = new Date();



    public MyAdapter(Context context){
        myContext=context;
        tasks = new ArrayList<Task>();
    }
    public void addItem(Task tsk){
        tasks.add(tsk);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        tasks.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = tasks.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_element, null);
            ViewHolder holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.ime);
            holder.date = (TextView) view.findViewById(R.id.datum);
            holder.time = (TextView) view.findViewById(R.id.vreme);
            holder.prior = (ImageView) view.findViewById(R.id.prioritet);
            holder.finished = (CheckBox) view.findViewById(R.id.zavrsen);
            holder.rb = (RadioButton) view.findViewById(R.id.podsetnik);
            view.setTag(holder);
        }


        Task tsk = (Task) getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(tsk.getNaziv());
        holder.time.setText(tsk.getVreme());

        if (tsk.getPrioritet() == 1) {
            holder.prior.setImageResource(R.drawable.red);

        } else if (tsk.getPrioritet() == 2) {
            holder.prior.setImageResource(R.drawable.yellow);
        } else {
            holder.prior.setImageResource(R.drawable.green);
        }


        holder.finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.finished.isChecked()){
                    holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        current = trenutno.toString();


        sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        sdf2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");


        try {
            trenutno = sdf1.parse(current);
            Log.d(TAG,"trenutno: " + trenutno);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            odabrano = sdf2.parse(tsk.getDatum()+" "+ tsk.getVreme());
            Log.d(TAG,"odabrano: " + odabrano);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String moj_datum = tsk.getDatum();

        SimpleDateFormat simple = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal  = Calendar.getInstance();
        try {
            cal.setTime(simple.parse(moj_datum));
        }catch (ParseException e){
            e.printStackTrace();
        }

        int day = (cal.get(Calendar.DAY_OF_WEEK));
        int difference= ((int)((odabrano.getTime()/(24*60*60*1000))-(int)(trenutno.getTime()/(24*60*60*1000))));

        Log.d(TAG,"razlika: "+ difference);

        if(difference == 0){
            holder.date.setText(R.string.danas);
        }else if(difference == 1){
            holder.date.setText(R.string.sutra);
        }else if(difference >= 2 && difference <= 7) {

            Log.d(TAG, "usao u petlju" + tsk.getDatum());
           switch(day){
               case 1:
                   holder.date.setText(R.string.nedelja);
                   break;
               case 2:
                   holder.date.setText(R.string.ponedeljak);
                   break;
               case 3:
                   holder.date.setText(R.string.utorak);
                   break;
               case 4:
                   holder.date.setText(R.string.sreda);
                   break;
               case 5:
                   holder.date.setText(R.string.četvrtak);
                   break;
               case 6:
                   holder.date.setText(R.string.petak);
                   break;
               case 7:
                   holder.date.setText(R.string.subota);
                   break;
           }


        }

        else{
            holder.date.setText(tsk.getDatum());

        }

        if (tsk.isPodsetnik() == true) {
            holder.rb.setChecked(true);
        } else {
            holder.rb.setChecked(false);
        }

        if(holder.finished.isChecked()){
            tsk.setFinished(true);
        }else{
            tsk.setFinished(false);
        }



        return view;
    }

    private class ViewHolder{
        public TextView name = null;
        public TextView date = null;
        public TextView time = null;
        public ImageView prior = null;
        public CheckBox finished = null;
        public RadioButton rb = null;

    }


}
