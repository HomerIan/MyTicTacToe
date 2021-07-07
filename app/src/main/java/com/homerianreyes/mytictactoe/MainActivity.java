package com.homerianreyes.mytictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.homerianreyes.mytictactoe.databinding.ActivityMainBinding;
import com.shashank.sony.fancytoastlib.FancyToast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private Button[][] container;
    private boolean firstPlayerTurn = true;
    private int roundCount;
    private int player1points;
    private int player2points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(binding.getRoot());

        container = new Button[3][3];

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                container[i][j] = findViewById(resID);
                container[i][j].setOnClickListener(this);
            }
        }

        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }

        });
    }

    private void resetGame() {
        player1points = 0;
        player2points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    public void onClick(View view) {

        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (firstPlayerTurn) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.BLUE);
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.RED);
        }

        roundCount++;

        if (checkWinner()) {
            if (firstPlayerTurn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            drawGame();
        } else {
            firstPlayerTurn = !firstPlayerTurn;
        }

    }

    private void drawGame() {
        FancyToast.makeText(this, "DRAW!", FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
        resetBoard();
    }

    private void player2Wins() {
        player2points++;
        FancyToast.makeText(this, "Player 2 WINS!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
        updatePointsText();
        resetBoard();
    }

    private void player1Wins() {
        player1points++;
        FancyToast.makeText(this, "Player 1 WINS!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
        updatePointsText();
        resetBoard();
    }

    private void updatePointsText() {
        binding.player1TextView.setText("Player 1: "+player1points);
        binding.player2TextView.setText("Player 2: "+player2points);
    }

    private void resetBoard() {
        //reinitialize
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                container[i][j].setText("");
            }
        }
        roundCount = 0;
        firstPlayerTurn = true;
    }


    private boolean checkWinner() {

        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                field[i][j] = container[i][j].getText().toString();
            }
        }
        //column match
        for (int i = 0; i < 3; i++) {

            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
            && !field[i][0].equals("")) {
                return true;
            }
        }
        //row match
        for (int i = 0; i < 3; i++) {

            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
}