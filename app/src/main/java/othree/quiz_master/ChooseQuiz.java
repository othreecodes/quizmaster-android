package othree.quiz_master;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ChooseQuiz extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }

    ArrayList<String> allFiles = new ArrayList<>();
    ListView list ; ArrayAdapter t;String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_quiz);
        Bundle bund = getIntent().getExtras();
        type = bund.getString("type");

        list =(ListView)findViewById(R.id.listView);
         result = new HashMap<>();
        allFiles(getExternalFilesDir(null));



         t = new ArrayAdapter<String>(this,
                 android.R.layout.simple_list_item_1, allFiles);

        list.setAdapter(t);


        list.setItemsCanFocus(false);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.setItemChecked(position, true);

                Intent sat = new Intent();
                Bundle n = new Bundle();

                n.putString("result", result.get(list.getItemAtPosition(position).toString()));
                n.putString("type",type);
                setResult(RESULT_OK, sat);
                sat.putExtras(n);


                finish();
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





            //list.setAdapter(new ArrayAdapter<File>(ChooseQuizActivity.this, android.R.layout.simple_list_item_1, allFiles));


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
        Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent,"Open Quiz File"),1);

        }

        return super.onOptionsItemSelected(item);
    }

}
