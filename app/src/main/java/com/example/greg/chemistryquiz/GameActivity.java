package com.example.greg.chemistryquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.LinkedHashSet;
import java.util.Set;




public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private Button ansOne;
    private String ansOneText;

    private Button ansTwo;
    private String ansTwoText;

    private Button ansThree;
    private String ansThreeText;

    private Button ansFour;
    private String ansFourText;

    private Set<Integer> indices = new LinkedHashSet<Integer>();

    private String ansCorrect;
    private int ansIndex;
    private String hint;

    private int correct = 0;
    private int incorrect = 0;

    private int questions_left = 8;
    private Animation shake_animation;

    private SecureRandom random = new SecureRandom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        addListener();
        updateScore();
        setButtonText(ansOne, ansTwo, ansThree, ansFour);
    }

    public void updateScore() {
        TextView score = (TextView) findViewById(R.id.question_number);
        String s = "Correct: " + correct + "\nIncorrect: " + incorrect + "\nQuestions Left: " + questions_left;
        score.setText(s);
    }

    public void addListener(){
        ansOne = (Button) findViewById(R.id.button_ansOne);
        ansOne.setOnClickListener(this);

        ansTwo = (Button) findViewById(R.id.button_ansTwo);
        ansTwo.setOnClickListener(this);

        ansThree = (Button) findViewById(R.id.button_ansThree);
        ansThree.setOnClickListener(this);

        ansFour = (Button) findViewById(R.id.button_ansFour);
        ansFour.setOnClickListener(this);
    }

    public boolean isCorrect(String guess, String correct) {
        if(guess.equalsIgnoreCase(correct)){
            return true;
        }
        return false;
    }


    public void respondToInput(Boolean isCorrect) {
        if(isCorrect){
            correct++;
            questions_left--;
            Toast.makeText(GameActivity.this, "Correct, L337", Toast.LENGTH_SHORT).show();

            nextQuestion();


        } else {
            incorrect++;
            Toast.makeText(GameActivity.this, "Incorrect, N00b", Toast.LENGTH_SHORT).show();
        }
    }

    public void transition() {

    }


    public void setAnswer(int index) {
        String[] answers = getResources().getStringArray(R.array.names);
        ansCorrect = answers[index];
        ansIndex = index;
        String[] hints = getResources().getStringArray(R.array.symbols);
        hint = hints[index];
        TextView hinter = (TextView) findViewById(R.id.mainText);
        hinter.setText(hint);
    }

    public void setButtonText(Button one, Button two, Button three, Button four){
        String[] answers = getResources().getStringArray(R.array.names);

        indices.clear();
        while(indices.size() <= 4) {
            indices.add(random.nextInt(answers.length));
        }
        Integer[] temp = new Integer[indices.size()];
        indices.toArray(temp);
        String randone = answers[temp[0]];
        ansOneText = randone;
        one.setText(randone);

        String randtwo = answers[temp[1]];
        ansTwoText = randtwo;
        two.setText(randtwo);

        String randthree = answers[temp[2]];
        ansThreeText = randthree;
        three.setText(randthree);

        String randfour = answers[temp[3]];
        ansFourText = randfour;
        four.setText(randfour);


        setAnswer(temp[random.nextInt(4)]);
        return;
    }

    public void nextQuestion(){
        if(questions_left >= 0) {
            ansOne.setEnabled(true);
            ansTwo.setEnabled(true);
            ansThree.setEnabled(true);
            ansFour.setEnabled(true);
            setButtonText(ansOne, ansTwo, ansThree, ansFour);
        } else {
            String s = "Correct: " + correct + "\nIncorrect: " + incorrect + "\nPercent Correct: " + ((double)correct / (((double)correct + (double)incorrect)) * 100.0) + "%";
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Final Score")
                    .setMessage(s);

            builder.setNegativeButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent main;
                            main = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(main);
                        }
                    });
            builder.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ansOne:
                if (!isCorrect(ansOneText, ansCorrect)) {
                    ansOne.setEnabled(false);
                }
                respondToInput(isCorrect(ansOneText, ansCorrect));
                break;
            case R.id.button_ansTwo:
                if (!isCorrect(ansTwoText, ansCorrect)) {
                    ansTwo.setEnabled(false);
                }
                respondToInput(isCorrect(ansTwoText, ansCorrect));
                break;
            case R.id.button_ansThree:
                if (!isCorrect(ansThreeText, ansCorrect)) {
                    ansThree.setEnabled(false);
                }
                respondToInput(isCorrect(ansThreeText, ansCorrect));
                break;
            case R.id.button_ansFour:
                if (!isCorrect(ansFourText, ansCorrect)) {
                    ansFour.setEnabled(false);
                }
                respondToInput(isCorrect(ansFourText, ansCorrect));
                break;

        }
        if (questions_left >= 0) {
            updateScore();
        }
    }
}
