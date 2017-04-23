package ra129_2014.com.example.student.taskmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {


    Button noviZadatak;
    Button statistika;
    ListView lista;
    MyAdapter adapter;

    int long_klik = 0;
    int normalan_klik = 1;
    int pozicija;
    private String TAG="iz mainActivity";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"usao u onActivityResult");
        if(requestCode == normalan_klik && resultCode == RESULT_OK){

                Task tsk = (Task) data.getSerializableExtra(getResources().getString(R.string.task));
                adapter.addItem(tsk);
                adapter.notifyDataSetChanged();

        }
        if(requestCode == long_klik && RESULT_CANCELED == resultCode)
        {
            adapter.removeItem(pozicija);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"usao u onCreate");

        noviZadatak = (Button) (findViewById(R.id.b1));
        statistika = (Button) (findViewById(R.id.b2));
        adapter = new MyAdapter(this);
        lista = (ListView)(findViewById(R.id.list));
        lista.setAdapter(adapter);


        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"usao u longKlik");
                Intent in4 = new Intent(MainActivity.this,ActivityAdd.class);
                Task tsk = (Task)lista.getItemAtPosition(position);
                in4.putExtra(getResources().getString(R.string.long_klik),tsk);
                startActivityForResult(in4,long_klik);
                return true;
            }
        });


        noviZadatak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ActivityAdd.class);
                startActivityForResult(in,normalan_klik);

            }
        });

        statistika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(MainActivity.this,ActivityStatistic.class);
                startActivity(in1);
            }
        });


    }



}
