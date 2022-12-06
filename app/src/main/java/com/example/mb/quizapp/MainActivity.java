package com.example.mb.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    QuestionsList obj = new QuestionsList();
    int index = 0;
    int QuestionId;
    int ColorId;
    QuestionFragment fragmentObj;
    Button trueButton;
    Button falseButton;
    ProgressBar myProgressBar;
    Boolean ans;
    MyStorage storageObj;
    int totalScore = 0;

    public void updateMyFragment(int qId, int colorId) {

        FragmentManager manager = getSupportFragmentManager();
        manager.findFragmentById(R.id.frame_question);
        fragmentObj = QuestionFragment.newInstance(qId, colorId);
        manager.beginTransaction().replace(R.id.frame_question, fragmentObj).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.average:
                String str = storageObj.getScoreFromFile(MainActivity.this);

                int ave = storageObj.checkAverage();
                int totalCount = storageObj.checkTotalCount();
                if (totalCount == 0) {
                    Toast.makeText(this, R.string.nothing, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.avg) + ave + getString(R.string.avg1) + totalCount + getString(R.string.avg2), Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.select_num:
                Toast.makeText(this, R.string.remain, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.reset:
                storageObj.resetSavedResult(MainActivity.this);
                Toast.makeText(this, R.string.reset, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            index = savedInstanceState.getInt("QuestionIndex");
            totalScore = savedInstanceState.getInt("TotalScore");
        }

        storageObj = new MyStorage();
        QuestionId = obj.questions.get(index).question;
        ColorId = obj.colorsList.get(index);
        updateMyFragment(QuestionId, ColorId);

        trueButton = findViewById(R.id.button_true);
        falseButton = findViewById(R.id.button_false);

        myProgressBar = findViewById(R.id.progressbar);
        myProgressBar.setMax(obj.questions.size());
        myProgressBar.setProgress(0);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("QuestionIndex", index);
        outState.putInt("TotalScore", totalScore);
    }

    @Override
    public void onClick(View view) {
        /* get answer from database */
        ans = obj.questions.get(index).answer;

        /* now match to user's answer */

        if (view.getId() == R.id.button_true) {
            checkAnswer(true);
        } else {
            checkAnswer(false);

        }
        if (index == obj.questions.size() - 1) {
            showAlert();
        } else {
            index = index + 1;
            QuestionId = obj.questions.get(index).question;
            ColorId = obj.colorsList.get(index);
            updateMyFragment(QuestionId, ColorId);
            myProgressBar.setProgress(myProgressBar.getProgress() + 1);

        }

    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.alert_text1) + totalScore + getString(R.string.alert_text2))
                .setTitle(R.string.result);
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.ignore, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                restartQuiz();
            }
        });
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                saveResult();
            }
        });
        builder.create().show();
    }

    private void saveResult() {
        storageObj.saveScoreToFile(String.valueOf(totalScore), MainActivity.this);
        restartQuiz();

    }

    private void restartQuiz() {
        index = 0;
        totalScore = 0;
        QuestionId = obj.questions.get(index).question;
        ColorId = obj.colorsList.get(index);
        updateMyFragment(QuestionId, ColorId);
        myProgressBar.setProgress(0);

    }

    private void checkAnswer(boolean userAnswer) {
        if (ans == userAnswer) {
            totalScore = totalScore + 1;
            Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.incorrect, Toast.LENGTH_SHORT).show();

        }
    }
}