package laura.gotarra.tabmyscores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditTabActivity extends AppCompatActivity {
    private Diccionari diccionari;
    private TabView tabView2;
    private EditText editText;
    private Spinner chose_Chord;
    private Object[] chords; // = {"Do", "Re", "Mi", "Fa", "Sol", "La", "Si"};
    private String chord_actual;
    private ArrayList<String> chord;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tab);

        chord = new ArrayList<>();
        diccionari = new Diccionari();
        chose_Chord = findViewById(R.id.chose_Chord);
        editText = findViewById(R.id.editText);
        chords = diccionari.getChords().keySet().toArray();
        chose_Chord.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, chords));

        tabView2 = findViewById(R.id.tabView2);
        tabView2.setChords_frets(diccionari.getChords().get("Do")); // Default visible chord

        Intent intent = getIntent();
        if (intent != null){
            pos = intent.getIntExtra("current_item_pos",0);
        }


        chose_Chord.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tabView2.setChords_frets((diccionari.getChords().get(parent.getItemAtPosition(position))));
                chord_actual = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent about;
        switch(id){
            case R.id.sing_in_menu:
                // Log.i("ActionBar", "Settings!");
                about = new Intent(getApplicationContext(), SingInActivity.class);
                startActivity(about);
                return true;
            case R.id.log_in_menu:
                about = new Intent(getApplication(), LogInActivity.class);
                startActivity(about);
                return true;
            case R.id.profile_menu:
                about = new Intent(getApplication(), UserProfileActivity.class);
                startActivity(about);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void addChord(View view){
        chord.add(chord_actual);
    }
    public void finish(View view){
        String phrase = editText.getText().toString();
        Intent data = new Intent();
        data.putExtra("phrase", phrase);
        data.putExtra("CH", chord);
        data.putExtra("current_item_pos", pos);
        setResult(RESULT_OK, data);
        finish();
    }
}
