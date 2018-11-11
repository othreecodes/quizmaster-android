package othree.quiz_master;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class PreStartActivity extends AppCompatActivity {

    Button norm;
    Button quick;
    String where;
    String file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent n = getIntent();
        Bundle bund = getIntent().getExtras();

        String action = n.getAction();
        String scheme = n.getScheme();
        ContentResolver contentResolver = getContentResolver();
         Uri uri = n.getData();
         file = uri.toString().replace("file://","").replace("%20"," ");

        String name = uri.getLastPathSegment();

        if(!name.endsWith(".quizfile")){
            Toast.makeText(getApplicationContext(),"Not A Supported File Format ",Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            File fre = new File(getExternalFilesDir(null) + File.separator + name);
            if(!fre.exists()){

                File now = new File(file);
                now.renameTo(fre);
                file = fre.getAbsolutePath();
                Toast.makeText(PreStartActivity.this,"QUIZ IMPORTED "+file,Toast.LENGTH_LONG).show();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        norm = (Button) findViewById(R.id.button3);
        quick = (Button) findViewById(R.id.button2);
        norm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(PreStartActivity.this, NormalQuiz.class);
                Bundle extra = new Bundle();
                extra.putString("question", file);
                create.putExtras(extra);

                startActivity(create);

            }
        });

        quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(PreStartActivity.this, QuickQuiz.class);
                Bundle extra = new Bundle();
                extra.putString("question", file);
                create.putExtras(extra);

                startActivity(create);

            }
        });

    }
}
