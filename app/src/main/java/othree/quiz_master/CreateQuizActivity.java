package othree.quiz_master;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CreateQuizActivity extends AppCompatActivity implements othree.quiz_master.Dialog.OnFragmentInteractionListener {
    EditText question;
    EditText aText;
    EditText bText;
    EditText cText;
    EditText dText;


    RadioButton aRadio;
    RadioButton bRadio;
    RadioButton cRadio;
    RadioButton dRadio;

    RadioGroup radioGroup;
    int currentQuestion = 1;
    TextView questionNumber;
    ArrayList<Questions> questions;
    String selectedOption = "";
    Workbook book;
    othree.quiz_master.Dialog dialog;
    String filename = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_quiz);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        question = (EditText) findViewById(R.id.questionView);
        aText = (EditText) findViewById(R.id.aText);
        bText = (EditText) findViewById(R.id.bText);
        cText = (EditText) findViewById(R.id.cText);
        dText = (EditText) findViewById(R.id.dText);
        questionNumber = (TextView) findViewById(R.id.questionNumber);
        aRadio = (RadioButton) findViewById(R.id.aRadio);

        bRadio = (RadioButton) findViewById(R.id.bRadio);
        cRadio = (RadioButton) findViewById(R.id.cRadio);
        dRadio = (RadioButton) findViewById(R.id.dRadio);
        selectedOption = "";
        currentQuestion = 1;
        setListeners();
        questions = new ArrayList<>();

        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.nextfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cont = getEnteredQuestionsValue();
                if (cont == true) {
                    currentQuestion++;
                    Toast.makeText(CreateQuizActivity.this, "QUESTION " + currentQuestion, Toast.LENGTH_SHORT).show();
                    questionNumber.setText(String.valueOf(currentQuestion));
                    clearAllData();
                }


            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void clearAllData() {
        aRadio.setChecked(false);
        bRadio.setChecked(false);
        cRadio.setChecked(false);
        dRadio.setChecked(false);

        aText.setText(null);
        bText.setText(null);
        cText.setText(null);
        dText.setText(null);

        question.setText(null);

        selectedOption = "";

    }

    private boolean getEnteredQuestionsValue() {

        boolean cont = false;
        if (TextUtils.isEmpty(question.getText().toString().trim())) {
            question.setError("Please fill in a question");
        } else if (TextUtils.isEmpty(aText.getText().toString().trim())) {
            aText.setError("Please fill in option A");
        } else if (TextUtils.isEmpty(bText.getText().toString().trim())) {
            bText.setError("Please fill in option B");
        } else if (TextUtils.isEmpty(cText.getText().toString().trim())) {
            cText.setError("Please fill in option C");
        } else if (TextUtils.isEmpty(dText.getText().toString().trim())) {
            dText.setError("Please fill in option D");
        } else if (selectedOption.equals("")) {
            Toast.makeText(this, "Please select the correct answer", Toast.LENGTH_SHORT).show();
        } else {
            Questions quest = new Questions();
            quest.setQuestion(question.getText().toString());
            quest.setAText(aText.getText().toString());
            quest.setBText(bText.getText().toString());
            quest.setCText(cText.getText().toString());
            quest.setDText(dText.getText().toString());
            quest.setAnswer(selectedOption);
            quest.setQuestionNumber(currentQuestion);
            questions.add(quest);
            cont = true;

        }


        return cont;
    }

    private void setListeners() {
        aRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "A";
                bRadio.setChecked(false);
                cRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        bRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "B";
                aRadio.setChecked(false);
                cRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        cRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "C";
                bRadio.setChecked(false);
                aRadio.setChecked(false);
                dRadio.setChecked(false);
            }
        });
        dRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = "D";
                bRadio.setChecked(false);
                cRadio.setChecked(false);
                aRadio.setChecked(false);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return true;
    }

    public void showInputDialog() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        dialog = new othree.quiz_master.Dialog();
        dialog.show(fm, "SAVE QUIZ AS...");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {

//            Dialog d = new Dialog(this);
//            d.setTitle("SUCESS!!!");
//            TextView text = new TextView(this);
//            text.setText("QUIZ BEEN SAVED "+id);
//       +     d.setContentView(text);
//            d.show();


            showInputDialog();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        filename = text;
        dialog.getActivity().finish();

        String string = "Hello world!";
        FileOutputStream outputStream;
        File n = new File(getExternalFilesDir(null) + File.separator + filename.concat(".quizfile"));

        try {
            n.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();

        }
        book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("QUIZ 1");

        int iter = 0;
        for (Questions ques : questions) {
            Row row = sheet.createRow(iter);
            row.createCell(0)
                    .setCellValue(ques.getQuestion());
            row.createCell(1).setCellValue(ques.getAText());
            row.createCell(2).setCellValue(ques.getBText());
            row.createCell(3).setCellValue(ques.getCText());
            row.createCell(4).setCellValue(ques.getDText());
            row.createCell(5).setCellValue(ques.getAnswer());
            System.out.println(iter);
            iter++;
        }

        try {
            outputStream = new FileOutputStream(n);

            book.write(outputStream);

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(CreateQuizActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }


}
