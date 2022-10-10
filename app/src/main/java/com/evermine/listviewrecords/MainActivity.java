package com.evermine.listviewrecords;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Model: Record (intents=puntuació, nom)
    class Record implements Comparable<Record> {
        public int intents;
        public String nom;

        public Record(int _intents, String _nom ) {
            intents = _intents;
            nom = _nom;
        }
        public int compareTo(Record e){
            if(this.intents==e.intents)
                return 0;
            else if(this.intents>e.intents)
                return 1;
            else
                return -1;
        }

    }
    // Model = Taula de records: utilitzem ArrayList
    ArrayList<Record> records;

    // ArrayAdapter serà l'intermediari amb la ListView
    ArrayAdapter<Record> adapter;
    //Array of names
    String names[] = {"Pep","Tanjir", "Nezuck", "Zenits", "Robert","Felix"};
    Random rn = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialitzem model
        records = new ArrayList<Record>();
        // Afegim alguns exemples
        records.add( new Record(33,"Manolo") );
        records.add( new Record(12,"Pepe") );
        records.add( new Record(42,"Laura") );

        // Inicialitzem l'ArrayAdapter amb el layout pertinent
        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos

                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);

        // botó per afegir entrades a la ListView
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<4;i++) {
                    int name = rn.nextInt(names.length);
                    int tries = rn.nextInt(100);
                    records.add(new Record(tries, names[name]));

                }
                // notificar l'adapter dels canvis al model
                adapter.notifyDataSetChanged();
            }
        });
        Button sort = (Button) findViewById(R.id.sortButton);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(records);
                adapter.notifyDataSetChanged();
            }
        });
    }
}