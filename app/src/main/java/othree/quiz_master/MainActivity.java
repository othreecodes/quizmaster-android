package othree.quiz_master;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Contact The Developer On\ndaviduchenna@outlook.com ", Snackbar.LENGTH_INDEFINITE)
//                        .setAction("SEND MAIL", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//
//                                emailIntent.setType("plain/text");
//
//                                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
//                                        new String[]{"daviduchenna@outlook.com"});
//
//                                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//                                        "Email Subject");
//
//                                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
//                                        "Email Body");
//
//                                startActivity(Intent.createChooser(
//                                        emailIntent, "Send mail..."));
//                            }
//
//                        }).show();
//            }
//        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(MainActivity.this,ShareQuiz.class);
                Bundle extra = new Bundle();
                extra.putString("type", "share");
                create.putExtras(extra);

                startActivityForResult(create,0);
            }
        });
        Button create = (Button) findViewById(R.id.button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent("othree.quiz_master.CreateQuizActivity");
                startActivity(create);

            }
        });


        Button newQuiz = (Button) findViewById(R.id.button3);
        newQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent create = new Intent(MainActivity.this,ChooseQuiz.class);
                        Bundle extra = new Bundle();
                        extra.putString("type", "normal");
                        create.putExtras(extra);

                        startActivityForResult(create,0);
                    }
                });
                th.start();


            }
        });
        Button newQuiz2 = (Button) findViewById(R.id.button2);
        newQuiz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent create = new Intent(MainActivity.this,ChooseQuiz.class);
                        Bundle extra = new Bundle();
                        extra.putString("type", "quick");
                        create.putExtras(extra);

                        startActivityForResult(create,0);
                    }
                });
                th.start();


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode ==RESULT_OK){

        Bundle result = data.getExtras();
            String s = result.getString("result");
           String t = result.getString("type");

            startQuiz(s,t);

        }

    }


    private void startQuiz(String s, String t) {

        if(t.equals("normal")){
            Intent create = new Intent(MainActivity.this,NormalQuiz.class);
            Bundle extra = new Bundle();
            extra.putString("question", s);
            create.putExtras(extra);
            startActivity(create);

        }else if(t.equals("quick")){
            Intent create = new Intent(MainActivity.this,QuickQuiz.class);
            Bundle extra = new Bundle();
            extra.putString("question", s);
            create.putExtras(extra);
            startActivity(create);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem shareItem = menu.findItem(R.id.shareQuiz);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        mShareActionProvider.setShareIntent(getDefaultIntent());
        mShareActionProvider.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
                //     startActivity(intent);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent create = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(create);
            return true;

        }


        return super.onOptionsItemSelected(item);
    }
    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"Download Quiz Master for Android\nhttp://www.linktodownload.com/");
        intent.setType("text/plain");

        return intent;
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder n = new AlertDialog.Builder(MainActivity.this);
        n.setTitle("Exit Quiz-Master ?");
        n.setMessage("Are you sure you want to quit this App?");
        n.setIcon(android.R.drawable.ic_dialog_alert);
        n.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent upIntent = NavUtils.getParentActivityIntent(MainActivity.this);
                finish();
            }
        });
        n.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        n.show();


    }

}
