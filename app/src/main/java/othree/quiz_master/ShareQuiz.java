package othree.quiz_master;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ShareQuiz extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       this.getMenuInflater().inflate(R.menu.sendfile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    ArrayList<String> allFiles = new ArrayList<>();
    ListView list ; ArrayAdapter t;String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_quiz);

        list =(ListView)findViewById(R.id.listView);
        result = new HashMap<>();
        allFiles(getExternalFilesDir(null));



        t = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, allFiles);

        list.setAdapter(t);


        list.setItemsCanFocus(false);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //list.setItemChecked(position,true);


            }
        });
    }

    HashMap<String,String> result;
    public void allFiles(File file) {



        if (file.isDirectory()) {
            for (int i = 0; i < file.listFiles().length; i++) {
                File[] next = file.listFiles();
                allFiles(next[i]);

            }

        } else {

            String fname = file.getName();
            if (fname.endsWith(".quizfile"))
            {
                result.put(fname.replace(".quizfile",""),file.getAbsolutePath());

                allFiles.add(fname.replace(".quizfile",""));
            }


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.e("Ennn","outside");
        if(id==R.id.sfile){
            Log.e("Ennn","jfhufdf");

           String my_sel_items = new String("Selected Items");
            SparseBooleanArray a = list.getCheckedItemPositions();
            for (int i = 0; i < a.size(); i++) {
                if (a.valueAt(i)) {
                    my_sel_items = my_sel_items + ","
                            + (String) list.getAdapter().getItem(i);
                }
            }
            Log.v("values", my_sel_items);
            Toast.makeText(getApplicationContext(),my_sel_items,Toast.LENGTH_LONG).show();
//
//            ContentValues values = new ContentValues();
//            values.put(BluetoothShare.URI, "file:///storage/sdcard0/refresh.txt");
//            values.put(BluetoothShare.DESTINATION, Environment.DIRECTORY_DOCUMENTS);
//            values.put(BluetoothShare.DIRECTION, BluetoothShare.DIRECTION_OUTBOUND);
//            Long ts = System.currentTimeMillis();
//            values.put(BluetoothShare.TIMESTAMP, ts);
//            grantUriPermission("othree.quiz_master",BluetoothShare.CONTENT_URI,BluetoothShare.DIRECTION_OUTBOUND);
//            getContentResolver().insert(BluetoothShare.CONTENT_URI, values);
//            list.clearChoices();

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            Uri screenshotUri = Uri.parse("file:///storage/sdcard0/refresh.txt");
            sharingIntent.setType("*/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            startActivity(Intent.createChooser(sharingIntent, "Share image using"));
        }
        else if(id==R.id.clear){
            list.clearChoices();
        }



        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

}
