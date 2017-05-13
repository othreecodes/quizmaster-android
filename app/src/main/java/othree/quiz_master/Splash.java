package othree.quiz_master;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by OTHREE on 3/15/2016.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(800);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent openMain = new Intent("othree.quiz_master.MainActivity");
                    startActivity(openMain);
                }
            }

        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}


